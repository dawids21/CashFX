package xyz.stasiak.cashfx;

import io.vavr.Tuple;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    void onLogoutButtonAction() {

    }

    @FXML
    void onChangeAccountButtonAction() {

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

    }

    @FXML
    void onTakeLoanButtonAction() {

    }

    @FXML
    void onWithdrawButtonAction() {

    }

    @FXML
    void onDepositButtonAction() {

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
