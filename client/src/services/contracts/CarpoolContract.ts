import Web3 from 'web3';
import type { Contract } from 'web3-eth-contract';
import type { AbiItem } from 'web3-utils';
import { web3Service } from '../web3.service';
import { CONTRACT_ADDRESSES } from '../../constants';
import { BaseContract, CreateRideParams, RideInfo, ContractError } from '../../types/contracts';
import CarpoolContractABI from '../../contracts/CarpoolContract.json';

interface RideInfoResponse {
  rideId: string;
  posterAddress: string;
  startLocation: string;
  endLocation: string;
  departureTime: string;
  availableSeats: string;
  fare: string;
  status: string;
}

class CarpoolContractService implements BaseContract {
  public contract: Contract<AbiItem[]> | null = null;
  public address: string = CONTRACT_ADDRESSES.CARPOOL;

  async initialize(): Promise<void> {
    const web3 = web3Service.getWeb3();
    if (!web3) throw new Error('Web3 not initialized');
    
    if (!this.address) {
      throw new Error('Contract address not configured');
    }

    this.contract = new web3.eth.Contract(
      CarpoolContractABI.abi as AbiItem[],
      this.address
    );
  }

  private async getAccount(): Promise<string> {
    const account = await web3Service.getAccount();
    if (!account) throw new Error('No account connected');
    return account;
  }

  async createRide(params: CreateRideParams): Promise<string> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      
      await this.contract.methods.createRide(
        params.rideId,
        params.startLocation,
        params.endLocation,
        params.departureTime,
        params.availableSeats,
        params.fare
      ).send({ from: account });

      return params.rideId;
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async requestRide(rideId: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.requestRide(rideId).send({ from: account });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async acceptRideRequest(rideId: string, requesterAddress: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.acceptRideRequest(rideId, requesterAddress)
        .send({ from: account });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async makePayment(rideId: string, amount: string): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.makePayment(rideId)
        .send({ from: account, value: amount });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async updateReputation(userAddress: string, isPositive: boolean): Promise<void> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const account = await this.getAccount();
      await this.contract.methods.updateReputation(userAddress, isPositive)
        .send({ from: account });
    } catch (error) {
      throw this.handleError(error as Error);
    }
  }

  async getRideInfo(rideId: string): Promise<RideInfo> {
    if (!this.contract) throw new Error('Contract not initialized');
    
    try {
      const rideInfo = await this.contract.methods.getRideInfo(rideId).call() as RideInfoResponse;
      return {
        rideId: rideInfo.rideId,
        posterAddress: rideInfo.posterAddress,
        startLocation: rideInfo.startLocation,
        endLocation: rideInfo.endLocation,
        departureTime: rideInfo.departureTime,
        availableSeats: rideInfo.availableSeats,
        fare: rideInfo.fare,
        status: rideInfo.status
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

export const carpoolContract = new CarpoolContractService();
