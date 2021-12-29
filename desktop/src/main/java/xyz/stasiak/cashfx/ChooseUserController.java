package xyz.stasiak.cashfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserApplicationService;
import xyz.stasiak.cashfx.user.UserReadModel;
import xyz.stasiak.cashfx.user.exception.UserPasswordIncorrect;

import java.io.IOException;

public class ChooseUserController {

    private final UserApplicationService service;
    private final ApplicationState applicationState;
    private final ObservableList<UserReadModel> observableUserList = FXCollections.observableArrayList();
    @FXML
    private ListView<UserReadModel> userList;

    public ChooseUserController() {
        service = ApplicationContext.CONTEXT.getBean(UserApplicationService.class);
        applicationState = ApplicationContext.CONTEXT.getBean(ApplicationState.class);
    }

    @FXML
    void initialize() {
        observableUserList.addAll(service.getAllUsers());
        userList.setItems(observableUserList);
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
        if (userList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        var userId = userList.getSelectionModel().getSelectedItem().id();

        var dialog = new PasswordInputDialog();
        dialog.setTitle("Login");
        dialog.setHeaderText("Login");
        dialog.setContentText("Password: ");
        var password = dialog.showAndWait();
        if (password.isPresent()) {
            try {
                service.login(userId, password.get());
                applicationState.setUserId(userId);
                var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                var fxmlLoader = new FXMLLoader(getClass().getResource("choose-account-view.fxml"));
                var scene = new Scene(fxmlLoader.load(), 320, 240);
                stage.setScene(scene);
            } catch (UserPasswordIncorrect passwordIncorrect) {
                var alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login");
                alert.setHeaderText("Invalid password");
                alert.setContentText(passwordIncorrect.getMessage());
                alert.show();
            }
        }
    }

    @FXML
    void onCreateUserButtonAction() throws IOException {
        var dialog = new CreateUserDialog();
        dialog.setTitle("Create user");
        var newUser = dialog.showAndWait();
        if (newUser.isPresent()) {
            var userId = service.create(newUser.get().name(), newUser.get().password());
            observableUserList.add(service.getById(userId));
        }
    }
}