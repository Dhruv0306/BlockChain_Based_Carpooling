import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import { LoadingProvider } from './contexts/LoadingContext';
import { ErrorProvider } from './contexts/ErrorContext';
import Home from './pages/Home';
import CreateRide from './pages/CreateRide';
import FindRide from './pages/FindRide';
import Login from './pages/Login';
import Register from './pages/Register';
import Profile from './pages/Profile';
import DLVerificationPage from './pages/DLVerificationPage';
import RideDetail from './pages/RideDetail';
import Settings from './pages/Settings';
import ProtectedRoute from './components/ProtectedRoute';
import { AuthProvider } from './contexts/AuthContext';

const App: React.FC = () => {
  return (
    <Router>
      <AuthProvider>
        <ErrorProvider>
          <LoadingProvider>
        <div className="flex flex-col min-h-screen bg-gray-100">
          <Navbar />
          <main className="container mx-auto px-4 py-8 flex-grow">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route
                path="/create-ride"
                element={
                  <ProtectedRoute>
                    <CreateRide />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/find-ride"
                element={
                  <ProtectedRoute>
                    <FindRide />
                  </ProtectedRoute>
                }
              />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route
                path="/verify-dl"
                element={
                  <ProtectedRoute>
                    <DLVerificationPage />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/rides/:id"
                element={
                  <ProtectedRoute>
                    <RideDetail />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/profile"
                element={
                  <ProtectedRoute>
                    <Profile />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/settings"
                element={
                  <ProtectedRoute>
                    <Settings />
                  </ProtectedRoute>
                }
              />
            </Routes>
          </main>
          <Footer />
        </div>
          </LoadingProvider>
        </ErrorProvider>
      </AuthProvider>
    </Router>
  );
};

export default App;
