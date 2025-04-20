import React from 'react';
import DLVerification from '../components/DLVerification';

const DLVerificationPage: React.FC = () => {
  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-center mb-8">Driver License Verification</h1>
      <p className="text-center text-gray-600 mb-8">
        To offer rides, please verify your driver license by uploading a photo and providing the license number.
      </p>
      <DLVerification />
    </div>
  );
};

export default DLVerificationPage;
