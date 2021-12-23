package xyz.stasiak.cashfx.user;

import io.vavr.collection.List;
import io.vavr.control.Option;

interface UserRepository {

    List<UserReadModel> getAll();

    Option<UserReadModel> getById(int id);

    User save(User user);

    Option<User> getEntityById(int id);

}
