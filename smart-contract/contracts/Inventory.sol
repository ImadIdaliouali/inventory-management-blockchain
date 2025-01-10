// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

contract Inventory {
    struct Transaction {
        uint id;
        string productName;
        uint quantity;
        string action; // "ADD", "REMOVE", "TRANSFER"
        uint timestamp;
    }

    Transaction[] public transactions;
    uint public transactionCount;

    event TransactionAdded(
        uint id,
        string productName,
        uint quantity,
        string action,
        uint timestamp
    );

    function addTransaction(
        string memory _productName,
        uint _quantity,
        string memory _action
    ) public {
        transactionCount++;
        transactions.push(
            Transaction(
                transactionCount,
                _productName,
                _quantity,
                _action,
                block.timestamp
            )
        );
        emit TransactionAdded(
            transactionCount,
            _productName,
            _quantity,
            _action,
            block.timestamp
        );
    }

    function getTransaction(
        uint _id
    )
        public
        view
        returns (
            uint id,
            string memory productName,
            uint quantity,
            string memory action,
            uint timestamp
        )
    {
        require(_id > 0 && _id <= transactionCount, "Invalid transaction ID");
        Transaction memory transaction = transactions[_id - 1];
        return (
            transaction.id,
            transaction.productName,
            transaction.quantity,
            transaction.action,
            transaction.timestamp
        );
    }

    function getAllTransactions() public view returns (Transaction[] memory) {
        return transactions;
    }
}
