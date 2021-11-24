package xyz.stasiak.cashfx.account;

public interface AccountRepository {

    Account save(Account account);

    int count();

    Account getById(int id);

}
