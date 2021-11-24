package xyz.stasiak.cashfx.account;

import io.vavr.control.Option;

public interface AccountRepository {

    Account save(Account account);

    Option<Account> getById(int id);

}
