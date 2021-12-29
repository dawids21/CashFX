package xyz.stasiak.cashfx;

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
import xyz.stasiak.cashfx.context.ApplicationContext;
import xyz.stasiak.cashfx.user.UserApplicationService;
import xyz.stasiak.cashfx.user.UserReadModel;
import xyz.stasiak.cashfx.user.exception.UserPasswordIncorrect;

import java.io.IOException;

public class ChooseUserController {

    private final UserApplicationService userApplicationService;
    private final AccountApplicationService accountApplicationService;
    private final ApplicationState applicationState;
    private final ObservableList<UserReadModel> observableUserList = FXCollections.observableArrayList();
    @FXML
    private ListView<UserReadModel> userList;

    public ChooseUserController() {
        userApplicationService = ApplicationContext.CONTEXT.getBean(UserApplicationService.class);
        accountApplicationService = ApplicationContext.CONTEXT.getBean(AccountApplicationService.class);
        applicationState = ApplicationContext.CONTEXT.getBean(ApplicationState.class);
    }

    @FXML
    void initialize() {
        observableUserList.addAll(userApplicationService.getAllUsers());
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
                userApplicationService.login(userId, password.get());
                applicationState.setUserId(userId);
                var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                var fxmlLoader = new FXMLLoader(getClass().getResource("choose-account-view.fxml"));
                var scene = new Scene(fxmlLoader.load());
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
    void onCreateButtonAction() throws IOException {
        var dialog = new CreateUserDialog();
        dialog.setTitle("Create user");
        var newUser = dialog.showAndWait();
        if (newUser.isPresent()) {
            var userId = userApplicationService.create(newUser.get().name(), newUser.get().password());
            observableUserList.add(userApplicationService.getById(userId));
        }
    }

    @FXML
    void onRenameButtonAction() {
        var user = userList.getSelectionModel().getSelectedItem();

        var dialog = new TextInputDialog(user.name());
        dialog.setTitle("Rename user");
        dialog.setHeaderText("Rename user");
        dialog.setContentText("Name:");
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if ("".equals(dialog.getEditor().getText())) {
                event.consume();
            }
        });
        dialog.showAndWait().ifPresent(name -> {
            userApplicationService.rename(user.id(), name);
            observableUserList.remove(user);
            observableUserList.add(userApplicationService.getById(user.id()));
        });
    }

    @FXML
    void onChangePasswordButtonAction() {

    }

    @FXML
    void onDeleteButtonAction() throws IOException {
        if (userList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        var user = userList.getSelectionModel().getSelectedItem();
        if (accountApplicationService.checkIfHasMoneyOnAccounts(user.id())) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete user");
            alert.setHeaderText("Delete user");
            alert.setContentText("Some accounts have some money or charges. Deletion is not possible!");
            alert.show();
            return;
        }
        var dialog = new PasswordInputDialog();
        dialog.setTitle("Delete account");
        dialog.setHeaderText("Write password to delete account");
        dialog.setContentText("Password: ");
        var password = dialog.showAndWait();
        if (password.isPresent()) {
            try {
                userApplicationService.delete(user.id(), password.get());
                accountApplicationService.deleteForUser(user.id());
                observableUserList.remove(user);
            } catch (UserPasswordIncorrect passwordIncorrect) {
                var alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete account");
                alert.setHeaderText("Invalid password");
                alert.setContentText(passwordIncorrect.getMessage());
                alert.show();
            }
        }
    }
}