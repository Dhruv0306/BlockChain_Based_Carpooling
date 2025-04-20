import { API_BASE_URL } from '../constants';

const TOKEN_KEY = 'auth_token';

export interface AuthResponse {
  token: string;
  user: {
    id: string;
    name: string;
    email: string;
    driverLicense?: string;
  };
}

class AuthService {
  private token: string | null = null;

  constructor() {
    this.token = localStorage.getItem(TOKEN_KEY);
  }

  getToken(): string | null {
    return this.token;
  }

  isAuthenticated(): boolean {
    return !!this.token;
  }

  async login(email: string, password: string): Promise<AuthResponse> {
    try {
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        throw new Error('Invalid credentials');
      }

      const data: AuthResponse = await response.json();
      this.setToken(data.token);
      return data;
    } catch (error) {
      throw new Error('Login failed');
    }
  }

  async register(userData: {
    name: string;
    email: string;
    password: string;
    driverLicense?: string;
  }): Promise<AuthResponse> {
    try {
      const response = await fetch(`${API_BASE_URL}/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      if (!response.ok) {
        throw new Error('Registration failed');
      }

      const data: AuthResponse = await response.json();
      this.setToken(data.token);
      return data;
    } catch (error) {
      throw new Error('Registration failed');
    }
  }

  logout(): void {
    this.token = null;
    localStorage.removeItem(TOKEN_KEY);
  }

  private setToken(token: string): void {
    this.token = token;
    localStorage.setItem(TOKEN_KEY, token);
  }
}

export const authService = new AuthService();
