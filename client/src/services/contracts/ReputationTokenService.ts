import type { Contract } from 'web3-eth-contract';
import type { AbiItem } from 'web3-utils';
import { web3Service } from '../web3.service';
import { CONTRACT_ADDRESSES } from '../../constants';
import { BaseContract, ContractError } from '../../types/contracts';
import ReputationTokenABI from '../../contracts/ReputationToken.json';

interface TokenInfo {
  name: string;
  symbol: string;
  decimals: number;
  totalSupply: string;
}

interface TokenMethod<T> {
  call: () => Promise<T>;
  send: (options: { from: string; value?: string }) => Promise<any>;
}

class ReputationTokenService implements BaseContract {
  public contract: Contract<AbiItem[]> | null = null;
  public address: string = CONTRACT_ADDRESSES.REPUTATION_TOKEN;

  async initialize(): Promise<void> {
    const web3 = web3Service.getWeb3();
    if (!web3) throw new Error('Web3 not initialized');
    
    if (!this.address) {
      throw new Error('Contract address not configured');
    }

    this.contract = new web3.eth.Contract(
      ReputationTokenABI.abi as AbiItem[],
      this.address
    );
  }

  private async getAccount(): Promise<string> {
    const account = await web3Service.getAccount();
    if (!account) throw new Error('No account connected');
    return account;
  }

  async getTokenInfo(): Promise<TokenInfo> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const [name, symbol, decimals, totalSupply] = await Promise.all([
        (this.contract.methods.name() as TokenMethod<string>).call(),
        (this.contract.methods.symbol() as TokenMethod<string>).call(),
        (this.contract.methods.decimals() as TokenMethod<number>).call(),
        (this.contract.methods.totalSupply() as TokenMethod<string>).call()
      ]);

      return {
        name,
        symbol,
        decimals,
        totalSupply
      };
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async balanceOf(address: string): Promise<string> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      return await (this.contract.methods.balanceOf(address) as TokenMethod<string>).call();
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async rateUser(userAddress: string, isPositive: boolean): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.rateUser(userAddress, isPositive)
        .send({ from: account });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async getReputationScore(address: string): Promise<string> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      return await (this.contract.methods.getReputationScore(address) as TokenMethod<string>).call();
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async getPositiveRatings(address: string): Promise<string> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      return await (this.contract.methods.positiveRatings(address) as TokenMethod<string>).call();
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async getNegativeRatings(address: string): Promise<string> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      return await (this.contract.methods.negativeRatings(address) as TokenMethod<string>).call();
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  private handleError(error: Error): ContractError {
    console.error('Contract error:', error);
    const contractError: ContractError = new Error(
      error.message || 'Contract operation failed'
    );
    if ('code' in error) {
      contractError.code = (error as any).code;
    }
    if ('data' in error) {
      contractError.data = (error as any).data;
    }
    return contractError;
  }
}

export const reputationToken = new ReputationTokenService();
