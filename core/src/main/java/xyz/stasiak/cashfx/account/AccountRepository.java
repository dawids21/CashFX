package xyz.stasiak.cashfx.account;

import java.util.List;
import java.util.Optional;

interface AccountRepository {

    Account save(Account account);

    Optional<Account> getById(int id);

    Optional<AccountReadModel> getReadModelById(int id);

    List<AccountReadModel> getReadModelsByUserId(int userId);

    List<AccountNameReadModel> getNameReadModels();

}
