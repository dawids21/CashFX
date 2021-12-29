package xyz.stasiak.cashfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.account.AccountApplicationService;
import xyz.stasiak.cashfx.account.AccountReadModel;
import xyz.stasiak.cashfx.context.ApplicationContext;

import java.io.IOException;

public class ChooseAccountController {
    private final AccountApplicationService service;
    private final ApplicationState applicationState;

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
        var observableList = FXCollections.observableArrayList(service.getUserAccounts(userId));
        accountsTable.setItems(observableList);
        nameTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(cellDataFeatures.getValue().name()));
        moneyTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(String.format("%d", cellDataFeatures.getValue().money())));
        typeTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(cellDataFeatures.getValue().type().name()));
    }

    @FXML
    void onChooseAccountButtonAction(ActionEvent event) throws IOException {
        if (accountsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        var accountId = accountsTable.getSelectionModel().getSelectedItem().id();
        applicationState.setAccountId(accountId);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        var fxmlLoader = new FXMLLoader(getClass().getResource("account-details-view.fxml"));
        var scene = new Scene(fxmlLoader.load(), 600, 400);
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
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setScene(scene);
        }
    }
}
