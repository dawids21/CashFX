package xyz.stasiak.cashfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import xyz.stasiak.cashfx.account.AccountTypeReadModel;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountDialog extends Dialog<CreateAccountDialog.NewAccountData> {

    @FXML
    private TextField nameField;

    @FXML
    private ChoiceBox<AccountTypeReadModel> accountTypeField;

    public CreateAccountDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-account-dialog.fxml"));
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

            return new NewAccountData(nameField.getText(), accountTypeField.getSelectionModel().getSelectedItem());
        });

        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (!isValid()) {
                event.consume();
            }
        });
        setOnShowing(dialogEvent -> Platform.runLater(() -> nameField.requestFocus()));

        accountTypeField.setItems(FXCollections.observableArrayList(AccountTypeReadModel.values()));
    }

    private boolean isValid() {
        return !"".equals(nameField.getText()) && accountTypeField.getSelectionModel().getSelectedItem() != null;
    }

    record NewAccountData(String name, AccountTypeReadModel accountTypeReadModel) {
    }

}
