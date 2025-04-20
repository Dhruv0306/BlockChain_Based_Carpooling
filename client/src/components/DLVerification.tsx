import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '../constants';
import { useAuth } from '../contexts/AuthContext';

interface DLVerificationProps {
  onSuccess?: () => void;
}

const DLVerification: React.FC<DLVerificationProps> = ({ onSuccess }) => {
  const [dlImage, setDLImage] = useState<File | null>(null);
  const [dlNumber, setDLNumber] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { user } = useAuth();

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setDLImage(e.target.files[0]);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!dlImage || !dlNumber) {
      setError('Please provide both driver license image and number');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const formData = new FormData();
      formData.append('dlImage', dlImage);
      formData.append('dlNumber', dlNumber);
      formData.append('userId', user?.id || '');

      // TODO: Implement API call to verify DL
      const response = await fetch(`/api/verify-dl`, {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        throw new Error('Verification failed');
      }

      onSuccess?.();
      navigate(ROUTES.HOME);
    } catch (err) {
      setError('Driver License verification failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-8 p-6 bg-white rounded-lg shadow-lg">
      <h2 className="text-2xl font-bold mb-6 text-gray-900">
        Driver License Verification
      </h2>
      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label htmlFor="dlNumber" className="block text-sm font-medium text-gray-700">
            Driver License Number
          </label>
          <input
            type="text"
            id="dlNumber"
            value={dlNumber}
            onChange={(e) => setDLNumber(e.target.value)}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            required
          />
        </div>

        <div>
          <label htmlFor="dlImage" className="block text-sm font-medium text-gray-700">
            Upload Driver License Image
          </label>
          <input
            type="file"
            id="dlImage"
            accept="image/*"
            onChange={handleImageChange}
            className="mt-1 block w-full text-sm text-gray-500
              file:mr-4 file:py-2 file:px-4
              file:rounded-full file:border-0
              file:text-sm file:font-semibold
              file:bg-indigo-50 file:text-indigo-700
              hover:file:bg-indigo-100"
            required
          />
        </div>

        {error && (
          <div className="text-red-500 text-sm text-center">
            {error}
          </div>
        )}

        <button
          type="submit"
          disabled={loading}
          className={`w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${
            loading ? 'opacity-50 cursor-not-allowed' : ''
          }`}
        >
          {loading ? 'Verifying...' : 'Verify Driver License'}
        </button>
      </form>
    </div>
  );
};

export default DLVerification;
