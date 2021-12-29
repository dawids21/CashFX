package xyz.stasiak.cashfx.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @Override
    public User save(User user) {

        user.setId(nextId);
        users.put(nextId, user);
        nextId += 1;
        return user;
    }

    @Override
    public Optional<UserReadModel> getById(int id) {
        return Optional.ofNullable(users.get(id)).map(User::toReadModel);
    }

    @Override
    public List<UserReadModel> getAll() {
        return users.values().stream().map(User::toReadModel).toList();
    }

    @Override
    public Optional<User> getEntityById(int id) {
        return Optional.ofNullable(users.get(id));
    }
}
