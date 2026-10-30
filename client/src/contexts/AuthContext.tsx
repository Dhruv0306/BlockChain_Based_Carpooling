import React, { createContext, useContext, useState, useEffect } from 'react';
import { authService, AuthResponse } from '../services/auth.service';
import { User } from '../types';

interface AuthContextType {
  user: User | null;
  loading: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (userData: {
    name: string;
    email: string;
    password: string;
    driverLicense?: string;
  }) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = authService.getToken();
    if (token) {
      setIsAuthenticated(true);
      // TODO: Fetch user data using token
    }
    setLoading(false);
  }, []);

  const login = async (email: string, password: string) => {
    try {
      const response = await authService.login(email, password);
      setUser(response.user);
      setIsAuthenticated(true);
    } catch (error) {
      throw error;
    }
  };

  const register = async (userData: {
    name: string;
    email: string;
    password: string;
    driverLicense?: string;
  }) => {
    try {
      const response = await authService.register(userData);
      setUser(response.user);
      setIsAuthenticated(true);
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    authService.logout();
    setUser(null);
    setIsAuthenticated(false);
  };

  const value = {
    user,
    loading,
    login,
    register,
    logout,
    isAuthenticated,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
