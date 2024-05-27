package org.western.frontend;

import org.western.backend.LevelSession;
import org.western.backend.Player;
import org.western.backend.PuzzleRecord;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The FinishPuzzleGameInterface class is the interface for the finish of a puzzle game.
 * It displays the solution of the puzzle, the source of the puzzle, and the author of the puzzle.
 * It also displays the total score of the player in the current level.
 *
 * @author Chao Zhang
 * @author Enqin Liu
 */
public class FinishPuzzleGameInterface{

    /**
     * Start the interface for finishing puzzle
     * @param primaryStage the stage to display the view
     */
    public void start(Stage primaryStage) {
        LevelSession levelSession = (LevelSession) TransferDataUtils.CONTROLLER.get("levelSession");
        PuzzleRecord puzzleRecord = (PuzzleRecord) TransferDataUtils.CONTROLLER.get("puzzleRecord");
        Player player = (Player) TransferDataUtils.CONTROLLER.get("player");
        if (!levelSession.getDebugStatus()) {
            ArrayList<Integer> collection = player.getCollection();
            collection.add(puzzleRecord.getId());
            player.setCollection(collection);
            player.updatePlayer();
        }

        BorderPane root = new BorderPane();

        Text scoreLabel = new Text("Total score: " + levelSession.getAccumulatedScore() + " / " + levelSession.getPassScore());
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        BorderPane.setAlignment(scoreLabel, Pos.TOP_LEFT);
        BorderPane.setMargin(scoreLabel, new Insets(10));
        root.setTop(scoreLabel);

        StackPane centerPane = new StackPane();

        Rectangle bigRectangle = new Rectangle(550, 400, Color.TRANSPARENT);
        bigRectangle.setStroke(Color.BLACK);
        bigRectangle.setStrokeWidth(2);

        Text text = new Text(puzzleRecord.getSolution() + "\n\n\nFrom: " + puzzleRecord.getSource() + "\n\nAuthor: " + puzzleRecord.getAuthor());
        text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        text.setLineSpacing(10);
        text.setWrappingWidth(500);
        StackPane.setAlignment(text, Pos.CENTER);
        centerPane.getChildren().addAll(bigRectangle, text);

        root.setCenter(centerPane);

        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));

        Button nextButton = new Button("NEXT >");
        nextButton.setOnAction(e -> {
            TransferDataUtils.CONTROLLER.remove("puzzleRecord");
            if (levelSession.getProgress().size() < 5 && !levelSession.getDebugStatus()) {
                GameInterface gameInterface = new GameInterface();
                gameInterface.start(primaryStage);
            } else if (levelSession.getProgress().size() == 5 && !levelSession.getDebugStatus()) {
                try {
                    FinishLevelController finishLevelController = new FinishLevelController();
                    finishLevelController.start(primaryStage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    TransferDataUtils.CONTROLLER.remove("LevelSession");
                    TransferDataUtils.CONTROLLER.remove("debugPuzzleId");
                    DebugModeController debugModeController = new DebugModeController();
                    debugModeController.start(primaryStage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        bottomBox.getChildren().add(nextButton);

        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        root.getStyleClass().add("background");
        primaryStage.setScene(scene);
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
