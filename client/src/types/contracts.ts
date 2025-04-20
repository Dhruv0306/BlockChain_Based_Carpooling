import type { Contract } from 'web3-eth-contract';
import type { AbiItem } from 'web3-utils';

export interface RideInfo {
  rideId: string;
  posterAddress: string;
  startLocation: string;
  endLocation: string;
  departureTime: string;
  availableSeats: string;
  fare: string;
  status: string;
}

export interface CreateRideParams {
  rideId: string;
  startLocation: string;
  endLocation: string;
  departureTime: number;
  availableSeats: number;
  fare: number;
}

export interface ContractError extends Error {
  code?: number;
  data?: any;
}

export interface BaseContract {
  contract: Contract<AbiItem[]> | null;
  address: string;
  initialize(): Promise<void>;
}
