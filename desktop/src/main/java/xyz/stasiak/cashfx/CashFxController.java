package xyz.stasiak.cashfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CashFxController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}