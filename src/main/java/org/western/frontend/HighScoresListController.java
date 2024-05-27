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
import org.western.backend.HighScoreManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is the controller for the high scores list view.
 * It is responsible for displaying the top high scores in the game.
 *
 * @author Xi Wang
 * @author Xikai Lin
 */
public class HighScoresListController {

    @FXML
    private TableView<HighScores> scoresTable;

    @FXML
    private TableColumn<HighScores,Number> column1;

    @FXML
    private TableColumn<HighScores,Number> column2;

    @FXML
    private TableColumn<HighScores,String> column3;

    @FXML
    private Button backButton;

    /**
     * This method initializes the high scores list view.
     * It retrieves the top high scores from the high score manager and displays them in the table view.
     */
    @FXML
    public void initialize() {
        HighScoreManager highScoreManager = HighScoreManager.getInstance();
        List<Map.Entry<String, Integer>> topScores = highScoreManager.getTopScores();

        ObservableList<HighScores> scores = FXCollections.observableArrayList();
        if (topScores.isEmpty()) {
            scoresTable.setPlaceholder(new Label("No Records found. Please check back later."));
        }else{
            int listNumber = 1;
            int preScore = -1;
            for (Map.Entry<String, Integer> entry : topScores) {
                if(entry.getValue() != preScore){
                    preScore = entry.getValue();
                }else{
                    listNumber--;
                }
                scores.add(new HighScores(listNumber++, entry.getValue(), entry.getKey()));
            }

            column1.setCellValueFactory(new PropertyValueFactory<>("rank"));
            column2.setCellValueFactory(new PropertyValueFactory<>("score"));
            column3.setCellValueFactory(new PropertyValueFactory<>("name"));
            column1.setResizable(false);
            column2.setResizable(false);
            column3.setResizable(false);

            scoresTable.setItems(scores);
        }

    }

    /**
     * This method starts the high scores list view.
     * @param primaryStage the stage to display the high scores list view
     * @throws Exception if the high scores list view cannot be displayed
     */
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/western/frontend/high_scores_list_view.fxml")));
            primaryStage.setTitle("PhilosoFill");
            primaryStage.setScene(new Scene(root, 600, 500));
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method handles the back button action.
     * It returns to the home view when the back button is clicked.
     * @throws IOException if the home view cannot be displayed
     */
    @FXML
    private void handleBackButtonAction() throws IOException {
        HomeController home = new HomeController();
        home.start((Stage) backButton.getScene().getWindow());
    }

}
