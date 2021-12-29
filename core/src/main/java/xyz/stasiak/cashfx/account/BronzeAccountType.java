package xyz.stasiak.cashfx.account;

import java.io.Serial;
import java.io.Serializable;

class BronzeAccountType implements AccountType, Serializable {

    @Serial
    private static final long serialVersionUID = 4787108643998086353L;

    @Override
    public AccountTypeReadModel toReadModel() {
        return AccountTypeReadModel.BRONZE;
    }

    @Override
    public int getWithdrawCost() {
        return 0;
    }

    @Override
    public int getLoanCost(int credit) {
        return (int) (credit * 0.05);
    }

    @Override
    public int getTransferCost() {
        return 10;
    }

    @Override
    public int getDepositGain(int deposit) {
        return (int) (deposit * 0.01);
    }

}
