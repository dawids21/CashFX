package xyz.stasiak.cashfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserApplicationService;
import xyz.stasiak.cashfx.user.UserReadModel;

import java.io.IOException;

public class ChooseUserController {

    private final UserApplicationService service;
    private final ApplicationState applicationState;
    @FXML
    private ListView<UserReadModel> userList;

    public ChooseUserController() {
        service = ApplicationContext.CONTEXT.getBean(UserApplicationService.class);
        applicationState = ApplicationContext.CONTEXT.getBean(ApplicationState.class);
    }

    @FXML
    void initialize() {
        var observableList = FXCollections.observableArrayList(service.getAllUsers().toJavaList());
        userList.setItems(observableList);
        userList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(UserReadModel user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.name());
                }
            }
        });
    }

    @FXML
    void onLoginButtonAction(ActionEvent event) throws IOException {
        var userId = userList.getSelectionModel().getSelectedItem().id();
        applicationState.setUserId(userId);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        var fxmlLoader = new FXMLLoader(getClass().getResource("choose-account-view.fxml"));
        var scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
    }

}