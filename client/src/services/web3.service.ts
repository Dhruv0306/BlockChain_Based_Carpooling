import Web3 from 'web3';
import { initializeContracts } from './contracts';

class Web3Service {
  private web3: Web3 | null = null;
  private accounts: string[] = [];
  private initialized: boolean = false;

  async initialize(): Promise<boolean> {
    if (typeof window.ethereum !== 'undefined') {
      try {
        this.web3 = new Web3(window.ethereum);
        // Request account access
        await window.ethereum.request({ method: 'eth_requestAccounts' });
        // Get connected accounts
        this.accounts = await this.web3.eth.getAccounts();
        
        // Switch to required network
        await this.switchToRequiredNetwork();
        
        // Initialize smart contracts
        await initializeContracts();
        
        this.initialized = true;
        return true;
      } catch (error) {
        console.error('Error initializing Web3:', error);
        return false;
      }
    } else {
      console.error('Please install MetaMask!');
      return false;
    }
  }

  async getAccount(): Promise<string | null> {
    if (!this.web3 || this.accounts.length === 0) {
      return null;
    }
    return this.accounts[0];
  }

  async getBalance(address: string): Promise<string> {
    if (!this.web3) {
      throw new Error('Web3 not initialized');
    }
    const balance = await this.web3.eth.getBalance(address);
    return this.web3.utils.fromWei(balance, 'ether');
  }

  async switchToRequiredNetwork(): Promise<boolean> {
    if (!window.ethereum) return false;

    try {
      // Try to switch to the required network
      await window.ethereum.request({
        method: 'wallet_switchEthereumChain',
        params: [{ chainId: '0x13881' }], // Mumbai testnet
      });
      return true;
    } catch (error: any) {
      if (error.code === 4902) {
        // Chain not added, try to add it
        try {
          await window.ethereum.request({
            method: 'wallet_addEthereumChain',
            params: [
              {
                chainId: '0x13881',
                chainName: 'Mumbai Testnet',
                nativeCurrency: {
                  name: 'MATIC',
                  symbol: 'MATIC',
                  decimals: 18,
                },
                rpcUrls: ['https://rpc-mumbai.maticvigil.com'],
                blockExplorerUrls: ['https://mumbai.polygonscan.com'],
              },
            ],
          });
          return true;
        } catch (addError) {
          console.error('Error adding network:', addError);
          return false;
        }
      }
      console.error('Error switching network:', error);
      return false;
    }
  }

  // Event listeners
  onAccountsChanged(callback: (accounts: string[]) => void): void {
    if (window.ethereum) {
      window.ethereum.on('accountsChanged', callback);
    }
  }

  onChainChanged(callback: (chainId: string) => void): void {
    if (window.ethereum) {
      window.ethereum.on('chainChanged', callback);
    }
  }

  // Clean up listeners
  removeAccountsChangedListener(callback: (accounts: string[]) => void): void {
    if (window.ethereum) {
      window.ethereum.removeListener('accountsChanged', callback);
    }
  }

  removeChainChangedListener(callback: (chainId: string) => void): void {
    if (window.ethereum) {
      window.ethereum.removeListener('chainChanged', callback);
    }
  }

  getWeb3(): Web3 | null {
    return this.web3;
  }

  isInitialized(): boolean {
    return this.initialized;
  }
}

export const web3Service = new Web3Service();

// TypeScript type declarations for window.ethereum
declare global {
  interface Window {
    ethereum?: {
      request: (request: { method: string; params?: any[] }) => Promise<any>;
      on: (event: string, callback: any) => void;
      removeListener: (event: string, callback: any) => void;
      isMetaMask?: boolean;
    };
  }
}
