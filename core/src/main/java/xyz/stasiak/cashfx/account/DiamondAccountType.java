package xyz.stasiak.cashfx.account;

import java.io.Serial;
import java.io.Serializable;

class DiamondAccountType implements AccountType, Serializable {

    @Serial
    private static final long serialVersionUID = 2431971992839555488L;

    @Override
    public AccountTypeReadModel toReadModel() {
        return AccountTypeReadModel.DIAMOND;
    }

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
