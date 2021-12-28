package xyz.stasiak.cashfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.Objects;

public class CreateUserDialog extends Dialog<CreateUserDialog.NewUserData> {

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    public CreateUserDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-user-dialog.fxml"));
        loader.setController(this);

        DialogPane dialogPane = loader.load();

        initModality(Modality.APPLICATION_MODAL);

        setResizable(true);
        setTitle("Dialog");
        setDialogPane(dialogPane);
        setResultConverter(buttonType -> {
            if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                return null;
            }

            return new NewUserData(nameField.getText(), passwordField.getText());
        });

        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (!isValid()) {
                event.consume();
            }
        });
        setOnShowing(dialogEvent -> Platform.runLater(() -> passwordField.requestFocus()));
    }

    private boolean isValid() {
        return !"".equals(nameField.getText()) && !"".equals(passwordField.getText()) && !"".equals(confirmPasswordField.getText()) && passwordField.getText().equals(confirmPasswordField.getText());
    }

    record NewUserData(String name, String password) {
    }

}
