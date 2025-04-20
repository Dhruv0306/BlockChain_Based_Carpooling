import type { Contract } from 'web3-eth-contract';
import type { AbiItem } from 'web3-utils';
import { web3Service } from '../web3.service';
import { CONTRACT_ADDRESSES } from '../../constants';
import { BaseContract, ContractError } from '../../types/contracts';
import RideInsuranceABI from '../../contracts/RideInsurance.json';

interface Insurance {
  premium: string;
  coverage: string;
  active: boolean;
  claimed: boolean;
}

interface TokenMethod<T> {
  call: () => Promise<T>;
  send: (options: { from: string; value?: string }) => Promise<any>;
}

class RideInsuranceService implements BaseContract {
  public contract: Contract<AbiItem[]> | null = null;
  public address: string = CONTRACT_ADDRESSES.RIDE_INSURANCE;

  async initialize(): Promise<void> {
    const web3 = web3Service.getWeb3();
    if (!web3) throw new Error('Web3 not initialized');
    
    if (!this.address) {
      throw new Error('Contract address not configured');
    }

    this.contract = new web3.eth.Contract(
      RideInsuranceABI.abi as AbiItem[],
      this.address
    );
  }

  private async getAccount(): Promise<string> {
    const account = await web3Service.getAccount();
    if (!account) throw new Error('No account connected');
    return account;
  }

  async purchaseInsurance(rideId: string, premium: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.purchaseInsurance(rideId)
        .send({ from: account, value: premium });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async fileClaim(rideId: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.fileClaim(rideId)
        .send({ from: account });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async getRideInsurance(rideId: string): Promise<Insurance> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const result = await (this.contract.methods.rideInsurance(rideId) as TokenMethod<{
        premium: string;
        coverage: string;
        active: boolean;
        claimed: boolean;
      }>).call();

      return {
        premium: result.premium,
        coverage: result.coverage,
        active: result.active,
        claimed: result.claimed
      };
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

export const rideInsurance = new RideInsuranceService();
