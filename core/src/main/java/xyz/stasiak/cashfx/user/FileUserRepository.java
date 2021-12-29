package xyz.stasiak.cashfx.user;

import java.io.IOException;

interface FileUserRepository extends UserRepository {

    void persist() throws IOException;

}
