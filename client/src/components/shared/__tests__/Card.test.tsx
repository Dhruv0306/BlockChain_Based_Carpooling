import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Card from '../Card';

describe('Card Component', () => {
  test('renders children content correctly', () => {
    render(
      <Card>
        <div>Test Content</div>
      </Card>
    );
    expect(screen.getByText('Test Content')).toBeInTheDocument();
  });

  test('applies default styles correctly', () => {
    const { container } = render(<Card>Content</Card>);
    const card = container.firstChild as HTMLElement;
    expect(card).toHaveClass('bg-white', 'rounded-lg', 'p-4', 'shadow-md');
  });

  test('handles click events when provided', () => {
    const handleClick = jest.fn();
    render(<Card onClick={handleClick}>Clickable Card</Card>);
    
    fireEvent.click(screen.getByText('Clickable Card'));
    expect(handleClick).toHaveBeenCalledTimes(1);
  });

  test('applies hoverable styles when hoverable prop is true', () => {
    const { container } = render(<Card hoverable>Hover Card</Card>);
    const card = container.firstChild as HTMLElement;
    expect(card).toHaveClass('transition-transform', 'hover:scale-102', 'cursor-pointer');
  });

  test('applies different shadow variants correctly', () => {
    const { rerender } = render(<Card shadow="sm">Small Shadow</Card>);
    expect(screen.getByText('Small Shadow').parentElement).toHaveClass('shadow-sm');

    rerender(<Card shadow="lg">Large Shadow</Card>);
    expect(screen.getByText('Large Shadow').parentElement).toHaveClass('shadow-lg');
  });

  test('combines custom className with default styles', () => {
    const { container } = render(
      <Card className="custom-class">Custom Card</Card>
    );
    const card = container.firstChild as HTMLElement;
    expect(card).toHaveClass('custom-class', 'bg-white', 'rounded-lg');
  });

  test('renders without shadow when shadow is set to none', () => {
    const { container } = render(<Card shadow="none">No Shadow</Card>);
    const card = container.firstChild as HTMLElement;
    expect(card).not.toHaveClass('shadow-sm', 'shadow-md', 'shadow-lg');
  });

  test('maintains accessibility attributes', () => {
    render(
      <Card role="region" aria-label="Test Card">
        Accessible Content
      </Card>
    );
    expect(screen.getByRole('region')).toHaveAttribute('aria-label', 'Test Card');
  });
});
