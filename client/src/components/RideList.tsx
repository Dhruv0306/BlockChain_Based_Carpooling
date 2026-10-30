import React, { useState } from 'react';
import RideCard from './RideCard';
import { Ride } from '../types';
import Button from './shared/Button';
import Input from './shared/Input';

interface RideListProps {
  rides: Ride[];
  loading?: boolean;
  onBookRide?: (ride: Ride) => void;
}

const RideList: React.FC<RideListProps> = ({ rides, loading = false, onBookRide }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [locationFilter, setLocationFilter] = useState('');

  const filteredRides = rides.filter((ride) => {
    const matchesSearch = (
      ride.origin.toLowerCase().includes(searchTerm.toLowerCase()) ||
      ride.destination.toLowerCase().includes(searchTerm.toLowerCase()) ||
      ride.driver.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const matchesLocation = !locationFilter || 
      ride.origin.toLowerCase().includes(locationFilter.toLowerCase()) ||
      ride.destination.toLowerCase().includes(locationFilter.toLowerCase());

    return matchesSearch && matchesLocation;
  });

  if (loading) {
    return (
      <div className="flex justify-center items-center py-8">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex gap-4 mb-6">
        <Input
          placeholder="Search rides..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="flex-1"
        />
        <Input
          placeholder="Filter by location"
          value={locationFilter}
          onChange={(e) => setLocationFilter(e.target.value)}
          className="flex-1"
        />
        <Button 
          onClick={() => {
            setSearchTerm('');
            setLocationFilter('');
          }}
          variant="outline"
        >
          Clear Filters
        </Button>
      </div>

      {filteredRides.length === 0 ? (
        <div className="text-center py-8">
          <h3 className="text-lg font-medium text-gray-900">No rides found</h3>
          <p className="mt-1 text-sm text-gray-500">
            Try adjusting your search or filter criteria
          </p>
        </div>
      ) : (
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
          {filteredRides.map((ride) => (
            <RideCard
              key={ride.id}
              ride={ride}
              onBook={() => onBookRide?.(ride)}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default RideList;
