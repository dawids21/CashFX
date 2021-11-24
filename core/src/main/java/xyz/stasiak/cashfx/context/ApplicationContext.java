package xyz.stasiak.cashfx.context;

import io.vavr.collection.List;

import java.util.HashMap;
import java.util.Map;

public enum ApplicationContext {

    CONTEXT;

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public static ApplicationContext init(List<ContextConfiguration> configurations) {

        var context = CONTEXT;

        configurations.forEach(configuration -> configuration.apply(context));

        return context;
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }

    public <T> void register(Class<T> clazz, T bean) {
        beans.put(clazz, bean);
    }
}
