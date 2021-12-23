package xyz.stasiak.cashfx.user;

import io.vavr.collection.List;
import xyz.stasiak.cashfx.user.exception.UserNotFound;

public class UserApplicationService {

    private final UserRepository repository;

    UserApplicationService(UserRepository repository) {
        this.repository = repository;
    }

    public int create(String name, String password) {
        var user = User.create(name, password);

        return repository.save(user).toReadModel().id();
    }

    public UserReadModel getById(Integer id) {
        return repository.getById(id)
                .getOrElseThrow(() -> new UserNotFound(id));
    }

    public List<UserReadModel> getAllUsers() {
        return repository.getAll();
    }

}
