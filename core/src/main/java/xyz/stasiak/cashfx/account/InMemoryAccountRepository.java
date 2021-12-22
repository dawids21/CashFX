package xyz.stasiak.cashfx.account;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

class InMemoryAccountRepository implements AccountRepository {

    private Map<Integer, Account> accounts = HashMap.empty();
    private int nextId = 1;

    @Override
    public Account save(Account account) {

        if (account.getId() != null) {
            accounts.put(account.getId(), account);
            return account;
        }
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

    @Override
    public List<AccountReadModel> getReadModelsByUserId(int userId) {
        return accounts.values()
                .map(Account::toReadModel)
                .filter(account -> account.userId() == userId)
                .toList();
    }

    @Override
    public List<AccountNameReadModel> getNameReadModels() {
        return accounts.values()
                .map(Account::toNameReadModel)
                .toList();
    }
}
