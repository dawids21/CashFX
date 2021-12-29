package xyz.stasiak.cashfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AccountAmountDialog extends Dialog<Pair<AccountAmountDialog.UserAccountData, Integer>> {

    @FXML
    private ChoiceBox<UserAccountData> account;

    @FXML
    private TextField amount;

    public AccountAmountDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("account-amount-dialog.fxml"));
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

            if (account.getValue() == null || amount.getText() == null || "".equals(amount.getText())) {
                return null;
            }

            return new Pair<>(account.getValue(), Integer.parseInt(amount.getText()));
        });

        setOnShowing(dialogEvent -> Platform.runLater(() -> amount.requestFocus()));
        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (!isValid()) {
                event.consume();
            }
        });

        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amount.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    void setAccounts(List<UserAccountData> accounts) {
        var list = FXCollections.observableArrayList(accounts);
        account.setItems(list);
    }

    private boolean isValid() {
        return account.getSelectionModel().getSelectedItem() != null && !"".equals(amount.getText());
    }

    record UserAccountData(int accountId, String accountName, String username) {
        @Override
        public String toString() {
            return String.format("%s - %s", username, accountName);
        }
    }
}
