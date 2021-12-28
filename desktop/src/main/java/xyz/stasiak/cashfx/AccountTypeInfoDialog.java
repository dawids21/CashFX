package xyz.stasiak.cashfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import xyz.stasiak.cashfx.account.AccountTypeReadModel;

import java.io.IOException;

public class AccountTypeInfoDialog extends Alert {

    @FXML
    private Label transferCostLabel;
    @FXML
    private Label withdrawCostLabel;
    @FXML
    private Label loanInterestRateLabel;
    @FXML
    private Label depositInterestRateLabel;

    public AccountTypeInfoDialog(AccountTypeReadModel accountType) throws IOException {
        super(AlertType.INFORMATION);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("account-type-info-dialog.fxml"));
        loader.setController(this);

        DialogPane dialogPane = loader.load();

        initModality(Modality.APPLICATION_MODAL);

        setResizable(true);
        setTitle("Account type");
        setDialogPane(dialogPane);
        transferCostLabel.setText(accountType.getTransferCost());
        withdrawCostLabel.setText(accountType.getWithdrawCost());
        loanInterestRateLabel.setText(accountType.getLoanInterestRate());
        depositInterestRateLabel.setText(accountType.getDepositInterestRate());
    }
}
