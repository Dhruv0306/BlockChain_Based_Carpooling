# Blockchain-Based P2P Carpooling System

A decentralized peer-to-peer carpooling system built on the Polygon Amoy Testnet with local PostgreSQL database integration.

## 🚀 Features

- **Smart Contract Integration**
  - Ride creation and management
  - Payment escrow system
  - Reputation token system
  - Insurance claims handling

- **User Management**
  - JWT-based authentication
  - User registration and profile management
  - Driving license verification
  - Reputation tracking

- **Ride Management**
  - Create and search rides
  - Request and accept ride offers
  - Real-time ride status updates
  - Payment processing

- **Security & Privacy**
  - Encrypted communication
  - Secure payment handling
  - Private key management
  - Data protection

## 🛠️ Tech Stack

- **Backend**
  - Spring Boot 3.2.2
  - Spring Security
  - Spring Data JPA
  - Web3j 4.9.8

- **Database**
  - PostgreSQL
  - Hibernate ORM

- **Blockchain**
  - Polygon Amoy Testnet
  - Solidity Smart Contracts
  - Web3j Integration

- **Security**
  - JWT Authentication
  - Spring Security
  - Environment-based Configuration

## 📋 Prerequisites

- Java 17 or higher
- PostgreSQL 14 or higher
- Node.js 16 or higher
- MetaMask or similar Web3 wallet
- Polygon Amoy Testnet account
- Ganache (for local development)

## 🔧 Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/BlockChain_Based_Carpooling.git
   cd BlockChain_Based_Carpooling
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE carpool_db;
   CREATE USER 'your_user' WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE carpool_db TO dhruv;
   ```

3. **Environment Configuration**
   - Copy `.env.example` to `.env`
   - Update the following in `.env`:
     ```bash
     # Database Configuration
     DB_URL=jdbc:postgresql://localhost:5432/carpool_db
     DB_USERNAME=your_username
     DB_PASSWORD=your_password

     # Blockchain Configuration
     ETHEREUM_NETWORK=http://localhost:7545  # Ganache
     PRIVATE_KEY=your_private_key
     ACCOUNT_ADDRESS=your_account_address

     # JWT Configuration
     JWT_SECRET=your_jwt_secret
     JWT_EXPIRATION=86400000  # 24 hours

     # Email Configuration
     SMTP_HOST=smtp.gmail.com
     SMTP_PORT=587
     SMTP_USERNAME=your_email
     SMTP_PASSWORD=your_app_password
     ```

4. **Smart Contract Deployment**
   ```bash
   cd src/main/solidity
   npm install
   truffle compile
   truffle migrate --network amoy
   ```

5. **Run the Application**
   ```bash
   ./gradlew bootRun
   ```

## 📝 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/carpool/
│   │       ├── config/        # Spring configurations
│   │       ├── controller/    # REST endpoints
│   │       ├── model/         # JPA entities
│   │       ├── repository/    # Data access layer
│   │       ├── service/       # Business logic
│   │       ├── contract/      # Web3j contract wrappers
│   │       └── util/          # Utility classes
│   ├── resources/
│   │   ├── static/           # Static resources
│   │   ├── templates/        # Thymeleaf templates
│   │   └── application.properties
│   └── solidity/             # Smart contracts
```

## 🔐 Security Best Practices

- **Environment Variables**
  - Never commit `.env` file
  - Use different keys for development and production
  - Rotate secrets regularly

- **Smart Contracts**
  - Implement access control
  - Use SafeMath for calculations
  - Include event logging
  - Test thoroughly before deployment

- **Application Security**
  - Validate all inputs
  - Use prepared statements
  - Implement rate limiting
  - Regular security audits

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot Team
- Web3j Team
- Polygon Network
- OpenZeppelin Contracts 
