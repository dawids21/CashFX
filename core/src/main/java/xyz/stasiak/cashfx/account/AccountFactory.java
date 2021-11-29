package xyz.stasiak.cashfx.account;

class AccountFactory {

    Account basic(String name, int userId) {
        return new Account(null, userId, name, 0, 0, new BasicAccountType());
    }

    Account bronze(String name, int userId) {
        return new Account(null, userId, name, 0, 0, new BronzeAccountType());
    }

    Account silver(String name, int userId) {
        return new Account(null, userId, name, 0, 0, new SilverAccountType());
    }

    Account gold(String name, int userId) {
        return new Account(null, userId, name, 0, 0, new GoldAccountType());
    }

    Account diamond(String name, int userId) {
        return new Account(null, userId, name, 0, 0, new DiamondAccountType());
    }

}
