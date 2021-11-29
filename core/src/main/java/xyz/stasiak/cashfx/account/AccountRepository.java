package xyz.stasiak.cashfx.account;

import io.vavr.collection.List;
import io.vavr.control.Option;

interface AccountRepository {

    Account save(Account account);

    Option<Account> getById(int id);

    Option<AccountReadModel> getReadModelById(int id);

    List<AccountReadModel> getReadModelsByUserId(int userId);

}
