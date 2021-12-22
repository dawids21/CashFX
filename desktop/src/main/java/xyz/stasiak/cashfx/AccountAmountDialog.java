package xyz.stasiak.cashfx;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AccountAmountDialog extends Dialog<Tuple2<AccountAmountDialog.UserAccountData, Integer>> {

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

            return Tuple.of(account.getValue(), Integer.parseInt(amount.getText()));
        });

        setOnShowing(dialogEvent -> Platform.runLater(() -> amount.requestFocus()));

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

    record UserAccountData(int accountId, String accountName, String username) {
        @Override
        public String toString() {
            return String.format("%s - %s", username, accountName);
        }
    }
}
