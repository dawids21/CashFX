package xyz.stasiak.cashfx.account;

class AccountFactory {

    Account basic(String name) {
        return new Account(null, name, 0, 0, new BasicAccountType());
    }

    Account bronze(String name) {
        return new Account(null, name, 0, 0, new BronzeAccountType());
    }

    Account silver(String name) {
        return new Account(null, name, 0, 0, new SilverAccountType());
    }

    Account gold(String name) {
        return new Account(null, name, 0, 0, new GoldAccountType());
    }

    Account diamond(String name) {
        return new Account(null, name, 0, 0, new DiamondAccountType());
    }

}
