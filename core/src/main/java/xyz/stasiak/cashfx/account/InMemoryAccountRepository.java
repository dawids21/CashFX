package xyz.stasiak.cashfx.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class InMemoryAccountRepository implements AccountRepository {

    private final Map<Integer, Account> accounts = new HashMap<>();
    private int nextId = 1;

    @Override
    public Account save(Account account) {

        if (account.getId() != null) {
            accounts.put(account.getId(), account);
            return account;
        }
        account.setId(nextId);
        accounts.put(nextId, account);
        nextId += 1;
        return account;

    }

    @Override
    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public Optional<AccountReadModel> getReadModelById(int id) {
        return Optional.ofNullable(accounts.get(id)).map(Account::toReadModel);
    }

    @Override
    public List<AccountReadModel> getReadModelsByUserId(int userId) {
        return accounts.values()
                .stream()
                .map(Account::toReadModel)
                .filter(account -> account.userId() == userId)
                .toList();
    }

    @Override
    public List<AccountNameReadModel> getNameReadModels() {
        return accounts.values()
                .stream()
                .map(Account::toNameReadModel)
                .toList();
    }
}
