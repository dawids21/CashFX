package xyz.stasiak.cashfx.user;

import io.vavr.control.Option;

interface UserRepository {

    Option<UserReadModel> getById(int id);

    User save(User user);

}
