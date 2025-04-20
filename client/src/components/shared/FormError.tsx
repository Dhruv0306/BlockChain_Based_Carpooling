import React from 'react';

interface FormErrorProps {
  errors?: string[];
  className?: string;
}

const FormError: React.FC<FormErrorProps> = ({ errors = [], className = '' }) => {
  if (!errors || errors.length === 0) return null;

  return (
    <div className={`mt-1 ${className}`}>
      {errors.map((error, index) => (
        <p
          key={index}
          className="text-sm text-red-600"
          role="alert"
        >
          {error}
        </p>
      ))}
    </div>
  );
};

export default FormError;
