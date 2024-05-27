package org.western.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.western.backend.LevelSession;
import org.western.backend.Player;
import org.western.backend.ScoreEntry;
import org.western.backend.HighScoreManager;

import java.io.IOException;

/**
 * Controller class for the finish level view.
 * It allows the user to check the results after completing a level session.
 *
 * @author Chao Zhang
 * @author Xikai Lin
 */
public class FinishLevelController {

    @FXML
    private Label totalScoreLabel;

    @FXML
    private Label resultLabel;
    @FXML
    private Button menuButton;

    private LevelSession levelSession;
    private int currentUnlock;


    /**
     * Start the finish level view.
     *
     * @param stage the stage to display the view
     * @throws IOException if the fxml file is not found
     */
    public void start(Stage stage) throws IOException {
        levelSession = (LevelSession) TransferDataUtils.CONTROLLER.get("levelSession");
        Player player = (Player) TransferDataUtils.CONTROLLER.get("player");
        currentUnlock = player.getUnlock();
        TransferDataUtils.CONTROLLER.put("unlock", currentUnlock);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("finish_level_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("PhilosoFill");
        stage.setScene(scene);
        stage.setResizable(false);
        totalScoreLabel = (Label) scene.lookup("#totalScoreLabel");
        resultLabel = (Label) scene.lookup("#resultLabel");
        totalScoreLabel.setText("Total score: " + levelSession.getAccumulatedScore() + " / " + levelSession.getPassScore());
        if (levelSession.getAccumulatedScore() >= levelSession.getPassScore()) {
            if (currentUnlock == levelSession.getLevelDifficulty()) {
                if (currentUnlock == 3) {
                    resultLabel.setText("Congratulations! You have completed all levels!");
                } else {
                    resultLabel.setText("Congratulations! The next level is unlocked!");
                }
                player.setUnlock(currentUnlock + 1);
                player.updatePlayer();
            } else {
                resultLabel.setText("Congratulations!");
            }
        } else {
            resultLabel.setText("You failed to get a pass score.");
        }
        ScoreEntry newScoreEntry = new ScoreEntry(player.getUsername(), levelSession.getLevelDifficulty(), levelSession.getAccumulatedScore());
        HighScoreManager highScoreManager = HighScoreManager.getInstance();
        highScoreManager.updateScores(newScoreEntry);
        stage.show();
    }

    /**
     * Go back to the home screen.
     *
     * @param event the action event
     * @throws IOException if the fxml file is not found
     */
    @FXML
    void nextScreen(ActionEvent event) throws IOException {
        levelSession = (LevelSession) TransferDataUtils.CONTROLLER.get("levelSession");
        currentUnlock = (int) TransferDataUtils.CONTROLLER.get("unlock");
        TransferDataUtils.CONTROLLER.remove("LevelSession");
        TransferDataUtils.CONTROLLER.remove("unlock");
        if (levelSession.getAccumulatedScore() >= levelSession.getPassScore()
                && currentUnlock == levelSession.getLevelDifficulty()
                && currentUnlock == 3) {
            PassAllController passAll = new PassAllController();
            passAll.start((Stage)((Button)event.getSource()).getScene().getWindow());
        } else {
            HomeController home = new HomeController();
            home.start((Stage)((Button)event.getSource()).getScene().getWindow());
        }
    }
}
