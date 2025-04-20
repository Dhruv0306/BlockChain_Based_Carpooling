declare const process: {
  env: {
    REACT_APP_API_URL: string;
    REACT_APP_CARPOOL_CONTRACT_ADDRESS: string;
    REACT_APP_PAYMENT_ESCROW_ADDRESS: string;
    REACT_APP_REPUTATION_TOKEN_ADDRESS: string;
    REACT_APP_RIDE_INSURANCE_ADDRESS: string;
  }
}

export const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export const CONTRACT_ADDRESSES = {
  CARPOOL: process.env.REACT_APP_CARPOOL_CONTRACT_ADDRESS || '',
  PAYMENT_ESCROW: process.env.REACT_APP_PAYMENT_ESCROW_ADDRESS || '',
  REPUTATION_TOKEN: process.env.REACT_APP_REPUTATION_TOKEN_ADDRESS || '',
  RIDE_INSURANCE: process.env.REACT_APP_RIDE_INSURANCE_ADDRESS || '',
};

export const RIDE_STATUS = {
  PENDING: 'pending',
  ACTIVE: 'active',
  COMPLETED: 'completed',
  CANCELLED: 'cancelled',
} as const;

export const REQUEST_STATUS = {
  PENDING: 'pending',
  ACCEPTED: 'accepted',
  REJECTED: 'rejected',
} as const;

export const ROUTES = {
  HOME: '/',
  CREATE_RIDE: '/create-ride',
  FIND_RIDE: '/find-ride',
  LOGIN: '/login',
  REGISTER: '/register',
  PROFILE: '/profile',
  SETTINGS: '/settings',
  VERIFY_DL: '/verify-dl',
} as const;
