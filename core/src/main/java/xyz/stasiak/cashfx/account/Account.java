package xyz.stasiak.cashfx.account;

import xyz.stasiak.cashfx.account.exceptions.NotEnoughMoney;

class Account {
    private final String name;
    private final AccountType type;
    private final Integer userId;
    private Integer id;
    private int money;
    private int charge;

    Account(Integer id, Integer userId, String name, int money, int charge, AccountType type) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.charge = charge;
        this.type = type;
        this.userId = userId;
    }

    void setId(Integer id) {
        this.id = id;
    }

    void makeTransfer(int amount, Account to) {

        if (money < amount) {
            throw new NotEnoughMoney(name, amount);
        }

        charge += type.getTransferCost();
        to.money += amount;
        money -= amount;

    }

    void payCharge() {

        if (charge <= money) {
            money -= charge;
            charge = 0;
        } else {
            charge -= money;
            money = 0;
        }

    }

    void takeLoan(int amount) {

        money += amount;
        charge += type.getLoanCost(amount);

    }

    void withdraw(int amount) {
        if (money < amount) {
            throw new NotEnoughMoney(name, amount);
        }

        money -= amount;
        charge += type.getWithdrawCost();
    }

    void deposit(int amount) {
        money += amount;
    }

    void openDeposit(int amount) {
        if (money < amount) {
            throw new NotEnoughMoney(name, amount);
        }
        money += type.getDepositGain(amount);
    }

    AccountReadModel toReadModel() {
        return new AccountReadModel(id, userId, name, money, charge, type.toReadModel());
    }

    AccountNameReadModel toNameReadModel() {
        return new AccountNameReadModel(id, userId, name);
    }
}
