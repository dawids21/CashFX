package xyz.stasiak.cashfx.user.exception;

public class UserNotFound extends RuntimeException {

    public UserNotFound(Integer id) {
        super(String.format("User with id %d not found", id));
    }
}
