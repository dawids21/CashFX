package xyz.stasiak.cashfx.account;

interface AccountType {

    int getWithdrawCost();

    int getLoanCost(int credit);

    int getTransferCost();

    int getDepositGain(int deposit);

}
