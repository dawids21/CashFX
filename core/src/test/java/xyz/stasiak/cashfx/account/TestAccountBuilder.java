package xyz.stasiak.cashfx.account;

class TestAccountBuilder {

    private int id = 1;
    private String name = "test";
    private int money = 0;
    private int charge = 0;
    private AccountType type = new BronzeAccountType();

    TestAccountBuilder() {
    }

    TestAccountBuilder id(int id) {
        this.id = id;
        return this;
    }

    TestAccountBuilder name(String name) {
        this.name = name;
        return this;
    }

    TestAccountBuilder money(int money) {
        this.money = money;
        return this;
    }

    TestAccountBuilder charge(int charge) {
        this.charge = charge;
        return this;
    }

    TestAccountBuilder type(AccountType type) {
        this.type = type;
        return this;
    }

    Account build() {
        return new Account(id, name, money, charge, type);
    }
}
