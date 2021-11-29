package xyz.stasiak.cashfx.user;

import xyz.stasiak.cashfx.user.exception.UserNotFound;

public class UserApplicationService {

    private final UserRepository repository;

    UserApplicationService(UserRepository repository) {
        this.repository = repository;
    }

    User create(String name, String surname) {
        var user = User.create(name, surname);

        return repository.save(user);
    }

    UserReadModel getById(Integer id) {
        return repository.getById(id)
                .getOrElseThrow(() -> new UserNotFound(id));
    }

}
