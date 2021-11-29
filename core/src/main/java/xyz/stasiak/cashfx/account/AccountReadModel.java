package xyz.stasiak.cashfx.account;

public record AccountReadModel(int id, int userId, String name, int money, int charge, AccountTypeReadModel type) {
}
