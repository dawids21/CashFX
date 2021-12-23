package xyz.stasiak.cashfx.user;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

class InMemoryUserRepository implements UserRepository {

    private Map<Integer, User> users = HashMap.empty();
    private int nextId = 1;

    @Override
    public User save(User user) {

        user.setId(nextId);
        users = users.put(nextId, user);
        nextId += 1;
        return user;

    }

    @Override
    public Option<UserReadModel> getById(int id) {
        return users.get(id).map(User::toReadModel);
    }

    @Override
    public List<UserReadModel> getAll() {
        return users.values().map(User::toReadModel).toList();
    }

    @Override
    public Option<User> getEntityById(int id) {
        return users.get(id);
    }
}
