package xyz.stasiak.cashfx.account;

import java.io.IOException;

public interface FileAccountRepository extends AccountRepository {

    void persist() throws IOException;

}
