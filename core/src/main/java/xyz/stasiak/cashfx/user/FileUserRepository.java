package xyz.stasiak.cashfx.user;

import java.io.IOException;

public interface FileUserRepository extends UserRepository {

    void persist() throws IOException;

}
