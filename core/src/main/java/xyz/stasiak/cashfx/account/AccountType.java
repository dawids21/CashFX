package xyz.stasiak.cashfx.account;

interface AccountType {

    int getWithdrawCost();

    int getCreditCost(int credit);

    int getTransferCost();

    int getDepositGain(int deposit);

}
