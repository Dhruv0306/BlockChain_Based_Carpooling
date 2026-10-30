import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { API_BASE_URL, ROUTES } from '../constants';
import { Ride } from '../types';
import Button from '../components/shared/Button';
import { useAuth } from '../contexts/AuthContext';

const RideDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [ride, setRide] = useState<Ride | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [bookingSeats, setBookingSeats] = useState(1);

  useEffect(() => {
    fetchRideDetails();
  }, [id]);

  const fetchRideDetails = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/api/rides/${id}`);
      if (!response.ok) {
        throw new Error('Failed to fetch ride details');
      }
      const data = await response.json();
      setRide(data);
    } catch (err) {
      setError('Failed to load ride details');
    } finally {
      setLoading(false);
    }
  };

  const handleBook = async () => {
    try {
      setLoading(true);
      const response = await fetch(`${API_BASE_URL}/api/rides/${id}/book`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          passengerId: user?.id,
          seats: bookingSeats,
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to book ride');
      }

      navigate(ROUTES.FIND_RIDE);
    } catch (err) {
      setError('Failed to book ride. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  if (!ride) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="text-center">
          <h1 className="text-2xl font-bold text-gray-900">Ride not found</h1>
          <Button
            onClick={() => navigate(ROUTES.FIND_RIDE)}
            className="mt-4"
          >
            Back to Rides
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-3xl mx-auto bg-white rounded-lg shadow-lg overflow-hidden">
        <div className="p-6">
          <div className="flex justify-between items-start mb-6">
            <div>
              <h1 className="text-2xl font-bold text-gray-900 mb-2">
                {ride.origin} → {ride.destination}
              </h1>
              <p className="text-gray-600">
                {new Date(ride.date).toLocaleDateString('en-US', {
                  weekday: 'long',
                  month: 'long',
                  day: 'numeric',
                  hour: 'numeric',
                  minute: '2-digit',
                })}
              </p>
            </div>
            <div className="text-2xl font-bold text-indigo-600">
              ${ride.price.toFixed(2)}
            </div>
          </div>

          <div className="border-t border-b border-gray-200 py-4 my-4">
            <h2 className="text-lg font-semibold mb-2">Driver Information</h2>
            <div className="flex items-center">
              <img
                src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
                  ride.driver.name
                )}&background=random`}
                alt={ride.driver.name}
                className="w-12 h-12 rounded-full mr-4"
              />
              <div>
                <p className="font-medium">{ride.driver.name}</p>
                <div className="flex items-center">
                  <span className="text-yellow-400 mr-1">★</span>
                  <span className="text-gray-600">
                    {ride.driver.reputation || 'New Driver'}
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div className="space-y-4">
            <div>
              <h2 className="text-lg font-semibold mb-2">Booking Details</h2>
              <p className="text-gray-600">
                {ride.availableSeats} seats available
              </p>
            </div>

            {error && (
              <div className="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded">
                {error}
              </div>
            )}

            {ride.status === 'pending' && user?.id !== ride.driver.id && (
              <div className="space-y-4">
                <div className="flex items-center space-x-4">
                  <label className="text-gray-700">Number of seats:</label>
                  <select
                    value={bookingSeats}
                    onChange={(e) => setBookingSeats(Number(e.target.value))}
                    className="rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  >
                    {[...Array(ride.availableSeats)].map((_, i) => (
                      <option key={i + 1} value={i + 1}>
                        {i + 1}
                      </option>
                    ))}
                  </select>
                </div>

                <Button
                  onClick={handleBook}
                  loading={loading}
                  fullWidth
                >
                  Book for ${(ride.price * bookingSeats).toFixed(2)}
                </Button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default RideDetail;
