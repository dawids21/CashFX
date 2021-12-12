package xyz.stasiak.cashfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import xyz.stasiak.cashfx.account.AccountApplicationService;
import xyz.stasiak.cashfx.account.AccountReadModel;
import xyz.stasiak.cashfx.context.ApplicationContext;

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
        var observableList = FXCollections.observableArrayList(service.getUserAccounts(userId).toJavaList());
        accountsTable.setItems(observableList);
        nameTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(cellDataFeatures.getValue().name()));
        moneyTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(String.format("%d", cellDataFeatures.getValue().money())));
        typeTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(cellDataFeatures.getValue().type().name()));
    }

}
