package xyz.stasiak.cashfx.user;

class User {
    private final String name;
    private Integer id;

    private User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    static User create(String name) {
        return new User(null, name);
    }

    void setId(Integer id) {
        this.id = id;
    }

    UserReadModel toReadModel() {
        return new UserReadModel(id, name);
    }
}
