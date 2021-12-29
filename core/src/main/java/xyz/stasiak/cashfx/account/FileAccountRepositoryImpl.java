package xyz.stasiak.cashfx.account;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class FileAccountRepositoryImpl implements FileAccountRepository {

    private static final String FILENAME = "accounts.dat";

    private final Map<Integer, Account> accounts = new HashMap<>();
    private int nextId = 1;

    public FileAccountRepositoryImpl() {
        var file = new File(FILENAME);
        if (file.exists()) {
            try {
                var fileIn = new FileInputStream(file);
                var objectIn = new ObjectInputStream(fileIn);
                var numOfAccounts = (int) objectIn.readObject();
                for (int i = 0; i < numOfAccounts; i++) {
                    var account = objectIn.readObject();
                    if (account instanceof Account) {
                        accounts.put(((Account) account).toReadModel().id(), (Account) account);
                    }
                }
                nextId = (int) objectIn.readObject();
            } catch (Exception e) {
                System.out.println("Can't load from file");
            }
        }
    }

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

    @Override
    public void persist() throws IOException {
        var file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        var fileOut = new FileOutputStream(file);
        var objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(accounts.size());
        for (var account : accounts.values()) {
            objectOut.writeObject(account);
        }
        objectOut.writeObject(nextId);
    }
}
