import React, { useEffect, useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { UserProfile, RideHistory } from '../types';
import { userService } from '../services/user.service';
import Button from '../components/shared/Button';

const Profile = () => {
  const { user } = useAuth();
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [rideHistory, setRideHistory] = useState<RideHistory[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const [profileData, historyData] = await Promise.all([
          userService.getProfile(),
          userService.getRideHistory()
        ]);
        setProfile(profileData);
        setRideHistory(historyData);
      } catch (error) {
        console.error('Failed to fetch profile data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProfileData();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div>Loading...</div>
      </div>
    );
  }

  if (!profile) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div>Failed to load profile</div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Profile Header */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-6">
        <div className="flex items-center space-x-4">
          <div className="w-20 h-20 bg-gray-200 rounded-full">
            {profile.profileImage ? (
              <img
                src={profile.profileImage}
                alt={profile.name}
                className="w-full h-full rounded-full object-cover"
              />
            ) : (
              <div className="w-full h-full rounded-full flex items-center justify-center text-2xl text-gray-500">
                {profile.name.charAt(0)}
              </div>
            )}
          </div>
          <div className="flex-1">
            <h1 className="text-2xl font-bold">{profile.name}</h1>
            <p className="text-gray-600">{profile.email}</p>
            {profile.dlVerified && (
              <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                DL Verified
              </span>
            )}
          </div>
          <Button 
            onClick={() => window.location.href = '/settings'} 
            variant="outline"
          >
            Edit Profile
          </Button>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
        <div className="bg-white rounded-lg shadow-md p-6">
          <h3 className="text-lg font-semibold mb-2">Reputation Score</h3>
          <div className="text-3xl font-bold text-blue-600">{profile.reputation}</div>
        </div>
        <div className="bg-white rounded-lg shadow-md p-6">
          <h3 className="text-lg font-semibold mb-2">Total Rides</h3>
          <div className="text-3xl font-bold text-green-600">{profile.totalRides}</div>
        </div>
        <div className="bg-white rounded-lg shadow-md p-6">
          <h3 className="text-lg font-semibold mb-2">Rating</h3>
          <div className="text-3xl font-bold text-yellow-600">{profile.rating.toFixed(1)}/5</div>
        </div>
      </div>

      {/* Ride History */}
      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-xl font-bold mb-4">Ride History</h2>
        <div className="divide-y divide-gray-200">
          {rideHistory.map((ride) => (
            <div key={ride.id} className="py-4">
              <div className="flex justify-between items-start">
                <div>
                  <div className="font-semibold">
                    {ride.from} → {ride.to}
                  </div>
                  <div className="text-sm text-gray-600">
                    {new Date(ride.date).toLocaleDateString()} • {ride.role}
                  </div>
                </div>
                <div className="text-right">
                  <div className="font-medium">₹{ride.amount}</div>
                  <div className={`text-sm ${
                    ride.status === 'completed' ? 'text-green-600' : 
                    ride.status === 'cancelled' ? 'text-red-600' : 
                    'text-yellow-600'
                  }`}>
                    {ride.status}
                  </div>
                </div>
              </div>
            </div>
          ))}
          {rideHistory.length === 0 && (
            <div className="py-4 text-center text-gray-500">
              No rides yet
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Profile;
