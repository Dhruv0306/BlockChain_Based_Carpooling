import React, { InputHTMLAttributes, forwardRef } from 'react';
import FormError from './FormError';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  errors?: string[];
  helperText?: string;
}

const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ label, errors, helperText, className = '', ...props }, ref) => {
    const baseInputStyles =
      'block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm';
    const errorInputStyles = 'border-red-300 text-red-900 placeholder-red-300 focus:border-red-500 focus:ring-red-500';

    const inputStyles = [
      baseInputStyles,
      errors && errors.length > 0 ? errorInputStyles : '',
      className,
    ].join(' ');

    return (
      <div>
        {label && (
          <label htmlFor={props.id} className="block text-sm font-medium text-gray-700 mb-1">
            {label}
          </label>
        )}
        <div className="relative">
          <input
            ref={ref}
            className={inputStyles}
            {...props}
            aria-invalid={errors && errors.length > 0 ? 'true' : 'false'}
            aria-describedby={
              errors && errors.length > 0
                ? `${props.id}-error`
                : helperText
                ? `${props.id}-description`
                : undefined
            }
          />
        </div>
        {errors && errors.length > 0 && (
          <FormError errors={errors} />
        )}
        {helperText && (!errors || errors.length === 0) && (
          <p className="mt-1 text-sm text-gray-500" id={`${props.id}-description`}>
            {helperText}
          </p>
        )}
      </div>
    );
  }
);

Input.displayName = 'Input';

export default Input;
