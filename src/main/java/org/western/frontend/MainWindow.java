package org.western.frontend;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.western.backend.Player;

/**
 * The controller for the profile view and the main window.
 * This class is responsible for displaying the profile view and handling user interactions.
 *
 * @author Xi Wang
 * @author Enqin Liu
 *
 */
public class MainWindow extends Application {

    @FXML
    private FlowPane profileContainer;

    /**
     * Start the profile view.
     *
     * @param stage the stage to display the profile view
     * @throws IOException if the profile view fxml file cannot be loaded
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/western/frontend/style.css")).toExternalForm());
        stage.setTitle("PhilosoFill");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Initialize the profile view.
     */
    @FXML
    private void initialize() {
        List<Player> userList = Player.readPlayers();
        if(userList != null){
            for (Player user : userList) {
                HBox profileBox = createProfileHBox(user);
                profileContainer.getChildren().add(profileBox);
            }
        }
        HBox newUserBox = createNewUserHBox();
        profileContainer.getChildren().add(newUserBox);
    }

    /**
     * Create a new user HBox
     *
     * @return HBox
     */
    private HBox createNewUserHBox() {
        HBox newUserBox = newHBoxCreate();
        VBox vbox = newVBoxCreate();

        Label newLabel = new Label("NEW");
        newLabel.setStyle("-fx-alignment: CENTER;");
        Button createButton = new Button("Create");
        createButton.setOnAction(this::createButtonClick);

        HBox buttonBox = new HBox(createButton);
        buttonBox.setStyle("-fx-padding: 20px 0  0; -fx-alignment: CENTER;");
        vbox.getChildren().addAll(newLabel, buttonBox);

        newUserBox.getChildren().add(vbox);
        return newUserBox;
    }

    /**
     * Create a profile HBox
     *
     * @param player the player
     * @return HBox
     */
    private HBox createProfileHBox(Player player) {
        HBox profileBox = newHBoxCreate();
        VBox vbox = newVBoxCreate();

        Label playerIdLabel = new Label(player.getUsername());
        // Set up the delete and play buttons
        Button deleteButton = new Button("Delete");
        Button playButton = new Button("Play");

        deleteButton.setOnAction(event -> deleteButtonClick(player.getId()));
        playButton.setOnAction(event -> playButtonClick(event, player.getId()));

        HBox buttonBox = new HBox(deleteButton, playButton);
        buttonBox.setSpacing(8);
        buttonBox.setStyle("-fx-padding: 20px 0  0;");


        vbox.getChildren().addAll(playerIdLabel, buttonBox);

        profileBox.getChildren().add(vbox);
        return profileBox;
    }

    /**
     * A helper method to create HBox and set style for it
     *
     * @return newHBox
     */
    private HBox newHBoxCreate(){
        HBox newHBox = new HBox();
        newHBox.setPrefWidth(180);
        newHBox.getStyleClass().add("box");
        newHBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10px;");

        return newHBox;
    }

    /**
     * A helper method to create VBox and set style for it
     *
     * @return vbox
     */
    private VBox newVBoxCreate(){
        VBox vbox = new VBox();
        vbox.setPrefWidth(180);
        vbox.setStyle("-fx-padding: 20px; -fx-alignment: CENTER;");
        return vbox;
    }

    /**
     * The method to handle the create button click event
     *
     * @param event the event
     */
    @FXML
    private void createButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        openCreatePage(stage);
    }

    /**
     * The method to handle the play button click event
     *
     * @param event the event
     * @param id the id of the player
     */
    @FXML
    private void playButtonClick(ActionEvent event, int id) {
        TransferDataUtils.CONTROLLER.put("player", Player.getPlayerById(id));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        openHomePage(stage);
    }

    /**
     * The method to handle the delete button click event
     *
     * @param id the id of the player
     */
    @FXML
    private void deleteButtonClick(int id) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Irreversible");
        alert.setHeaderText("Are you sure you want to delete this profile?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                Player.deleteUser(id);
                try {
                    Stage stage = (Stage) profileContainer.getScene().getWindow();
                    MainWindow profile = new MainWindow();
                    profile.start(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Open the home page
     *
     * @param currentStage the current stage
     */
    private void openHomePage(Stage currentStage) {
        try {

            HomeController home = new HomeController();
            home.start(currentStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the create page
     *
     * @param currentStage the current stage
     */
    private void openCreatePage(Stage currentStage) {
        try {
            CreateController createController = new CreateController();
            createController.start(currentStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method to launch the profile view which is the first window of the game
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch();
    }

}
