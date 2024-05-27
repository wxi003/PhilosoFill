package org.western.frontend;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is the controller for the collection_choose_view.fxml file.
 * It allows the user to choose the level of difficulty for the collection they want to view.
 *
 * @author Xi Wang
 * @author Xikai Lin
 */
public class CollectionChooseController {

    @FXML
    private ComboBox<String> levelComboBox;

    @FXML
    private Button continueButton;

    /**
     * This method initializes the levelComboBox with the options "Easy", "Medium", and "Hard".
     */
    @FXML
    public void initialize() {
        levelComboBox.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
        levelComboBox.getSelectionModel().selectFirst();
        levelComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                continueButton.setDisable(newSelection == null));
    }

    /**
     * This method starts the CollectionChooseController.
     *
     * @param primaryStage the stage to start the CollectionChooseController on
     * @throws IOException if the fxml file is not found
     */
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/western/frontend/collection_choose_view.fxml"))); // Check this path carefully
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This method handles the back button action.
     *
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void handleBackButtonAction() throws IOException {
        HomeController home = new HomeController();
        home.start((Stage) levelComboBox.getScene().getWindow());
    }

    /**
     * This method handles the continue button action.
     *
     * @throws Exception if the CollectionController cannot be started
     */
    @FXML
    private void handleContinueButtonAction() throws Exception{
        String selectedLevel = levelComboBox.getSelectionModel().getSelectedItem();

        if ("Easy".equals(selectedLevel) || "Medium".equals(selectedLevel) || "Hard".equals(selectedLevel)) {
            TransferDataUtils.CONTROLLER.put("selectedLevel", selectedLevel);
            CollectionController library = new CollectionController();
            library.start((Stage) levelComboBox.getScene().getWindow());
        }
    }
}
