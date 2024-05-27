package org.western.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.western.backend.Player;
import org.western.backend.Teacher;
import org.western.backend.Tester;

import java.io.IOException;

/**
 * Controller for the create user view.
 *
 * @author Chao Zhang
 * @author Enqin Liu
 */
public class CreateController {

    @FXML
    private Button finishButton;

    @FXML
    private TextField usernameField;

    private String keySequence = "";
    private int role = 1;

    /**
     * Start the create user view.
     *
     * @param stage the stage to display the view on
     * @throws IOException if the view file cannot be found
     */
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/western/frontend/create_view.fxml"));
        stage.setTitle("PhilosoFill");
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        usernameField = (TextField) root.lookup("#username");
        stage.show();
    }

    /**
     * Handle the finish button click event.
     *
     * @param event the event that triggered the handler
     */
    @FXML
    private void finishButtonClick(ActionEvent event) {
        try {
            String username = usernameField.getText();
            if (username.length() > 10 || username.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Username is too long or too short");
                alert.setContentText("Please enter a username with 1 to 10 characters.");
                alert.showAndWait();
                return;
            }
            int id = Player.checkPlayerExist(username);
            if (id == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Username is being used already");
                alert.setContentText("Please enter a new username.");
                alert.showAndWait();
            } else {
                Player.addUser(id, username, role, 1, "000000000000000000000000000000000000000000000");
                TransferDataUtils.CONTROLLER.put("player", Player.getPlayerById(id));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                HomeController home = new HomeController();
                home.start(stage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the back button click event.
     *
     * @param event the event that triggered the handler
     * @throws IOException if the view file cannot be found
     */
    @FXML
    private void backProfile(ActionEvent event) throws IOException {
        MainWindow profiles = new MainWindow();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        profiles.start(stage);
    }

    /**
     * Handle the key input event.
     *
     * @param event the event that triggered the handler
     * @throws Exception if the password is invalid
     */
    @FXML
    private void handleKeyInput(KeyEvent event) throws Exception {
        keySequence += event.getCharacter();
        if (keySequence.length() > 6) {
            keySequence = keySequence.substring(keySequence.length() - 6);
        }
        if (Teacher.verifyPassword(keySequence)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Password input is valid.");
            alert.setContentText("You're going to create a teacher profile");
            alert.showAndWait();
            role = 2;
        }
        if (Tester.verifyPassword(keySequence)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Password input is valid.");
            alert.setContentText("You're going to create a tester profile");
            alert.showAndWait();
            role = 3;
        }
    }

}
