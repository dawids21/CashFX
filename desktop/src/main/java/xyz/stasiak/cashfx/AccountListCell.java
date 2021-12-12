package xyz.stasiak.cashfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import xyz.stasiak.cashfx.account.AccountReadModel;

import java.io.IOException;

public class AccountListCell extends ListCell<AccountReadModel> {

    @FXML
    private Label name;
    @FXML
    private Label money;
    @FXML
    private Label type;

    public AccountListCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("account-list-cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(AccountReadModel account, boolean empty) {
        super.updateItem(account, empty);

        if (empty || account == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            name.setText(account.name());
            money.setText(String.format("%d", account.money()));
            type.setText(account.type().name());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
