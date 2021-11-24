package xyz.stasiak.cashfx.account;

import io.vavr.control.Option;

interface AccountRepository {

    Account save(Account account);

    Option<Account> getById(int id);

    Option<AccountReadModel> getReadModelById(int id);

}
