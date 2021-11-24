package xyz.stasiak.cashfx.account.exceptions;

public class AccountNotFound extends RuntimeException {

    public AccountNotFound(int id) {
        super(String.format("Account with id %d not found", id));
    }
}
