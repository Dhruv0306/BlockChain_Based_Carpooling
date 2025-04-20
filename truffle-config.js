module.exports = {
  networks: {
    development: {
      host: "127.0.0.1",     // Localhost (Ganache)
      port: 7545,            // Default Ganache port
      network_id: "*",       // Any network
    }
  },
  compilers: {
    solc: {
      version: "0.8.0",      // Solidity version
      settings: {
        optimizer: {
          enabled: true,
          runs: 200
        }
      }
    }
  }
}; 