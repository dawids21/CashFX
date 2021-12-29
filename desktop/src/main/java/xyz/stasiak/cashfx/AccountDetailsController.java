package xyz.stasiak.cashfx;

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
import xyz.stasiak.cashfx.account.exceptions.NotEnoughMoney;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserApplicationService;
import xyz.stasiak.cashfx.user.UserReadModel;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Scene scene = new Scene(fxmlLoader.load());
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
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        }
    }

    @FXML
    void onTransferButtonAction() throws IOException {
        var usernames = userApplicationService.getAllUsers().stream().collect(Collectors.toMap(UserReadModel::id, UserReadModel::name));
        var accountNameReadModels = accountApplicationService.readAccountNames()
                .stream()
                .filter(account -> account.id() != applicationState.getAccountId())
                .map(account -> new AccountAmountDialog.UserAccountData(account.id(), account.name(), Optional.ofNullable(usernames.get(account.userId())).orElse("")))
                .collect(Collectors.toList());
        var dialog = new AccountAmountDialog();
        dialog.setTitle("Transfer");
        dialog.setHeaderText("Transfer money");
        dialog.setAccounts(accountNameReadModels);
        dialog.showAndWait().ifPresent(pair -> {
            var accountId = pair.getKey().accountId();
            var amount = pair.getValue();
            try {
                accountApplicationService.makeTransfer(applicationState.getAccountId(), accountId, amount);
            } catch (NotEnoughMoney notEnoughMoney) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Transfer");
                alert.setHeaderText("Not enough money");
                alert.setContentText(notEnoughMoney.getMessage());
                alert.show();
                return;
            }
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
        var dialog = getTextInputDialog("Take loan", "Take loan");
        dialog.showAndWait().ifPresent(amountString -> {
            var amount = Integer.parseInt(amountString);
            accountApplicationService.takeLoan(applicationState.getAccountId(), amount);
            refreshAccount();
        });
    }

    @FXML
    void onWithdrawButtonAction() {
        var dialog = getTextInputDialog("Withdraw", "Withdraw money");
        dialog.showAndWait().ifPresent(amountString -> {
            var amount = Integer.parseInt(amountString);
            try {
                accountApplicationService.withdraw(applicationState.getAccountId(), amount);
            } catch (NotEnoughMoney notEnoughMoney) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Withdraw");
                alert.setHeaderText("Not enough money");
                alert.setContentText(notEnoughMoney.getMessage());
                alert.show();
                return;
            }
            refreshAccount();
        });
    }

    @FXML
    void onDepositButtonAction() {
        var dialog = getTextInputDialog("Deposit", "Deposit money");
        dialog.showAndWait().ifPresent(amountString -> {
            var amount = Integer.parseInt(amountString);
            accountApplicationService.deposit(applicationState.getAccountId(), amount);
            refreshAccount();
        });
    }

    @FXML
    void onOpenDepositButtonAction() {
        var dialog = getTextInputDialog("Open deposit", "Open deposit");
        dialog.showAndWait().ifPresent(amountString -> {
            var amount = Integer.parseInt(amountString);
            try {
                accountApplicationService.openDeposit(applicationState.getAccountId(), amount);
            } catch (NotEnoughMoney notEnoughMoney) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Open deposit");
                alert.setHeaderText("Not enough money");
                alert.setContentText(notEnoughMoney.getMessage());
                alert.show();
                return;
            }
            refreshAccount();
        });
    }

    @FXML
    void onInformationButtonAction() throws IOException {
        var dialog = new AccountTypeInfoDialog(accountApplicationService.readAccount(applicationState.getAccountId()).type());
        dialog.show();
    }

    private TextInputDialog getTextInputDialog(String title, String header) {
        var dialog = new TextInputDialog("0");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("Amount:");
        dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ((StringProperty) observable).setValue(newValue.replaceAll("[^\\d]", ""));
            }
        });
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if ("".equals(dialog.getEditor().getText())) {
                event.consume();
            }
        });
        return dialog;
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
