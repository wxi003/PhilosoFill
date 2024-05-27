package org.western.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

import org.western.backend.Player;
import org.western.backend.SavedGameEntries;
import org.western.backend.LevelSession;

/**
 * This class is the controller for the Load Game Entries view.
 * It is responsible for loading the saved game entries of the player
 * and displaying them in a table. The player can then select a game entry
 * to continue playing the game from where they left off.
 *
 * @author Xi Wang
 *
 */
public class LoadGameEntriesController {

    @FXML
    private TableView<GameEntries> savedGameTable;

    @FXML
    private TableColumn<GameEntries,Number> column1;

    @FXML
    private TableColumn<GameEntries,String> column2;

    @FXML
    private TableColumn<GameEntries,String> column3;

    @FXML
    private TableColumn<GameEntries,Number> column4;

    @FXML
    private Button backButton;

    @FXML
    private Button continueButton;

    /**
     * This method is called when the view is loaded.
     * It initializes the table view with the saved game entries of the player.
     */
    @FXML
    public void initialize() {
        // Disable the "Continue" button by default
        continueButton.setDisable(true);

        Player player = (Player)TransferDataUtils.CONTROLLER.get("player");
        int id = player.getId();

        setUpSavedGameTable(id, savedGameTable,column1,column2,column3,column4);

        // Enable the "Continue" button when a game entry is selected
        savedGameTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                continueButton.setDisable(newSelection == null));

    }

    /**
     * Start the home view
     *
     * @param primaryStage the stage to show the home view
     * @throws IOException if the fxml file is not found
     */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/org/western/frontend/load_game_view.fxml"));
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * a helper method to set up saved game entries table
     *
     * @param playerID the id of the player
     */
    public void setUpSavedGameTable(int playerID, TableView<GameEntries> table,TableColumn<GameEntries,Number> column1,
                                    TableColumn<GameEntries,String> column2, TableColumn<GameEntries,String> column3,
                                    TableColumn<GameEntries,Number> column4){
        List<SavedGameEntries> savedGameList = SavedGameEntries.getPlayerEntries(playerID);
        ObservableList<GameEntries> gameEntries = FXCollections.observableArrayList();

        if (savedGameList.isEmpty()) {
            table.setPlaceholder(new Label("No Saved Game Entries found. Please check back later."));
        }else{
            int recordNumber = 1;
            String level;
            String progress;
            for (SavedGameEntries entry : savedGameList) {
                progress = entry.getProgress().size() + "/5";
                level = switch (entry.getLevel()) {
                    case 2 -> "Medium";
                    case 3 -> "Hard";
                    default -> "Easy";
                };

                gameEntries.add(new GameEntries(recordNumber++, level, progress,entry.getAccumulatedScore(),entry.getProgress(),entry.getID()));
            }

            column1.setCellValueFactory(new PropertyValueFactory<>("recordNum"));
            column2.setCellValueFactory(new PropertyValueFactory<>("level"));
            column3.setCellValueFactory(new PropertyValueFactory<>("progress"));
            column4.setCellValueFactory(new PropertyValueFactory<>("score"));

            column1.setResizable(false);
            column2.setResizable(false);
            column3.setResizable(false);

            table.setItems(gameEntries);
        }
    }


    /**
     * Handle the back button action
     *
     * @throws IOException if the fxml file is not found
     */
    @FXML
    private void handleBackButtonAction() throws IOException {
        HomeController home = new HomeController();
        home.start((Stage) backButton.getScene().getWindow());
    }

    /**
     * Handle the continue button action
     */
    @FXML
    private void handleContinueButtonAction() {
        int level;
        GameEntries selectedEntry = savedGameTable.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            level = switch (selectedEntry.getLevel()){
                case "Medium"-> 2;
                case "Hard"->3;
                default-> 1;
            };
            LevelSession levelSession = new LevelSession(level,selectedEntry.getScore(),
                    selectedEntry.getSavedGameList(),Boolean.FALSE);
            TransferDataUtils.CONTROLLER.put("levelSession",levelSession);

            Player player = (Player)TransferDataUtils.CONTROLLER.get("player");
            int playerID = player.getId();

            List<SavedGameEntries> savedGameList = SavedGameEntries.getPlayerEntries(playerID);
            for(SavedGameEntries savedGameEntry: savedGameList) {
                if (savedGameEntry.getID() == selectedEntry.getId() ) {
                    SavedGameEntries.deleteSavedGameEntry(savedGameEntry.getID());
                }
            }
            GameInterface gameInterface = new GameInterface();
            gameInterface.start((Stage) continueButton.getScene().getWindow());
        }


    }
}
