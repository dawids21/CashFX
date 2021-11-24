package xyz.stasiak.cashfx.account;

class AccountFactory {

    Account basic(int id, String name) {
        return new Account(id, name, 0, 0, new BasicAccountType());
    }

    Account bronze(int id, String name) {
        return new Account(id, name, 0, 0, new BronzeAccountType());
    }

    Account silver(int id, String name) {
        return new Account(id, name, 0, 0, new SilverAccountType());
    }

    Account gold(int id, String name) {
        return new Account(id, name, 0, 0, new GoldAccountType());
    }

    Account diamond(int id, String name) {
        return new Account(id, name, 0, 0, new DiamondAccountType());
    }

}
