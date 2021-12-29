package xyz.stasiak.cashfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Objects;

public class ChangePasswordDialog extends Dialog<Pair<String, String>> {

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    public ChangePasswordDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("change-password-dialog.fxml"));
        loader.setController(this);

        DialogPane dialogPane = loader.load();

        initModality(Modality.APPLICATION_MODAL);

        setResizable(true);
        setTitle("Change password");
        setHeaderText("Change password");
        setDialogPane(dialogPane);
        setResultConverter(buttonType -> {
            if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                return null;
            }

            return new Pair<>(oldPasswordField.getText(), newPasswordField.getText());
        });

        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (!isValid()) {
                event.consume();
            }
        });
        setOnShowing(dialogEvent -> Platform.runLater(() -> oldPasswordField.requestFocus()));
    }

    private boolean isValid() {
        return !"".equals(oldPasswordField.getText()) && !"".equals(newPasswordField.getText()) && !"".equals(confirmNewPasswordField.getText()) && newPasswordField.getText().equals(confirmNewPasswordField.getText());
    }
}
