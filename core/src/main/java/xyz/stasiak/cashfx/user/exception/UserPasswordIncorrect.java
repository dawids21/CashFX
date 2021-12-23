package xyz.stasiak.cashfx.user.exception;

public class UserPasswordIncorrect extends RuntimeException {

    public UserPasswordIncorrect(String username) {
        super(String.format("Password for user %s is incorrect", username));
    }
}
