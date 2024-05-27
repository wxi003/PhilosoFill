package org.western.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.western.backend.LevelSession;
import org.western.backend.Player;


/**
 * NewgameController class is the controller for the new game page.
 * It allows the player to start a new game.
 *
 * @author Chao Zhang
 * @author Enqin Liu
 */
public class NewgameController {
    private Player player;
    @FXML
    private Button easyButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;

    /**
     * Start the new game page.
     *
     * @param stage the stage to start the new game page
     * @throws IOException if the fxml file is not found
     */
    public void start(Stage stage) throws IOException {
        player = (Player) TransferDataUtils.CONTROLLER.get("player");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new_game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("PhilosoFill");
        stage.setScene(scene);
        stage.setResizable(false);
        easyButton = (Button) scene.lookup("#1");
        mediumButton = (Button) scene.lookup("#2");
        hardButton = (Button) scene.lookup("#3");
        int level = player.getUnlock();
        if (level == 2) {
            hardButton.setDisable(true);
        }
        if (level == 1) {
            mediumButton.setDisable(true);
            hardButton.setDisable(true);
        }
        stage.show();
    }


    /**
     * Go back to the home page.
     *
     * @param event the event of clicking the button
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void backHome(ActionEvent event) throws IOException {
        HomeController home = new HomeController();
        home.start((Stage)((Button)event.getSource()).getScene().getWindow());
    }

    /**
     * Start a new game with the selected level.
     *
     * @param event the event of clicking the button
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void newGame(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        int level = Integer.parseInt(button.getId());
        LevelSession levelSession = new LevelSession(level, 0, false);
        TransferDataUtils.CONTROLLER.put("levelSession", levelSession);
        GameInterface gameInterface = new GameInterface();
        gameInterface.start((Stage) button.getScene().getWindow());
    }
}

