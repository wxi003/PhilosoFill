package org.western.frontend;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.western.backend.Player;
import org.western.backend.Teacher;
import org.western.backend.Tester;


/**
 * HomeController is the controller class for the home view.
 * It allows user navigate to different views
 *
 * @author Chao Zhang
 * @author Enqin Liu
 * @author Xi Wang
 * @author Xikai Lin
 */
public class HomeController {
    private String keySequence = "";
    private Player player;

    @FXML
    private Text currentUser;

    /**
     * Start the home view
     *
     * @param stage the stage to show the home view
     * @throws IOException if the fxml file is not found
     */
    public void start(Stage stage) throws IOException {
        player = (Player) TransferDataUtils.CONTROLLER.get("player");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Text currentUser = (Text) scene.lookup("#currentUser");
        currentUser.setText("Welcome to the game, " + player.getUsername()+"!");
        stage.setTitle("PhilosoFill");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    /**
     * Handle the start new game button action
     *
     * @param event the action event
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void startNewGame(ActionEvent event) throws IOException {
        NewgameController newgame = new NewgameController();
        newgame.start((Stage) ((Button) event.getSource()).getScene().getWindow());
    }


    /**
     * Handle the about us action
     *
     */
    @FXML
    private void showAboutUs() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Us");
        alert.setHeaderText("Welcome to PhilosoFill!");
        alert.setContentText("Welcome to PhilosoFill, an educational game designed to spark curiosity and foster philosophical thinking among its players.\n\n"
                + "PhilosoFill is designed and developed by Group 20 from the CS2212 course at Western University during the 2024 winter semester. "
                + "Our group is composed of five undergraduate students from the Computer Science major: Xi Wang, Enqin Liu, Xikai Lin, Chao Zhang and Yuqian Sun. "
                + "Each of us has made valuable contributions to this project.\n\n"
                + "For inquiries or more information, please contact us at xxx@uwo.ca. "
                + "Join us in this philosophical adventure and let's ponder the world of ideas together with PhilosoFill.");
        alert.showAndWait();
    }

    /**
     * Handle the load saved game button action
     *
     * @param event the action event
     * @throws Exception if the fxml file is not found
     */
    @FXML
    private void handleLoadSavedGameAction(ActionEvent event) throws Exception {
        LoadGameEntriesController loadGame = new LoadGameEntriesController();
        loadGame.start((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    /**
     * Handle the view high scores button action
     *
     * @param event the action event
     * @throws Exception if the fxml file is not found
     */
    @FXML
    private void handleViewHighScoresAction(ActionEvent event) throws Exception {
        HighScoresListController highScore = new HighScoresListController();
        highScore.start((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    /**
     * Handle the view tutorial button action
     *
     * @param event the action event
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void handleTutAction(ActionEvent event) throws IOException {
        TutController tut = new TutController();
        tut.start((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    /**
     * Handle the view collection button action
     *
     * @param event the action event
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void handleCollectionAction(ActionEvent event) throws IOException {
        CollectionChooseController collectionChoose = new CollectionChooseController();
        collectionChoose.start((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    /**
     * Handle the log-out action
     *
     * @param event the action event
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void exitToProfile(ActionEvent event) throws IOException {
        TransferDataUtils.CONTROLLER.remove("player");
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        MainWindow profile = new MainWindow();
        profile.start(stage);
    }

    /**
     * Handle the key input event
     *
     * @param event the key event
     * @throws Exception if the fxml file is not found
     */
    @FXML
    private void handleKeyInput(KeyEvent event) throws Exception {
        keySequence += event.getCharacter();
        if (keySequence.length() > 6) {
            keySequence = keySequence.substring(keySequence.length() - 6); // 只保留最后六个字符
        }
        player = (Player) TransferDataUtils.CONTROLLER.get("player");
        int role = player.getRole();
        if ((role == 2 || role ==3) && Teacher.verifyPassword(keySequence)) {
            navigateToTeacherTrack(event);
        }
        if (role == 3 && Tester.verifyPassword(keySequence)) {
            navigateToDebugMode(event);
        }
    }

    /**
     * Navigate to the debug mode
     *
     * @param event the key event
     * @throws IOException if the fxml file is not found
     */
    private void navigateToDebugMode(KeyEvent event) throws IOException {
        DebugModeController debugmode = new DebugModeController();
        debugmode.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Navigate to the teacher track
     *
     * @param event the key event
     * @throws IOException if the fxml file is not found
     */
    private void navigateToTeacherTrack(KeyEvent event) throws IOException {
        TeacherTrackController teachertrack = new TeacherTrackController();
        teachertrack.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }


}
