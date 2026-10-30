import React from 'react';
import { Link } from 'react-router-dom';
import { ROUTES } from '../constants';

const Navbar: React.FC = () => {
  return (
    <nav className="bg-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          <Link to={ROUTES.HOME} className="text-xl font-bold text-gray-800">
            P2P Carpooling
          </Link>
          <div className="flex space-x-4">
            <Link 
              to={ROUTES.FIND_RIDE}
              className="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md"
            >
              Find Ride
            </Link>
            <Link 
              to={ROUTES.CREATE_RIDE}
              className="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md"
            >
              Create Ride
            </Link>
            <Link 
              to={ROUTES.LOGIN}
              className="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md"
            >
              Login
            </Link>
            <Link 
              to={ROUTES.REGISTER}
              className="bg-blue-500 text-white hover:bg-blue-600 px-4 py-2 rounded-md"
            >
              Register
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
