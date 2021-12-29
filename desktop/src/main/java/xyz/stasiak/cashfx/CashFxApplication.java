package xyz.stasiak.cashfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.account.AccountConfig;
import xyz.stasiak.cashfx.account.AccountRepository;
import xyz.stasiak.cashfx.account.FileAccountRepository;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.FileUserRepository;
import xyz.stasiak.cashfx.user.UserConfig;
import xyz.stasiak.cashfx.user.UserRepository;

import java.io.IOException;
import java.util.List;

public class CashFxApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationContext.init(List.of(
                new AccountConfig(), new UserConfig(), new ApplicationConfig()
        ));
        FXMLLoader fxmlLoader = new FXMLLoader(CashFxApplication.class.getResource("choose-user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("CashFX");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        var userRepository = ApplicationContext.CONTEXT.getBean(UserRepository.class);
        if (userRepository instanceof FileUserRepository) {
            ((FileUserRepository) userRepository).persist();
        }

        var accountRepository = ApplicationContext.CONTEXT.getBean(AccountRepository.class);
        if (accountRepository instanceof FileAccountRepository) {
            ((FileAccountRepository) accountRepository).persist();
        }
    }
}