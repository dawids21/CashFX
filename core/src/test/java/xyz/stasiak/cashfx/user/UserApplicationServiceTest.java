package xyz.stasiak.cashfx.user;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import xyz.stasiak.cashfx.user.exception.UserPasswordIncorrect;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserApplicationServiceTest {

    private final UserRepository repository = new InMemoryUserRepository();
    private final UserApplicationService service = new UserApplicationService(repository);

    @Test
    void should_allow_login_when_password_is_correct() {
        var user = repository.save(User.create("Test", "1234"));

        assertThatNoException().isThrownBy(() -> service.login(user.toReadModel().id(), "1234"));
    }

    @Test
    void should_throw_an_exception_when_password_is_incorrect() {
        var user = repository.save(User.create("Test", "1234"));

        assertThatThrownBy(() -> service.login(user.toReadModel().id(), "wrong password"))
                .isInstanceOf(UserPasswordIncorrect.class)
                .hasMessageContaining("Test", "is incorrect");
    }
}