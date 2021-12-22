package xyz.stasiak.cashfx;

import io.vavr.Tuple;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.account.AccountApplicationService;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserApplicationService;

import java.io.IOException;

public class AccountDetailsController {

    private final ApplicationState applicationState;
    private final UserApplicationService userApplicationService;
    private final AccountApplicationService accountApplicationService;

    @FXML
    private Label nameLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label chargeLabel;
    @FXML
    private Label typeLabel;

    public AccountDetailsController() {
        applicationState = ApplicationContext.CONTEXT.getBean(ApplicationState.class);
        accountApplicationService = ApplicationContext.CONTEXT.getBean(AccountApplicationService.class);
        userApplicationService = ApplicationContext.CONTEXT.getBean(UserApplicationService.class);
    }

    @FXML
    void initialize() {
        refreshAccount();
    }

    @FXML
    void onLogoutButtonAction(ActionEvent event) throws IOException {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout");
        alert.setContentText("Do you want to log out?");
        var result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() != ButtonType.OK) {
                return;
            }
            applicationState.setAccountId(null);
            applicationState.setUserId(null);
            var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            var fxmlLoader = new FXMLLoader(getClass().getResource("choose-user-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setScene(scene);
        }
    }

    @FXML
    void onChangeAccountButtonAction(ActionEvent event) throws IOException {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change account");
        alert.setHeaderText("Change account");
        alert.setContentText("Do you want to change account?");
        var result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() != ButtonType.OK) {
                return;
            }
            applicationState.setAccountId(null);
            var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            var fxmlLoader = new FXMLLoader(getClass().getResource("choose-account-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setScene(scene);
        }
    }

    @FXML
    void onTransferButtonAction() throws IOException {
        var usernames = userApplicationService.getAllUsers().toMap(user -> Tuple.of(user.id(), String.format("%s %s", user.name(), user.surname())));
        var accountNameReadModels = accountApplicationService.readAccountNames()
                .filter(account -> account.id() != applicationState.getAccountId())
                .map(account -> new AccountAmountDialog.UserAccountData(account.id(), account.name(), usernames.get(account.userId()).getOrElse("")))
                .toJavaList();
        var dialog = new AccountAmountDialog();
        dialog.setTitle("Transfer money");
        dialog.setHeaderText("Transfer money");
        dialog.setAccounts(accountNameReadModels);
        dialog.showAndWait().ifPresent(tuple -> {
            var accountId = tuple._1.accountId();
            var amount = tuple._2;
            accountApplicationService.makeTransfer(applicationState.getAccountId(), accountId, amount);
            refreshAccount();
        });
    }

    @FXML
    void onPayChargeButtonAction() {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pay charges");
        alert.setHeaderText("Pay charges");
        alert.setContentText("Do you want to pay your charges?");
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType != ButtonType.OK) {
                return;
            }
            accountApplicationService.payCharge(applicationState.getAccountId());
            refreshAccount();
        });
    }

    @FXML
    void onTakeLoanButtonAction() {

    }

    @FXML
    void onWithdrawButtonAction() {

    }

    @FXML
    void onDepositButtonAction() {
        var dialog = new TextInputDialog("0");
        dialog.setTitle("Deposit money");
        dialog.setHeaderText("Deposit money");
        dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ((StringProperty) observable).setValue(newValue.replaceAll("[^\\d]", ""));
            }
        });
        dialog.showAndWait().ifPresent(amountString -> {
            var amount = Integer.parseInt(amountString);
            accountApplicationService.deposit(applicationState.getAccountId(), amount);
            refreshAccount();
        });
    }

    @FXML
    void onOpenDepositButtonAction() {

    }

    private void refreshAccount() {
        var accountId = applicationState.getAccountId();
        var account = accountApplicationService.readAccount(accountId);
        nameLabel.setText(account.name());
        moneyLabel.setText(String.format("%d", account.money()));
        chargeLabel.setText(String.format("%d", account.charge()));
        typeLabel.setText(account.type().name());
    }
}
