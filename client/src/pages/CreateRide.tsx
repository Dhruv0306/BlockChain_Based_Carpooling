import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../components/shared/Button';
import Input from '../components/shared/Input';
import { API_BASE_URL, ROUTES } from '../constants';
import { useAuth } from '../contexts/AuthContext';

interface CreateRideForm {
  origin: string;
  destination: string;
  date: string;
  price: string;
  availableSeats: string;
}

const CreateRide: React.FC = () => {
  const navigate = useNavigate();
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const [formData, setFormData] = useState<CreateRideForm>({
    origin: '',
    destination: '',
    date: '',
    price: '',
    availableSeats: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      // Validate form data
      if (!formData.origin || !formData.destination || !formData.date || !formData.price || !formData.availableSeats) {
        throw new Error('All fields are required');
      }

      // Validate price and seats are positive numbers
      const price = parseFloat(formData.price);
      const seats = parseInt(formData.availableSeats, 10);

      if (isNaN(price) || price <= 0) {
        throw new Error('Price must be a positive number');
      }

      if (isNaN(seats) || seats <= 0) {
        throw new Error('Available seats must be a positive number');
      }

      // Create ride
      const response = await fetch(`${API_BASE_URL}/api/rides`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          ...formData,
          price,
          availableSeats: seats,
          driverId: user?.id,
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to create ride');
      }

      navigate(ROUTES.FIND_RIDE);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to create ride');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">Create a Ride</h1>

        <form onSubmit={handleSubmit} className="space-y-6">
          <Input
            label="Origin"
            name="origin"
            value={formData.origin}
            onChange={handleChange}
            placeholder="Enter pickup location"
            required
          />

          <Input
            label="Destination"
            name="destination"
            value={formData.destination}
            onChange={handleChange}
            placeholder="Enter drop-off location"
            required
          />

          <Input
            label="Date and Time"
            name="date"
            type="datetime-local"
            value={formData.date}
            onChange={handleChange}
            required
          />

          <Input
            label="Price ($)"
            name="price"
            type="number"
            min="0"
            step="0.01"
            value={formData.price}
            onChange={handleChange}
            placeholder="Enter price per seat"
            required
          />

          <Input
            label="Available Seats"
            name="availableSeats"
            type="number"
            min="1"
            value={formData.availableSeats}
            onChange={handleChange}
            placeholder="Enter number of available seats"
            required
          />

          {error && (
            <div className="text-red-600 text-sm">
              {error}
            </div>
          )}

          <Button
            type="submit"
            fullWidth
            loading={loading}
          >
            Create Ride
          </Button>
        </form>
      </div>
    </div>
  );
};

export default CreateRide;
