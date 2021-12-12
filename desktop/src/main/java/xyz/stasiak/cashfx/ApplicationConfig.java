package xyz.stasiak.cashfx;

import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.context.ContextConfiguration;

public class ApplicationConfig implements ContextConfiguration {
    @Override
    public void apply(ApplicationContext context) {
        context.register(ApplicationState.class, new ApplicationState());
    }
}
