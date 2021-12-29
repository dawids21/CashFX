package xyz.stasiak.cashfx.account;

import xyz.stasiak.cashfx.account.exceptions.AccountNotFound;

import java.util.List;

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
        var fromAccount = getAccount(from);
        var toAccount = getAccount(to);

        fromAccount.makeTransfer(amount, toAccount);

        repository.save(fromAccount);
        repository.save(toAccount);
    }

    public void payCharge(int accountId) {
        var account = getAccount(accountId);
        account.payCharge();
        repository.save(account);
    }

    public void takeLoan(int accountId, int amount) {
        var account = getAccount(accountId);
        account.takeLoan(amount);
        repository.save(account);
    }

    public void withdraw(int accountId, int amount) {
        var account = getAccount(accountId);
        account.withdraw(amount);
        repository.save(account);
    }

    public void deposit(int accountId, int amount) {
        var account = getAccount(accountId);
        account.deposit(amount);
        repository.save(account);
    }

    public void openDeposit(int accountId, int amount) {
        var account = getAccount(accountId);
        account.openDeposit(amount);
        repository.save(account);
    }

    public AccountReadModel readAccount(int id) {

        return repository.getReadModelById(id).orElseThrow(() -> new AccountNotFound(id));
    }

    public List<AccountReadModel> getUserAccounts(int userId) {
        return repository.getReadModelsByUserId(userId);
    }

    public List<AccountNameReadModel> readAccountNames() {
        return repository.getNameReadModels();
    }

    private Account getAccount(int accountId) {
        return repository.getById(accountId).orElseThrow(() -> new AccountNotFound(accountId));
    }

    public void delete(int accountId) {
        var result = repository.delete(accountId);

        if (!result) {
            throw new AccountNotFound(accountId);
        }
    }

    public void deleteForUser(int userId) {
        var accounts = repository.getReadModelsByUserId(userId);
        for (var account : accounts) {
            repository.delete(account.id());
        }
    }

    public void renameAccount(int accountId, String newName) {
        var account = repository.getById(accountId).orElseThrow(() -> new AccountNotFound(accountId));
        account.setName(newName);
        repository.save(account);
    }

    public boolean checkIfHasMoneyOnAccounts(int userId) {
        var accounts = repository.getReadModelsByUserId(userId);
        return accounts.stream().anyMatch(account -> account.money() > 0 || account.charge() > 0);
    }
}
