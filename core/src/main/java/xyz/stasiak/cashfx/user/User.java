package xyz.stasiak.cashfx.user;

import java.util.Objects;

class User {
    private final String name;
    private final String password;
    private Integer id;

    private User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    static User create(String name, String password) {
        return new User(null, name, password);
    }

    void setId(Integer id) {
        this.id = id;
    }

    boolean isPasswordEqual(String password) {
        return Objects.nonNull(password) && password.equals(this.password);
    }

    UserReadModel toReadModel() {
        return new UserReadModel(id, name);
    }
}
