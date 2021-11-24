package xyz.stasiak.cashfx.account;

public record AccountReadModel(int id, String name, int money, int charge, AccountTypeReadModel type) {
}
