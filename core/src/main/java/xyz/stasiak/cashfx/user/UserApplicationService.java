package xyz.stasiak.cashfx.user;

import xyz.stasiak.cashfx.user.exception.UserNotFound;
import xyz.stasiak.cashfx.user.exception.UserPasswordIncorrect;

import java.util.List;

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
                .orElseThrow(() -> new UserNotFound(id));
    }

    public List<UserReadModel> getAllUsers() {
        return repository.getAll();
    }

    public void login(int userId, String password) {

        var user = repository.getEntityById(userId).orElseThrow(() -> new UserNotFound(userId));

        if (!user.isPasswordCorrect(password)) {
            throw new UserPasswordIncorrect(user.toReadModel().name());
        }

    }

    public void delete(int userId, String password) {
        var user = repository.getEntityById(userId).orElseThrow(() -> new UserNotFound(userId));

        if (!user.isPasswordCorrect(password)) {
            throw new UserPasswordIncorrect(user.toReadModel().name());
        }

        var result = repository.delete(userId);

        if (!result) {
            throw new UserNotFound(userId);
        }
    }

    public void rename(int userId, String newName) {
        var user = repository.getEntityById(userId).orElseThrow(() -> new UserNotFound(userId));
        user.rename(newName);
        repository.save(user);
    }

    public void changePassword(int userId, String oldPassword, String newPassword) {
        var user = repository.getEntityById(userId).orElseThrow(() -> new UserNotFound(userId));

        if (!user.isPasswordCorrect(oldPassword)) {
            throw new UserPasswordIncorrect(user.toReadModel().name());
        }

        user.changePassword(newPassword);
        repository.save(user);
    }
}
