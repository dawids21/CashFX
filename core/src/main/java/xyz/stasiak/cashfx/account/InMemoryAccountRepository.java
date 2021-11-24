package xyz.stasiak.cashfx.account;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;

class InMemoryAccountRepository implements AccountRepository {

    private Map<Integer, Account> accounts = HashMap.empty();
    private int nextId = 1;

    @Override
    public Account save(Account account) {

        account.setId(nextId);
        accounts = accounts.put(nextId, account);
        nextId += 1;
        return account;

    }

    @Override
    public Option<Account> getById(int id) {
        return accounts.get(id);
    }

    @Override
    public Option<AccountReadModel> getReadModelById(int id) {
        return accounts.get(id).map(Account::toReadModel);
    }
}
