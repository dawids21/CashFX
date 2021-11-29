package xyz.stasiak.cashfx;

import io.vavr.collection.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.account.AccountConfig;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserConfig;

import java.io.IOException;

public class CashFxApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationContext.init(List.of(
                new AccountConfig(), new UserConfig()
        ));
        FXMLLoader fxmlLoader = new FXMLLoader(CashFxApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("CashFX");
        stage.setScene(scene);
        stage.show();
    }
}