package xyz.stasiak.cashfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import xyz.stasiak.cashfx.account.AccountApplicationService;
import xyz.stasiak.cashfx.account.AccountReadModel;
import xyz.stasiak.cashfx.context.ApplicationContext;

public class ChooseAccountController {
    private final ObservableList<AccountReadModel> observableList = FXCollections.observableArrayList();
    private final AccountApplicationService service;
    private final ApplicationState applicationState;

    @FXML
    private ListView<AccountReadModel> accountsList;

    public ChooseAccountController() {
        service = ApplicationContext.CONTEXT.getBean(AccountApplicationService.class);
        applicationState = ApplicationContext.CONTEXT.getBean(ApplicationState.class);
    }

    @FXML
    void initialize() {
        var userId = applicationState.getUserId();
        observableList.addAll(service.getUserAccounts(userId).toJavaList());
        accountsList.setItems(observableList);
        accountsList.setCellFactory(listView -> new AccountListCell());
    }

}
