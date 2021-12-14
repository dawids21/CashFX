package xyz.stasiak.cashfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import xyz.stasiak.cashfx.account.AccountApplicationService;
import xyz.stasiak.cashfx.context.ApplicationContext;

public class AccountDetailsController {

    private final ApplicationState applicationState;
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
    }

    @FXML
    void initialize() {
        var accountId = applicationState.getAccountId();
        var account = accountApplicationService.readAccount(accountId);
        nameLabel.setText(account.name());
        moneyLabel.setText(String.format("%d", account.money()));
        chargeLabel.setText(String.format("%d", account.charge()));
        typeLabel.setText(account.type().name());
    }

    @FXML
    void onLogoutButtonAction() {

    }

    @FXML
    void onChangeAccountButtonAction() {

    }

    @FXML
    void onTransferButtonAction() {

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
}
