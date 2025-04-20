import type { Contract } from 'web3-eth-contract';
import type { AbiItem } from 'web3-utils';
import { web3Service } from '../web3.service';
import { CONTRACT_ADDRESSES } from '../../constants';
import { BaseContract, ContractError } from '../../types/contracts';
import PaymentEscrowABI from '../../contracts/PaymentEscrow.json';

class PaymentEscrowService implements BaseContract {
  public contract: Contract<AbiItem[]> | null = null;
  public address: string = CONTRACT_ADDRESSES.PAYMENT_ESCROW;

  async initialize(): Promise<void> {
    const web3 = web3Service.getWeb3();
    if (!web3) throw new Error('Web3 not initialized');
    
    if (!this.address) {
      throw new Error('Contract address not configured');
    }

    this.contract = new web3.eth.Contract(
      PaymentEscrowABI.abi as AbiItem[],
      this.address
    );
  }

  private async getAccount(): Promise<string> {
    const account = await web3Service.getAccount();
    if (!account) throw new Error('No account connected');
    return account;
  }

  async depositPayment(rideId: string, amount: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.depositPayment(rideId)
        .send({ from: account, value: amount });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async releasePayment(rideId: string, toAddress: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.releasePayment(rideId, toAddress)
        .send({ from: account });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async refundPayment(rideId: string, toAddress: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.refundPayment(rideId, toAddress)
        .send({ from: account });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async getRidePayment(rideId: string): Promise<string> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      return await this.contract.methods.ridePayments(rideId).call();
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async isPaymentReleased(rideId: string): Promise<boolean> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      return await this.contract.methods.paymentReleased(rideId).call();
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

export const paymentEscrow = new PaymentEscrowService();
