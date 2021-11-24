package xyz.stasiak.cashfx.account.exceptions;

public class NotEnoughMoney extends RuntimeException {

    public NotEnoughMoney(String name, int amount) {
        super(String.format("Account %s does not have enough (%d) money", name, amount));
    }
}
