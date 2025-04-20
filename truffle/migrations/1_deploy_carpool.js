const CarpoolContract = artifacts.require("CarpoolContract");
const PaymentEscrow = artifacts.require("PaymentEscrow");
const ReputationToken = artifacts.require("ReputationToken");
const RideInsurance = artifacts.require("RideInsurance");

module.exports = function(deployer) {
  deployer.deploy(CarpoolContract)
    .then(() => deployer.deploy(PaymentEscrow))
    .then(() => deployer.deploy(ReputationToken))
    .then(() => deployer.deploy(RideInsurance));
}; 