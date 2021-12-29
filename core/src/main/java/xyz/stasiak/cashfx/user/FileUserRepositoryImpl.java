package xyz.stasiak.cashfx.user;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import java.io.*;

class FileUserRepositoryImpl implements FileUserRepository {

    private static final String FILENAME = "users.dat";

    private Map<Integer, User> users = HashMap.empty();
    private int nextId = 1;

    public FileUserRepositoryImpl() {
        var file = new File(getJarLocation() + "/" + FILENAME);
        if (!file.exists()) {
            try {
                var fileIn = new FileInputStream(file);
                var objectIn = new ObjectInputStream(fileIn);
                var users = objectIn.readObject();
                var nextId = objectIn.readObject();
                if (users instanceof Map<?, ?> && nextId instanceof Integer) {
                    this.users = (Map<Integer, User>) users;
                    this.nextId = (int) nextId;
                }
            } catch (Exception e) {
                System.out.println("Can't load from file");
            }
        }
    }

    @Override
    public User save(User user) {

        user.setId(nextId);
        users = users.put(nextId, user);
        nextId += 1;
        return user;

    }

    @Override
    public Option<UserReadModel> getById(int id) {
        return users.get(id).map(User::toReadModel);
    }

    @Override
    public List<UserReadModel> getAll() {
        return users.values().map(User::toReadModel).toList();
    }

    @Override
    public Option<User> getEntityById(int id) {
        return users.get(id);
    }

    @Override
    public void persist() throws IOException {
        var file = new File(getJarLocation() + "/" + FILENAME);
        if (!file.exists()) {
            file.createNewFile();
        }
        var fileOut = new FileOutputStream(file);
        var objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(users);
        objectOut.writeObject(nextId);
    }

    private String getJarLocation() {
        return getClass().getProtectionDomain().getCodeSource().getLocation().toString();
    }
}
