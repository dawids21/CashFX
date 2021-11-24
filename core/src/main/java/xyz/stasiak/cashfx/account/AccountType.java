package xyz.stasiak.cashfx.account;

interface AccountType {

    AccountTypeReadModel toReadModel();

    int getWithdrawCost();

    int getLoanCost(int credit);

    int getTransferCost();

    int getDepositGain(int deposit);

}
