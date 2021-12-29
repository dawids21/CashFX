package xyz.stasiak.cashfx.account;

import java.io.Serial;
import java.io.Serializable;

class SilverAccountType implements AccountType, Serializable {

    @Serial
    private static final long serialVersionUID = 8017671624491355606L;

    @Override
    public AccountTypeReadModel toReadModel() {
        return AccountTypeReadModel.SILVER;
    }

    @Override
    public int getWithdrawCost() {
        return 0;
    }

    @Override
    public int getLoanCost(int credit) {
        return (int) (credit * 0.02);
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
