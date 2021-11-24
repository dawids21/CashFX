package xyz.stasiak.cashfx.account;

import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.context.ContextConfiguration;

public class AccountConfig implements ContextConfiguration {

    @Override
    public void apply(ApplicationContext context) {
        context.register(AccountRepository.class, new InMemoryAccountRepository());
        context.register(AccountFactory.class, new AccountFactory());

        var accountRepository = context.getBean(AccountRepository.class);
        var accountFactory = context.getBean(AccountFactory.class);

        context.register(AccountApplicationService.class, new AccountApplicationService(accountRepository, accountFactory));
    }
}
