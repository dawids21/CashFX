package xyz.stasiak.cashfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.Objects;

public class PasswordInputDialog extends Dialog<String> {

    @FXML
    private PasswordField passwordField;

    public PasswordInputDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("password-input-dialog.fxml"));
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

            return passwordField.getText();
        });

        setOnShowing(dialogEvent -> Platform.runLater(() -> passwordField.requestFocus()));
        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (!isValid()) {
                event.consume();
            }
        });
    }

    private boolean isValid() {
        return !"".equals(passwordField.getText());
    }
}
