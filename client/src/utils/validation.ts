export interface ValidationRule {
  test: (value: any) => boolean;
  message: string;
}

export interface ValidationRules {
  [key: string]: ValidationRule[];
}

export interface ValidationErrors {
  [key: string]: string[];
}

// Common validation rules
export const required = (fieldName: string): ValidationRule => ({
  test: (value: any) => {
    if (Array.isArray(value)) return value.length > 0;
    if (typeof value === 'string') return value.trim().length > 0;
    return value !== null && value !== undefined;
  },
  message: `${fieldName} is required`
});

export const minLength = (length: number, fieldName: string): ValidationRule => ({
  test: (value: string) => value.length >= length,
  message: `${fieldName} must be at least ${length} characters`
});

export const maxLength = (length: number, fieldName: string): ValidationRule => ({
  test: (value: string) => value.length <= length,
  message: `${fieldName} must not exceed ${length} characters`
});

export const email = (): ValidationRule => ({
  test: (value: string) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value),
  message: 'Please enter a valid email address'
});

export const password = (): ValidationRule => ({
  test: (value: string) => /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(value),
  message: 'Password must contain at least 8 characters, including uppercase, lowercase, and numbers'
});

export const phone = (): ValidationRule => ({
  test: (value: string) => /^\+?[\d\s-]{10,}$/.test(value),
  message: 'Please enter a valid phone number'
});

export const numeric = (fieldName: string): ValidationRule => ({
  test: (value: string) => /^\d+$/.test(value),
  message: `${fieldName} must contain only numbers`
});

export const match = (matchValue: any, fieldName: string): ValidationRule => ({
  test: (value: any) => value === matchValue,
  message: `${fieldName} does not match`
});

// Validate a single field
export const validateField = (value: any, rules: ValidationRule[]): string[] => {
  return rules
    .filter(rule => !rule.test(value))
    .map(rule => rule.message);
};

// Validate an entire form
export const validateForm = (values: { [key: string]: any }, rules: ValidationRules): ValidationErrors => {
  const errors: ValidationErrors = {};
  
  Object.keys(rules).forEach(fieldName => {
    const fieldErrors = validateField(values[fieldName], rules[fieldName]);
    if (fieldErrors.length > 0) {
      errors[fieldName] = fieldErrors;
    }
  });
  
  return errors;
};

// Check if form has any errors
export const hasErrors = (errors: ValidationErrors): boolean => {
  return Object.keys(errors).length > 0;
};

// Format error messages for display
export const formatErrors = (errors: string[]): string => {
  return errors.join(', ');
};
