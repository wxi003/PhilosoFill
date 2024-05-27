package org.western.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.western.backend.Player;
import org.western.backend.PuzzleRecord;

/**
 * This class is the controller for the collection view.
 * It displays the quotes that the player has collected.
 *
 * @author Xi Wang
 * @author Xikai Lin
 * @author Enqin Liu
 */
public class CollectionController {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Quotes> quotesListView;

    @FXML
    private Label collectedLabel;

    /**
     * This method initializes the collection view.
     */
    @FXML
    public void initialize() {
        Player player = (Player)TransferDataUtils.CONTROLLER.get("player");
        String levelSelected = (String) TransferDataUtils.CONTROLLER.get("selectedLevel");

        ArrayList<Integer> collections = player.getCollection();

        if(levelSelected.equals("Easy")){
            // Use lambda expression here to remove elements in list
            collections.removeIf(num -> num > 15);
        } else if (levelSelected.equals("Medium")) {
            collections.removeIf(num -> (num < 16 || num > 30));
        }else{
            collections.removeIf(num -> num < 31);
        }
        ObservableList<Quotes> quotesList = FXCollections.observableArrayList();

        if(collections.isEmpty()){
            quotesListView.setPlaceholder(new Label("No collections found. Please check back later."));
        }else{
            for (Integer collection: collections) {
                PuzzleRecord puzzle = PuzzleRecord.findPuzzle(collection);
                if (puzzle != null) {
                    quotesList.add(new Quotes(puzzle.getSolution(), puzzle.getAuthor(), puzzle.getSource()));
                }else{
                    System.out.println("puzzle is null");
                }
            }

        }
        quotesListView.setItems(quotesList);
        collectedLabel.setText("Already collected: " + collections.size()+ "/15");

        quotesListView.setCellFactory(param -> new ListCell<>() {
            private final Label label = new Label();

            @Override
            protected void updateItem(Quotes quote, boolean empty) {
                super.updateItem(quote, empty);

                if (empty || quote == null) {
                    setGraphic(null);
                } else {
                    label.setText(quote.toString());
                    label.setWrapText(true);
                    label.setMaxWidth(quotesListView.getPrefWidth());
                    setGraphic(label);
                }
            }
        });
    }

    /**
     * This method starts the CollectionController.
     *
     * @param stage the stage to start the CollectionController on
     * @throws Exception if the fxml file is not found
     */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/western/frontend/collection_view.fxml")));
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        root.getStyleClass().add("background");
        stage.setTitle("PhilosoFill");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This method handles the back button action.
     */
    @FXML
    private void handleBackAction() {
        try {
            CollectionChooseController collectionChoose = new CollectionChooseController();
            Stage stage = (Stage) backButton.getScene().getWindow();
            collectionChoose.start(stage);
            TransferDataUtils.CONTROLLER.remove("selectedLevel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
