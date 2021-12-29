package xyz.stasiak.cashfx.user;

import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.context.ContextConfiguration;

public class UserConfig implements ContextConfiguration {

    @Override
    public void apply(ApplicationContext context) {
        context.register(UserRepository.class, new FileUserRepositoryImpl());

        var repository = context.getBean(UserRepository.class);

        context.register(UserApplicationService.class, new UserApplicationService(repository));
    }

}
