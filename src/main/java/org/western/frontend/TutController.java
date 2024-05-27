package org.western.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Controller class for the tutorial view.
 *
 * @author Xikai Lin
 */
public class TutController {
    @FXML
    private Button backButton;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private ImageView imageView7;
    @FXML
    private ImageView imageView8;
    @FXML
    private ImageView imageView9;
    @FXML
    private ImageView imageView10;


    /**
     * Starts the tutorial view.
     *
     * @param primaryStage The stage to display the tutorial view.
     * @throws IOException If the FXML file cannot be loaded.
     */
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tut_view.fxml"));
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Handles the back button action.
     *
     * @param event The event that triggers the action.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        HomeController home = new HomeController();
        home.start((Stage) backButton.getScene().getWindow());
    }

    /**
     * Initializes the tutorial view.
     */
    @FXML
    public void initialize() {
        Image image1 = new Image(getClass().getResourceAsStream("/images/p1.png"));
        imageView1.setImage(image1);

        Image image2 = new Image(getClass().getResourceAsStream("/images/p2_profile.png"));
        imageView2.setImage(image2);

        Image image3 = new Image(getClass().getResourceAsStream("/images/p3_menu.png"));
        imageView3.setImage(image3);

        Image image4 = new Image(getClass().getResourceAsStream("/images/p4_levelchoose.png"));
        imageView4.setImage(image4);

        Image image5 = new Image(getClass().getResourceAsStream("/images/p5_game1.png"));
        imageView5.setImage(image5);

        Image image6 = new Image(getClass().getResourceAsStream("/images/p6_game2.png"));
        imageView6.setImage(image6);

        Image image7 = new Image(getClass().getResourceAsStream("/images/p7_game3.png"));
        imageView7.setImage(image7);

        Image image8 = new Image(getClass().getResourceAsStream("/images/p8_finishpuzzle.png"));
        imageView8.setImage(image8);

        Image image9 = new Image(getClass().getResourceAsStream("/images/p9_scorerule.png"));
        imageView9.setImage(image9);

        Image image10 = new Image(getClass().getResourceAsStream("/images/p10_snow.png"));
        imageView10.setImage(image10);
    }
}
