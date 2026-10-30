import React from 'react';
import { Link } from 'react-router-dom';

const Home: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-900 mb-4">
          Welcome to P2P Carpooling
        </h1>
        <p className="text-xl text-gray-600">
          A decentralized platform for secure and reliable carpooling
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <Link 
          to="/find-ride"
          className="block p-6 bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow"
        >
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Find a Ride</h2>
          <p className="text-gray-600">
            Browse available rides and connect with drivers heading your way
          </p>
        </Link>

        <Link
          to="/create-ride"
          className="block p-6 bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow"
        >
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Offer a Ride</h2>
          <p className="text-gray-600">
            Share your journey and help others while reducing costs
          </p>
        </Link>
      </div>
    </div>
  );
};

export default Home;
