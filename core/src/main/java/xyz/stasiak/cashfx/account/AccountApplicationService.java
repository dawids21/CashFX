package xyz.stasiak.cashfx.account;

import xyz.stasiak.cashfx.account.exceptions.AccountNotFound;

public class AccountApplicationService {

    private final AccountRepository repository;
    private final AccountFactory factory;

    AccountApplicationService(AccountRepository repository, AccountFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public Account openBasic(String name) {
        var account = factory.basic(name);
        return repository.save(account);
    }

    public Account openBronze(String name) {
        var account = factory.bronze(name);
        return repository.save(account);
    }

    public Account openSilver(String name) {
        var account = factory.silver(name);
        return repository.save(account);
    }

    public Account openGold(String name) {
        var account = factory.gold(name);
        return repository.save(account);
    }

    public Account openDiamond(String name) {
        var account = factory.diamond(name);
        return repository.save(account);
    }

    public void makeTransfer(int from, int to, int amount) {
        var fromAccount = repository.getById(from).getOrElseThrow(() -> new AccountNotFound(from));
        var toAccount = repository.getById(to).getOrElseThrow(() -> new AccountNotFound(to));

        fromAccount.makeTransfer(amount, toAccount);

        repository.save(fromAccount);
        repository.save(toAccount);
    }
}
