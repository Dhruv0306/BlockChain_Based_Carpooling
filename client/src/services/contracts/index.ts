export { carpoolContract } from './CarpoolContract';
export { paymentEscrow } from './PaymentEscrowService';
export { reputationToken } from './ReputationTokenService';
export { rideInsurance } from './RideInsuranceService';

import { carpoolContract } from './CarpoolContract';
import { paymentEscrow } from './PaymentEscrowService';
import { reputationToken } from './ReputationTokenService';
import { rideInsurance } from './RideInsuranceService';

export const initializeContracts = async (): Promise<void> => {
  try {
    await Promise.all([
      carpoolContract.initialize(),
      paymentEscrow.initialize(),
      reputationToken.initialize(),
      rideInsurance.initialize()
    ]);
  } catch (error) {
    console.error('Failed to initialize contracts:', error);
    throw error;
  }
};
