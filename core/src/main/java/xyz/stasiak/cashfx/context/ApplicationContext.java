package xyz.stasiak.cashfx.context;

import java.util.HashMap;
import java.util.Map;

public enum ApplicationContext {

    INSTANCE;

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }

    public <T> void register(Class<T> clazz, T bean) {
        beans.put(clazz, bean);
    }
}
