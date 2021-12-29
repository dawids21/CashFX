package xyz.stasiak.cashfx.account;

import java.io.Serial;
import java.io.Serializable;

class BasicAccountType implements AccountType, Serializable {

    @Serial
    private static final long serialVersionUID = 577679776278916116L;

    @Override
    public AccountTypeReadModel toReadModel() {
        return AccountTypeReadModel.BASIC;
    }

    @Override
    public int getWithdrawCost() {
        return 5;
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
