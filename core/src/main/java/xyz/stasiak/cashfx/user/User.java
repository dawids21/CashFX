package xyz.stasiak.cashfx.user;

class User {
    private final String name;
    private final String surname;
    private Integer id;

    private User(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    static User create(String name, String surname) {
        return new User(null, name, surname);
    }

    void setId(Integer id) {
        this.id = id;
    }

    UserReadModel toReadModel() {
        return new UserReadModel(id, name, surname);
    }
}
