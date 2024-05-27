package org.western.frontend;

import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.western.backend.HighScoreManager;
import org.western.backend.Player;
import org.western.backend.SavedGameEntries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the teacher track page.
 *
 * @author Chao Zhang
 * @author Xi Wang
 * @author Xikai Lin
 */
public class TeacherTrackController {

    @FXML
    private ComboBox<String> studentComboBox;

    @FXML
    private TableView<GameEntries> recordsTable;

    @FXML
    private TableColumn<GameEntries,Number> column1;

    @FXML
    private TableColumn<GameEntries,String> column2;

    @FXML
    private TableColumn<GameEntries,String> column3;

    @FXML
    private TableColumn<GameEntries,Number> column4;

    @FXML
    private Label recordsCount;

    @FXML
    private Label unlocked;

    @FXML
    private Label topScore;

    @FXML
    private Label levelScore;

    @FXML
    private Button backButton;
    private List<Player> userList;
    private Player selectedUser;


    /**
     * Initialize the teacher track page.
     */
    @FXML
    public void initialize() {
        List<String> usernameList = new ArrayList<>();
        userList = Player.readPlayers();
        if(userList != null){
            userList.forEach(user -> usernameList.add(user.getUsername()));
        }
        studentComboBox.setItems(FXCollections.observableArrayList(usernameList));
        studentComboBox.getSelectionModel().selectFirst();
        updatePlayerSelected();
        studentComboBox.setOnAction(event -> {
            recordsTable.setItems(FXCollections.observableArrayList());
            updatePlayerSelected();
        });

    }

    /**
     * Start the teacher track page.
     *
     * @param primaryStage the stage to display the teacher track page
     * @throws IOException if the teacher track page cannot be loaded
     */
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teacher_track.fxml"));
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Handle the back button action.
     *
     * @throws IOException if the home page cannot be loaded
     */
    @FXML
    private void handleBackButtonAction() throws IOException {
        HomeController home = new HomeController();
        home.start((Stage) backButton.getScene().getWindow());
    }

    /**
     * Update displays for the selected player.
     */
    private void updatePlayerSelected() {
        String selectedUsername = studentComboBox.getSelectionModel().getSelectedItem();
        for (Player user : userList) {
            if (user.getUsername().equals(selectedUsername)) {
                selectedUser = user;
                break;
            }
        }

        LoadGameEntriesController loadGameEntriesController = new LoadGameEntriesController();
        List<SavedGameEntries> savedGameList = SavedGameEntries.getPlayerEntries(selectedUser.getId());
        if(!savedGameList.isEmpty()){
            loadGameEntriesController.setUpSavedGameTable(selectedUser.getId(),recordsTable,column1,column2,column3,column4);
        }

        int unlockedCount = selectedUser.getUnlock() == 4 ? 3 : selectedUser.getUnlock();
        unlocked.setText(String.valueOf(unlockedCount));
        HighScoreManager highScoreManager = HighScoreManager.getInstance();
        int easyScore = highScoreManager.getLevelScore(selectedUser.getUsername(), 1);
        int mediumScore = highScoreManager.getLevelScore(selectedUser.getUsername(), 2);
        int hardScore = highScoreManager.getLevelScore(selectedUser.getUsername(), 3);
        easyScore = easyScore == -1 ? 0 : easyScore;
        mediumScore = mediumScore == -1 ? 0 : mediumScore;
        hardScore = hardScore == -1 ? 0 : hardScore;
        topScore.setText(String.valueOf(easyScore + mediumScore + hardScore));
        levelScore.setText("(Easy:" + easyScore + ", Medium:" + mediumScore + ", Hard:" + hardScore + ")");
        recordsCount.setText(String.valueOf(SavedGameEntries.getPlayerEntries(selectedUser.getId()).size()));
    }

}
