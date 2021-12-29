package xyz.stasiak.cashfx.account;

import java.io.Serial;
import java.io.Serializable;

class GoldAccountType implements AccountType, Serializable {

    @Serial
    private static final long serialVersionUID = -4845465278445769820L;

    @Override
    public AccountTypeReadModel toReadModel() {
        return AccountTypeReadModel.GOLD;
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
        return (int) (deposit * 0.05);
    }

}
