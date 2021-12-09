package xyz.stasiak.cashfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import xyz.stasiak.cashfx.user.UserReadModel;

import java.io.IOException;

public class UserListCell extends ListCell<UserReadModel> {

    private int userId;
    @FXML
    private Label username;

    public UserListCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user-list-cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(UserReadModel user, boolean empty) {
        super.updateItem(user, empty);

        if (empty || user == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            username.setText(String.format("%s %s", user.name(), user.surname()));
            userId = user.id();
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    int getUserId() {
        return userId;
    }
}
