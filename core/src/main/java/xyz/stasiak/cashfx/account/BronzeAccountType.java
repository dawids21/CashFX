package xyz.stasiak.cashfx.account;

class BronzeAccountType implements AccountType {

    @Override
    public int getWithdrawCost() {
        return 0;
    }

    @Override
    public int getCreditCost(int credit) {
        return (int) (credit * 0.05);
    }

    @Override
    public int getTransferCost() {
        return 0;
    }

    @Override
    public int getDepositGain(int deposit) {
        return (int) (deposit * 0.01);
    }

}
