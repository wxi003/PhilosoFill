package org.western.frontend;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.western.backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;


/**
 * GameInterface is the main interface for the game.
 * It displays the puzzle and allows the user to interact with it.
 *
 * @author Chao Zhang
 * @author Enqin Liu
 */
public class GameInterface {

    private Stage primaryStage;
    private LevelSession levelSession;
    private PuzzleSession puzzleSession;
    private PuzzleRecord puzzle;
    private TextFlow textFlow;
    private Label bottomAttemptLabel;
    private Label puzzleScoreLabel;
    private ToggleButton tickButton;
    private Label hintLabel;

    /**
     * Start the game interface.
     *
     * @param primaryStage the stage to display the game interface
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.levelSession = (LevelSession) TransferDataUtils.CONTROLLER.get("levelSession");
        int puzzleId;
        if (levelSession.getDebugStatus()) {
            puzzleId = (int) TransferDataUtils.CONTROLLER.get("debugPuzzleId");
        } else {
            puzzleId = this.levelSession.getNextPuzzleId();
        }
        this.puzzleSession = new PuzzleSession(puzzleId);
        // Create top buttons.
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> showResetConfirmation());

        Button hintButton = new Button("  Hint  ");

        // Create score display box.
        Label totalScoreLabel = new Label("Total Score: " + this.levelSession.getAccumulatedScore());
        puzzleScoreLabel = new Label("Puzzle Score: " + this.puzzleSession.getPuzzleScore());
        HBox scoreBox = new HBox(40, totalScoreLabel, puzzleScoreLabel);
        scoreBox.setStyle("-fx-padding: 10 10");


        // Create top menu.
        Button mainMenuButton = new Button("Back");
        mainMenuButton.setOnAction(event -> showMainMenuConfirmation());
        HBox menuBox = new HBox(mainMenuButton);


        // Create puzzle
        puzzle = puzzleSession.findPuzzle();
        String quote = puzzle.getSolution();
        // Split the quote into parts
        String[] parts = quote.split("");
        String[] chars = puzzle.getPuzzle().split("");
        // Create TextFlow to hold the quote
        textFlow = new TextFlow();
        textFlow.setMaxWidth(600);
        textFlow.setLineSpacing(5);
        textFlow.setStyle("-fx-background: transparent; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 14px;");
        for (String ch : parts) {
            if (ch.equals(" ")) {
                Text space = new Text("   ");
                //space.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 4px; ");
                textFlow.getChildren().add(space);
                continue;
            }
            int index = IntStream.range(0, chars.length)
                    .filter(i -> chars[i].equals(ch.toLowerCase()))
                    .findFirst()
                    .orElse(-1);
            if (index != -1) {
                TextField blank = new TextField();
                blank.setPrefWidth(18);
                blank.setPrefHeight(16);
                blank.setPadding(new Insets(0, 0, 0, 0));
                blank.setOpacity(0.7);

                blank.getProperties().put("char", ch);

                blank.setOnKeyTyped(event -> {
                    String string = blank.getText();
                    boolean empty = string.isEmpty();
                    if (blank.getText().length() > 1) {
                        blank.setText(string.substring(0, 1));
                        blank.positionCaret(string.length());
                    }
                    int indexBlank = textFlow.getChildren().indexOf(blank);
                    if (!empty) {
                        for (int i = indexBlank + 1; i < textFlow.getChildren().size(); i++) {
                            Node node = textFlow.getChildren().get(i);
                            if (node instanceof TextField && ((TextField) node).getText().isEmpty()) {
                                String chNode = (String) node.getProperties().get("char");
                                if (!chNode.equalsIgnoreCase(ch)) {
                                    ((TextField) node).requestFocus();
                                    break;
                                }
                            }
                        }
                    }
                });
                textFlow.getChildren().add(blank);
            } else {
                Text text = new Text(ch);
                //text.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; ");
                textFlow.getChildren().add(text);
            }
        }

        for (Node nodeA : textFlow.getChildren()) {
            if (nodeA instanceof TextField) {
                String charValueA = (String) nodeA.getProperties().get("char");
                for (Node nodeB : textFlow.getChildren()) {
                    if (nodeB instanceof TextField) {
                        String charValueB = (String) nodeB.getProperties().get("char");
                        if (charValueA.equalsIgnoreCase(charValueB)) {
                            nodeA.focusedProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue) {
                                    nodeA.setStyle("-fx-border-color: blue");
                                    nodeB.setStyle("-fx-border-color: blue");
                                } else {
                                    nodeA.setStyle("-fx-border-color: black");
                                    nodeB.setStyle("-fx-border-color: black");
                                }
                            });
                            ((TextField) nodeA).textProperty().addListener((observable, oldValue, newValue) -> {
                                if (charValueB.equals(charValueB.toLowerCase())) {
                                    ((TextField) nodeB).setText(newValue.toLowerCase());
                                } else {
                                    ((TextField) nodeB).setText(newValue.toUpperCase());
                                }
                                for (Node nodeC : textFlow.getChildren()) {
                                    if (nodeC instanceof TextField && ((TextField) nodeC).getText().isEmpty()) {
                                        tickButton.setDisable(true);
                                        return;
                                    }
                                }
                                tickButton.setDisable(false);
                            });
                        }
                    }
                }
            }
        }

        // Set up the scene and stage
        StackPane centerStackPane = new StackPane();
        centerStackPane.getChildren().add(textFlow);

        hintLabel = new Label("Letters will be used：" + String.join(", ", chars));
        hintLabel.setVisible(false);

        // Set action for hint button
        hintButton.setOnAction(event -> {
            if (!hintLabel.isVisible()) {
                hintLabel.setVisible(true);
                this.puzzleSession.useHint();
                this.puzzleSession.updatePuzzleScore();
                puzzleScoreLabel.setText("Puzzle Score: " + this.puzzleSession.getPuzzleScore());
            }
        });

        // Create bottom hints.
        Label bottomlevelLabel = new Label("Level: " + levelSession.getLevelDifficultyName());
        Label bottomProgressLabel = new Label("Puzzle: " + (this.levelSession.getProgress().size() + 1) + "/5");
        bottomAttemptLabel = new Label("Failed Attempt: " + this.puzzleSession.getAttempt());
        Label bottomTargetLabel = new Label("Unlock Next Level: " + this.levelSession.getPassScore());
        HBox.setHgrow(bottomlevelLabel, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(bottomProgressLabel, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(bottomAttemptLabel, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(bottomTargetLabel, javafx.scene.layout.Priority.ALWAYS);


        tickButton = new ToggleButton("✓");
        tickButton.getStyleClass().add("tick-button");
        StackPane.setAlignment(tickButton, javafx.geometry.Pos.BOTTOM_RIGHT);
        tickButton.setOnAction(event -> showWrongAnswerAlert(textFlow, quote));
        tickButton.setDisable(true);

        VBox centerVBox = new VBox(10, textFlow, hintLabel);
        centerVBox.setAlignment(Pos.CENTER);

        HBox centerHBox = new HBox(centerVBox);
        centerHBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.getStyleClass().add("background");
        root.setPadding(new Insets(26));
        HBox topBox = new HBox(10, menuBox, resetButton, hintButton, scoreBox);
        root.setTop(topBox);
        root.setCenter(centerHBox);
        HBox bottomBoxLeft = new HBox(10, bottomlevelLabel, bottomProgressLabel, bottomAttemptLabel, bottomTargetLabel);
        bottomBoxLeft.setStyle("-fx-padding: 16 0");
        HBox bottomBoxRight = new HBox(10, tickButton);
        bottomBoxRight.setStyle("-fx-padding: 0 0 0 30");
        HBox bottomBox = new HBox(10, bottomBoxLeft, tickButton);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent k) {
                if (k.getCode() == KeyCode.ESCAPE) {
                    showMainMenuConfirmation();
                }
                if (k.getCode() == KeyCode.ENTER && !tickButton.isDisabled()) {
                    showWrongAnswerAlert(textFlow, quote);
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("PhilosoFill");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Show the reset confirmation dialog.
     */
    private void showResetConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to reset this puzzle?");

        ButtonType yesButtonType = new ButtonType("Yes");
        ButtonType noButtonType = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButtonType, noButtonType);

        Button yesButton = (Button) alert.getDialogPane().lookupButton(yesButtonType);
        Button noButton = (Button) alert.getDialogPane().lookupButton(noButtonType);

        yesButton.setDefaultButton(false);
        noButton.setDefaultButton(false);

        yesButton.setOnAction(event -> {
            textFlow.getChildren().forEach(node -> {
                if (node instanceof TextField) {
                    ((TextField) node).clear();
                }
            });
            this.puzzleSession.reset();
            bottomAttemptLabel.setText("Failed Attempt: " + this.puzzleSession.getAttempt());
            puzzleScoreLabel.setText("Puzzle Score: " + this.puzzleSession.getPuzzleScore());
            tickButton.setDisable(true);
            hintLabel.setVisible(false);
            alert.close();
        });

        alert.showAndWait();
    }

    /**
     * Show the main menu confirmation dialog.
     */
    private void showMainMenuConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setHeaderText("Going back to main menu will automatically save the previous completed puzzles as progress.");
        alert.setContentText("Do you still want to continue?");

        ButtonType yesButtonType = new ButtonType("Yes");
        ButtonType noButtonType = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButtonType, noButtonType);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == yesButtonType) {
                showSaveSuccessAlert();
            } else if (result.get() == noButtonType) {
                alert.close();
            }
        }

    }

    /**
     * Show the save success alert.
     */
    private void showSaveSuccessAlert() {
        Player player = (Player) TransferDataUtils.CONTROLLER.get("player");
        int playerId = player.getId();
        int accumulatedScore = this.levelSession.getAccumulatedScore();
        int level = this.levelSession.getLevelDifficulty();
        ArrayList<Integer> progressList = this.levelSession.getProgress();
        boolean saveStatus = !this.levelSession.getProgress().isEmpty() && !this.levelSession.getDebugStatus();
        if (saveStatus) {
            SavedGameEntries savedGameEntries = new SavedGameEntries(playerId, accumulatedScore, level, progressList);
            SavedGameEntries.addSavedGameEntry(savedGameEntries);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (saveStatus) {
            alert.setTitle("Success");
            alert.setContentText("Previous completed puzzles saved as progress!");
        } else {
            alert.setTitle("No puzzles completed");
            alert.setContentText("Nothing saved as no puzzles completed in this session.");
        }

        ButtonType okButtonType = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButtonType);

        Button okButton = (Button) alert.getDialogPane().lookupButton(okButtonType);
        okButton.setDefaultButton(false);


        alert.showAndWait().ifPresent(buttonType -> {
            navigateToHomeView();
        });
    }

    /**
     * Navigate to the home view.
     */
    private void navigateToHomeView() {
        try {
            HomeController homeController = new HomeController();
            homeController.start(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the input is correct or not.
     *
     * @param textFlow the text flow to check the answer
     * @param fullText the full text of the answer
     */
    private void showWrongAnswerAlert(TextFlow textFlow, String fullText) {
        StringBuilder sb = new StringBuilder();
        for (Node node : textFlow.getChildren()) {
            if (node instanceof Text) {
                sb.append(((Text) node).getText());
            }
            if (node instanceof TextField) {
                sb.append(((TextField) node).getText());
            }
        }
        String answer = sb.toString().replaceAll("   ", " ");
        if (answer.equalsIgnoreCase(fullText)) {
            this.levelSession.updateAccumulatedScore(this.puzzleSession.getPuzzleScore() + this.levelSession.getAccumulatedScore());
            this.levelSession.updateProgress(this.puzzleSession.getPuzzleId());
            TransferDataUtils.CONTROLLER.put("levelSession", this.levelSession);
            TransferDataUtils.CONTROLLER.put("puzzleRecord", this.puzzle);

            FinishPuzzleGameInterface finishPuzzleGameInterface = new FinishPuzzleGameInterface();
            finishPuzzleGameInterface.start(primaryStage);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong Answer");
            alert.setHeaderText(null);
            alert.setContentText("Sorry, wrong answer.");

            ButtonType continueButtonType = new ButtonType("Continue");
            alert.getButtonTypes().setAll(continueButtonType);

            Button continueButton = (Button) alert.getDialogPane().lookupButton(continueButtonType);
            continueButton.setDefaultButton(false);

            alert.showAndWait();
            this.puzzleSession.updateAttempt();
            this.puzzleSession.updatePuzzleScore();
            puzzleScoreLabel.setText("Puzzle Score: " + this.puzzleSession.getPuzzleScore());
            bottomAttemptLabel.setText("Failed Attempt: " + this.puzzleSession.getAttempt());
        }
    }
}
