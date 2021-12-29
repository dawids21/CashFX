package xyz.stasiak.cashfx.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<UserReadModel> getAll();

    Optional<UserReadModel> getById(int id);

    User save(User user);

    Optional<User> getEntityById(int id);

}
