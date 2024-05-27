package org.western.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Controller for the pass all view.
 * This view is displayed when the user has passed all the levels.
 *
 * @author Xikai Lin
 */
public class PassAllController {
    @FXML
    private Button mainMenuButton;
    @FXML
    private ImageView imageView;

    /**
     * Initializes the pass all view.
     */
    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResourceAsStream("/images/passall.png"));
        imageView.setImage(image);
    }

    /**
     * Starts the pass all view.
     *
     * @param primaryStage the primary stage of the application
     * @throws IOException if the FXML file is not found
     */
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("pass_all-view.fxml"));
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Handles the main menu button action.
     *
     * @throws IOException if the FXML file is not found
     */
    @FXML
    private void handleMainMenuAction() throws IOException {
        HomeController home = new HomeController();
        home.start((Stage) mainMenuButton.getScene().getWindow());

    }
}
