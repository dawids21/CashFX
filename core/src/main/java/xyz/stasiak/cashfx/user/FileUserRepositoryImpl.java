package xyz.stasiak.cashfx.user;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class FileUserRepositoryImpl implements FileUserRepository {

    private static final String FILENAME = "users.dat";

    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    public FileUserRepositoryImpl() {
        var file = new File(FILENAME);
        if (file.exists()) {
            try {
                var fileIn = new FileInputStream(file);
                var objectIn = new ObjectInputStream(fileIn);
                var numOfUsers = (int) objectIn.readObject();
                for (int i = 0; i < numOfUsers; i++) {
                    var user = objectIn.readObject();
                    if (user instanceof User) {
                        users.put(((User) user).getId(), (User) user);
                    }
                }
                nextId = (int) objectIn.readObject();
            } catch (Exception e) {
                System.out.println("Can't load from file");
            }
        }
    }

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            users.put(user.getId(), user);
            return user;
        }
        user.setId(nextId);
        users.put(nextId, user);
        nextId += 1;
        return user;
    }

    @Override
    public Optional<UserReadModel> getById(int id) {
        return Optional.ofNullable(users.get(id)).map(User::toReadModel);
    }

    @Override
    public List<UserReadModel> getAll() {
        return users.values().stream().map(User::toReadModel).toList();
    }

    @Override
    public Optional<User> getEntityById(int id) {
        return Optional.ofNullable(users.get(id));
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
        objectOut.writeObject(users.size());
        for (var user : users.values()) {
            objectOut.writeObject(user);
        }
        objectOut.writeObject(nextId);
    }
}
