package xyz.stasiak.cashfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserApplicationService;
import xyz.stasiak.cashfx.user.UserReadModel;

public class CashFxController {

    private final ObservableList<UserReadModel> observableList = FXCollections.observableArrayList();
    @FXML
    private ListView<UserReadModel> userList;

    @FXML
    void initialize() {
        var service = ApplicationContext.CONTEXT.getBean(UserApplicationService.class);
        observableList.addAll(service.getAllUsers().toJavaList());
        userList.setItems(observableList);

        userList.setCellFactory(userReadModelListView -> new UserListCell());
    }

}