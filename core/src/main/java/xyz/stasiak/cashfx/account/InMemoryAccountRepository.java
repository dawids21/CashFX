package xyz.stasiak.cashfx.account;

import io.vavr.control.Option;

import java.util.HashMap;
import java.util.Map;

class InMemoryAccountRepository implements AccountRepository {

    private final Map<Integer, Account> accounts = new HashMap<>();
    private int nextId = 1;

    @Override
    public Account save(Account account) {

        account.setId(nextId);
        accounts.put(nextId, account);
        nextId += 1;
        return account;

    }

    @Override
    public Option<Account> getById(int id) {
        return Option.of(accounts.get(id));
    }
}
