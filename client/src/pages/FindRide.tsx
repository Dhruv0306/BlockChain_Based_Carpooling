import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import RideList from '../components/RideList';
import { Ride } from '../types';
import { API_BASE_URL } from '../constants';

const FindRide: React.FC = () => {
  const [rides, setRides] = useState<Ride[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchRides();
  }, []);

  const fetchRides = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/api/rides?status=pending`);
      if (!response.ok) {
        throw new Error('Failed to fetch rides');
      }
      const data = await response.json();
      setRides(data);
    } catch (err) {
      setError('Failed to load rides. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  const handleBookRide = async (ride: Ride) => {
    // TODO: Implement booking logic
    try {
      const response = await fetch(`${API_BASE_URL}/api/rides/${ride.id}/book`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Failed to book ride');
      }

      // Refresh the rides list
      fetchRides();
    } catch (err) {
      setError('Failed to book ride. Please try again.');
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Find a Ride</h1>
        <p className="mt-2 text-gray-600">Browse available rides and book your trip</p>
      </div>

      {error && (
        <div className="mb-6 p-4 bg-red-50 border border-red-200 rounded-md">
          <p className="text-red-600">{error}</p>
        </div>
      )}

      <RideList
        rides={rides}
        loading={loading}
        onBookRide={handleBookRide}
      />
    </div>
  );
};

export default FindRide;
