package xyz.stasiak.cashfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.account.AccountApplicationService;
import xyz.stasiak.cashfx.account.AccountReadModel;
import xyz.stasiak.cashfx.context.ApplicationContext;

import java.io.IOException;

public class ChooseAccountController {
    private final AccountApplicationService service;
    private final ApplicationState applicationState;
    private final ObservableList<AccountReadModel> observableAccountsList = FXCollections.observableArrayList();
    @FXML
    private TableView<AccountReadModel> accountsTable;
    @FXML
    private TableColumn<AccountReadModel, String> nameTableColumn;
    @FXML
    private TableColumn<AccountReadModel, String> moneyTableColumn;
    @FXML
    private TableColumn<AccountReadModel, String> typeTableColumn;

    public ChooseAccountController() {
        service = ApplicationContext.CONTEXT.getBean(AccountApplicationService.class);
        applicationState = ApplicationContext.CONTEXT.getBean(ApplicationState.class);
    }

    @FXML
    void initialize() {
        var userId = applicationState.getUserId();
        observableAccountsList.addAll(service.getUserAccounts(userId));
        accountsTable.setItems(observableAccountsList);
        nameTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(cellDataFeatures.getValue().name()));
        moneyTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(String.format("%d", cellDataFeatures.getValue().money())));
        typeTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(cellDataFeatures.getValue().type().name()));
    }

    @FXML
    void onOpenButtonAction(ActionEvent event) throws IOException {
        if (accountsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        var accountId = accountsTable.getSelectionModel().getSelectedItem().id();
        applicationState.setAccountId(accountId);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        var fxmlLoader = new FXMLLoader(getClass().getResource("account-details-view.fxml"));
        var scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
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
    void onCreateButtonAction() throws IOException {
        var dialog = new CreateAccountDialog();
        dialog.setTitle("Create account");
        var newAccount = dialog.showAndWait();
        if (newAccount.isPresent()) {
            var accountId = switch (newAccount.get().accountTypeReadModel()) {
                case BASIC -> service.openBasic(applicationState.getUserId(), newAccount.get().name());
                case BRONZE -> service.openBronze(applicationState.getUserId(), newAccount.get().name());
                case SILVER -> service.openSilver(applicationState.getUserId(), newAccount.get().name());
                case GOLD -> service.openGold(applicationState.getUserId(), newAccount.get().name());
                case DIAMOND -> service.openDiamond(applicationState.getUserId(), newAccount.get().name());
            };
            observableAccountsList.add(service.readAccount(accountId));
        }
    }

    @FXML
    void onModifyButtonAction() {

        var account = accountsTable.getSelectionModel().getSelectedItem();

        var dialog = new TextInputDialog(account.name());
        dialog.setTitle("Modify account");
        dialog.setHeaderText("Rename account");
        dialog.setContentText("Name:");
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if ("".equals(dialog.getEditor().getText())) {
                event.consume();
            }
        });
        dialog.showAndWait().ifPresent(name -> {
            service.renameAccount(account.id(), name);
            observableAccountsList.remove(account);
            observableAccountsList.add(service.readAccount(account.id()));
        });
    }

    @FXML
    void onDeleteButtonAction() {
        if (accountsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        var account = accountsTable.getSelectionModel().getSelectedItem();

        if (account.money() != 0 || account.charge() != 0) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete account");
            alert.setHeaderText("Delete account");
            alert.setContentText("You can't delete account with money or charges!");
            alert.show();
            return;
        }

        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete account");
        alert.setHeaderText("Delete account");
        alert.setContentText("Are you sure?");
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType != ButtonType.OK) {
                return;
            }
            service.delete(account.id());
            observableAccountsList.remove(account);
        });
    }
}
