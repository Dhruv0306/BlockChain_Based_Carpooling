import React from 'react';
import { Link } from 'react-router-dom';
import { Ride } from '../types';
import Button from './shared/Button';

interface RideCardProps {
  ride: Ride;
  onBook?: () => void;
}

const RideCard: React.FC<RideCardProps> = ({ ride, onBook }) => {
  const formatDate = (dateStr: string) => {
    const date = new Date(dateStr);
    return date.toLocaleDateString('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric',
      hour: 'numeric',
      minute: '2-digit',
    });
  };

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="p-6">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h3 className="text-lg font-semibold text-gray-900">
              {ride.origin} → {ride.destination}
            </h3>
            <p className="text-sm text-gray-500">{formatDate(ride.date)}</p>
          </div>
          <div className="text-lg font-bold text-indigo-600">
            ${ride.price.toFixed(2)}
          </div>
        </div>

        <div className="space-y-3">
          <div className="flex items-center space-x-2">
            <img
              src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
                ride.driver.name
              )}&background=random`}
              alt={ride.driver.name}
              className="w-8 h-8 rounded-full"
            />
            <div>
              <p className="text-sm font-medium text-gray-900">{ride.driver.name}</p>
              <div className="flex items-center">
                <svg
                  className="w-4 h-4 text-yellow-400"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                >
                  <path
                    fillRule="evenodd"
                    d="M10 15.585l-4.146 2.18a1 1 0 01-1.452-1.054l.793-4.617-3.353-3.267a1 1 0 01.553-1.706l4.627-.672L9.17 2.2a1 1 0 011.66 0l2.147 4.249 4.627.672a1 1 0 01.553 1.706l-3.353 3.267.793 4.617a1 1 0 01-1.452 1.054L10 15.585z"
                    clipRule="evenodd"
                  />
                </svg>
                <span className="ml-1 text-sm text-gray-500">
                  {ride.driver.reputation || 'New'}
                </span>
              </div>
            </div>
          </div>

          <div className="flex justify-between text-sm text-gray-500">
            <span>{ride.availableSeats} seats available</span>
            <span className="capitalize">{ride.status}</span>
          </div>
        </div>

        <div className="mt-4 space-y-2">
          <Link to={`/rides/${ride.id}`}>
            <Button
              variant="outline"
              fullWidth
            >
              View Details
            </Button>
          </Link>
          {ride.status === 'pending' && onBook && (
            <Button
              onClick={onBook}
              fullWidth
              variant="primary"
            >
              Book Ride
            </Button>
          )}
        </div>
      </div>
    </div>
  );
};

export default RideCard;
