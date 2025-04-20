import React, { HTMLAttributes } from 'react';

interface CardProps extends HTMLAttributes<HTMLDivElement> {
  className?: string;
  onClick?: () => void;
  hoverable?: boolean;
  shadow?: 'none' | 'sm' | 'md' | 'lg';
}

const Card: React.FC<CardProps> = ({
  children,
  className = '',
  onClick,
  hoverable = false,
  shadow = 'md',
  ...props
}) => {
  const shadowClasses = {
    none: '',
    sm: 'shadow-sm',
    md: 'shadow-md',
    lg: 'shadow-lg'
  };

  const baseClasses = [
    'bg-white',
    'rounded-lg',
    'p-4',
    shadowClasses[shadow],
    hoverable && 'transition-transform hover:scale-102 cursor-pointer',
    className
  ]
    .filter(Boolean)
    .join(' ');

  return (
    <div 
      className={baseClasses}
      onClick={onClick}
      {...props}
    >
      {children}
    </div>
  );
};

export default Card;
