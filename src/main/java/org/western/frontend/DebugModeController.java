package org.western.frontend;



import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.western.backend.LevelSession;

import java.io.IOException;

/**
 * DebugModeController class is the controller for the debug mode screen.
 * It allows the user to select a level and a puzzle to play.
 *
 * @author Chao Zhang
 * @author Xikai Lin
 */
public class DebugModeController {

    @FXML
    private ComboBox<String> levelComboBox;

    @FXML
    private ComboBox<String> puzzleComboBox;

    /**
     * Initializes the debug mode screen.
     */
    @FXML
    public void initialize() {
        levelComboBox.setItems(FXCollections.observableArrayList( "Easy", "Medium", "Hard"));
        levelComboBox.getSelectionModel().selectFirst();
        puzzleComboBox.setItems(FXCollections.observableArrayList( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"));
        puzzleComboBox.getSelectionModel().selectFirst();
        levelComboBox.setOnAction(event -> {
            if (levelComboBox.getSelectionModel().getSelectedIndex() == 0) {
                puzzleComboBox.setItems(FXCollections.observableArrayList( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"));
                puzzleComboBox.getSelectionModel().selectFirst();
            } else if (levelComboBox.getSelectionModel().getSelectedIndex() == 1) {
                puzzleComboBox.setItems(FXCollections.observableArrayList( "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"));
                puzzleComboBox.getSelectionModel().selectFirst();
            } else {
                puzzleComboBox.setItems(FXCollections.observableArrayList( "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"));
                puzzleComboBox.getSelectionModel().selectFirst();
            }
        });
    }

    /**
     * Starts the debug mode screen.
     *
     * @param primaryStage the stage to display the debug mode screen
     * @throws IOException if the debug mode screen fxml file is not found
     */
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("debug_mode.fxml"));
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Handles the back button action.
     *
     * @throws IOException if the home screen fxml file is not found
     */
    @FXML
    private void handleBackButtonAction() throws IOException {
        HomeController home = new HomeController();
        home.start((Stage) levelComboBox.getScene().getWindow());
    }

    /**
     * Handles the start button action.
     */
    @FXML
    private void startGame() {
        int level = levelComboBox.getSelectionModel().getSelectedIndex() + 1;
        int puzzleId = puzzleComboBox.getSelectionModel().getSelectedIndex() + 1 + (level - 1) * 15;
        LevelSession levelSession = new LevelSession(level, 0, true);
        TransferDataUtils.CONTROLLER.put("levelSession", levelSession);
        TransferDataUtils.CONTROLLER.put("debugPuzzleId", puzzleId);
        GameInterface gameInterface = new GameInterface();
        gameInterface.start((Stage) levelComboBox.getScene().getWindow());
    }
}

