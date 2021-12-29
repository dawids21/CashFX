package xyz.stasiak.cashfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.account.AccountApplicationService;
import xyz.stasiak.cashfx.account.AccountConfig;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserApplicationService;
import xyz.stasiak.cashfx.user.UserConfig;

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
        var userApplicationService = ApplicationContext.CONTEXT.getBean(UserApplicationService.class);
        var accountApplicationService = ApplicationContext.CONTEXT.getBean(AccountApplicationService.class);
        var userId = userApplicationService.create("Dawid Stasiak", "1234");
        var basic = accountApplicationService.openBasic(userId, "Basic");
        accountApplicationService.deposit(basic, 100);
        userId = userApplicationService.create("Jan Nowak", "1234");
        accountApplicationService.openSilver(userId, "Silver");

        FXMLLoader fxmlLoader = new FXMLLoader(CashFxApplication.class.getResource("choose-user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("CashFX");
        stage.setScene(scene);
        stage.show();
    }
}