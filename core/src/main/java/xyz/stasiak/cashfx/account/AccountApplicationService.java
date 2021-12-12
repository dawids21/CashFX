package xyz.stasiak.cashfx.account;

import io.vavr.collection.List;
import xyz.stasiak.cashfx.account.exceptions.AccountNotFound;

public class AccountApplicationService {

    private final AccountRepository repository;
    private final AccountFactory factory;

    AccountApplicationService(AccountRepository repository, AccountFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public int openBasic(int userId, String name) {
        var account = factory.basic(name, userId);
        return repository.save(account).toReadModel().id();
    }

    public int openBronze(int userId, String name) {
        var account = factory.bronze(name, userId);
        return repository.save(account).toReadModel().id();
    }

    public int openSilver(int userId, String name) {
        var account = factory.silver(name, userId);
        return repository.save(account).toReadModel().id();
    }

    public int openGold(int userId, String name) {
        var account = factory.gold(name, userId);
        return repository.save(account).toReadModel().id();
    }

    public int openDiamond(int userId, String name) {
        var account = factory.diamond(name, userId);
        return repository.save(account).toReadModel().id();
    }

    public void makeTransfer(int from, int to, int amount) {
        var fromAccount = repository.getById(from).getOrElseThrow(() -> new AccountNotFound(from));
        var toAccount = repository.getById(to).getOrElseThrow(() -> new AccountNotFound(to));

        fromAccount.makeTransfer(amount, toAccount);

        repository.save(fromAccount);
        repository.save(toAccount);
    }

    public AccountReadModel readAccount(int id) {

        return repository.getReadModelById(id).getOrElseThrow(() -> new AccountNotFound(id));
    }

    public List<AccountReadModel> getUserAccounts(int userId) {
        return repository.getReadModelsByUserId(userId);
    }
}
