# Blockchain-Based Carpooling Application

A decentralized carpooling platform built with Java Spring Boot, PostgreSQL, and Polygon Mumbai Testnet.

## Features

- User registration with Driving License validation
- Two user types: Posters (drivers) and Accepters (riders)
- Ride posting and acceptance system
- Blockchain-based ride and transaction recording
- Reputation system with upvoting/downvoting
- Email notifications for ride requests
- JWT-based authentication
- Admin dashboard
- Location-based ride search

## Tech Stack

- **Backend:** Java Spring Boot
- **Database:** PostgreSQL
- **Blockchain:** Polygon Mumbai Testnet (Web3j)
- **Frontend:** Spring Boot with Thymeleaf
- **Authentication:** JWT
- **Email:** SMTP

## Prerequisites

- Java 21 or higher
- PostgreSQL
- Maven or Gradle
- Polygon Mumbai Testnet wallet with MATIC tokens
- SMTP email account (for notifications)

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/Dhruv0306/BlockChain_Based_Carpooling.git
   cd blockchain-carpooling
   ```

2. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE carpooling;
   ```

3. Configure your environment variables:
   - Database credentials
   - JWT secret
   - Email credentials
   - Blockchain wallet details

4. Build the project:
   ```bash
   ./gradlew build
   ```

5. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## Smart Contract Deployment

1. Deploy the smart contract to Polygon Mumbai Testnet
2. Update the contract address in your environment configuration
3. Ensure your wallet has sufficient MATIC tokens for gas fees

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/validate-dl` - Validate driving license

### Rides
- `POST /api/rides` - Create a new ride
- `GET /api/rides` - List all rides
- `GET /api/rides/{id}` - Get ride details
- `POST /api/rides/{id}/accept` - Accept a ride
- `POST /api/rides/{id}/complete` - Complete a ride
- `POST /api/rides/{id}/vote` - Vote on a ride

### User
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile
- `GET /api/users/reputation` - Get user reputation

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Boot team
- Web3j team
- Polygon team
- All contributors 