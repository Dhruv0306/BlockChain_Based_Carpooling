import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Button from '../Button';

describe('Button Component', () => {
  test('renders button with correct text', () => {
    render(<Button>Click me</Button>);
    expect(screen.getByText('Click me')).toBeInTheDocument();
  });

  test('calls onClick handler when clicked', () => {
    const handleClick = jest.fn();
    render(<Button onClick={handleClick}>Click me</Button>);
    
    fireEvent.click(screen.getByText('Click me'));
    expect(handleClick).toHaveBeenCalledTimes(1);
  });

  test('applies correct variant styles', () => {
    const { rerender } = render(<Button variant="primary">Primary Button</Button>);
    expect(screen.getByText('Primary Button')).toHaveClass('bg-blue-600');

    rerender(<Button variant="secondary">Secondary Button</Button>);
    expect(screen.getByText('Secondary Button')).toHaveClass('bg-gray-600');

    rerender(<Button variant="danger">Danger Button</Button>);
    expect(screen.getByText('Danger Button')).toHaveClass('bg-red-600');
  });

  test('applies disabled styles when disabled', () => {
    render(<Button disabled>Disabled Button</Button>);
    const button = screen.getByText('Disabled Button');
    
    expect(button).toBeDisabled();
    expect(button).toHaveClass('opacity-50', 'cursor-not-allowed');
  });

  test('applies full width style when fullWidth prop is true', () => {
    render(<Button fullWidth>Full Width Button</Button>);
    expect(screen.getByText('Full Width Button')).toHaveClass('w-full');
  });

  test('applies loading state correctly', () => {
    const { container } = render(<Button loading>Loading Button</Button>);
    
    expect(screen.getByText('Loading Button')).toHaveClass('opacity-75', 'cursor-wait');
    expect(container.querySelector('[role="status"]')).toBeInTheDocument(); // LoadingSpinner
  });

  test('renders with custom className', () => {
    render(<Button className="custom-class">Custom Button</Button>);
    expect(screen.getByText('Custom Button')).toHaveClass('custom-class');
  });

  test('handles size variants correctly', () => {
    const { rerender } = render(<Button size="sm">Small Button</Button>);
    expect(screen.getByText('Small Button')).toHaveClass('px-3', 'py-1', 'text-sm');

    rerender(<Button size="lg">Large Button</Button>);
    expect(screen.getByText('Large Button')).toHaveClass('px-6', 'py-3', 'text-lg');
  });
});
