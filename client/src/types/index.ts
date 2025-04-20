export interface User {
  id: string;
  name: string;
  email: string;
  driverLicense?: string;
  walletAddress?: string;
  reputation?: number;
}

export interface Ride {
  id: string;
  driver: User;
  origin: string;
  destination: string;
  date: string;
  price: number;
  availableSeats: number;
  status: 'pending' | 'active' | 'completed' | 'cancelled';
}

export interface RideRequest {
  id: string;
  userId: string;
  rideId: string;
  status: string;
  createdAt: string;
}

export interface UserProfile {
  id: string;
  name: string;
  email: string;
  walletAddress: string;
  dlVerified: boolean;
  rating: number;
  reputation: number;
  totalRides: number;
  completedRides: number;
  profileImage?: string;
}

export interface RideHistory {
  id: string;
  role: 'driver' | 'passenger';
  date: string;
  from: string;
  to: string;
  status: string;
  amount: number;
}
