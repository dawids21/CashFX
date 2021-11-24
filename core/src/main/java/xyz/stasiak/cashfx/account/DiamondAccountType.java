package xyz.stasiak.cashfx.account;

class DiamondAccountType implements AccountType {

    @Override
    public int getWithdrawCost() {
        return 0;
    }

    @Override
    public int getLoanCost(int credit) {
        return (int) (credit * 0.01);
    }

    @Override
    public int getTransferCost() {
        return 0;
    }

    @Override
    public int getDepositGain(int deposit) {
        return (int) (deposit * 0.1);
    }

}
