const Inventory = artifacts.require("Inventory");

module.exports = function (deployer) {
  deployer.deploy(Inventory);
};
