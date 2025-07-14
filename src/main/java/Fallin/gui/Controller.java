package Fallin.gui;

import Fallin.engine.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class Controller {

    @FXML
    private TextField difficultyInput;

    @FXML
    private Label stepsLimit;

    @FXML
    private Label stepsCounter;

    @FXML
    private Label life;

    @FXML
    private Label treasures;

    @FXML
    private GridPane gameMap;

    private GameEngine gameEngine;
    private static final int MAP_SIZE = 10;
    private boolean gameEnded = false;


    @FXML
    public void initialize() {
        gameEngine = new GameEngine(MAP_SIZE, 0);
        setupGameMap();
    }

    @FXML
    private void handleRun() {
        String input = difficultyInput.getText();
        if (input.isEmpty() || !isValidDifficulty(input)) {
            showAlert("Invalid Difficulty", "Please enter a valid difficulty value (0-10).");
        } else {
            int difficulty = Integer.parseInt(input);
            gameEngine = new GameEngine(MAP_SIZE, difficulty);
            gameEnded = false; // RESET HERE
            setupGameMap();
            updateGameMap();
        }
    }


    private void moveAndUpdate(int direction) {
        if (gameEnded) return;
        gameEngine.movePlayer(direction);
        updateGameMap();
        if (!gameEnded) {  // Only move mutants if game is still running
            gameEngine.moveMutants();
            updateGameMap();
        }
    }


    @FXML private void handleMoveUp()    { moveAndUpdate(2); }
    @FXML private void handleMoveDown()  { moveAndUpdate(4); }
    @FXML private void handleMoveLeft()  { moveAndUpdate(1); }
    @FXML private void handleMoveRight() { moveAndUpdate(3); }


    @FXML
    private void handleSave() {
        String fileName = "saved_game.txt";
        try {
            GameEngine.saveGame(fileName, gameEngine);
            showAlert("Game Saved", "The game has been saved successfully.");
        } catch (IOException e) {
            showAlert("Error", "An error occurred while saving the game.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoad() {
        String fileName = "saved_game.txt";
        try {
            GameEngine.loadGame(fileName, gameEngine);
            gameEnded = false; // RESET HERE TOO
            updateGameMap();
            showAlert("Game Loaded", "The game has been loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Error", "An error occurred while loading the game.");
            e.printStackTrace();
        }
    }


    @FXML
    private void handleHelp() {
        String instructions = "Instructions:\n" +
                "1. Use the arrow buttons to move the player around the map.\n" +
                "2. Collect treasures and avoid mutants and traps.\n" +
                "3. Reach the exit gate within the steps limit to win the game.";
        showAlert("Help", instructions);
    }

    private void setupGameMap() {
        gameMap.getChildren().clear();
        gameMap.setHgap(5);
        gameMap.setVgap(5);
        gameMap.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                Pane cell = new Pane();
                cell.setPrefSize(50, 50);
                cell.setStyle("-fx-background-color: lightgrey;");
                gameMap.add(cell, j, i);
            }
        }
    }


    private void updateGameMap() {
        Cell[][] map = gameEngine.getMap();
        Player player = gameEngine.getPlayer();

        clearPreviousIcons();

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                Cell cell = map[i][j];
                Pane pane = (Pane) gameMap.getChildren().get(i * MAP_SIZE + j);

                if (cell != null) {
                    pane.setStyle(cell.getCellStyle());
                }
            }
        }

        Pane playerPane = (Pane) gameMap.getChildren().get(player.getX() * MAP_SIZE + player.getY());
        playerPane.setStyle("-fx-background-image: url(\"player_icon.png\"); -fx-background-size: cover;");

        stepsLimit.setText("Steps left: " + (gameEngine.getTimeLimit() - player.getSteps()));
        stepsCounter.setText("Steps counter: " + player.getSteps());
        life.setText("Life: " + player.getLife());
        treasures.setText("Treasures collected: " + player.getTreasures());

        if (!gameEnded) {
            if (player.getX() == 0 && player.getY() == 9) {
                gameEnded = true;
                int score = 20 * player.getTreasures() + (gameEngine.getTimeLimit() - player.getSteps());
                showAlert("Congratulations!", "You won! Your score is " + score);
            } else if (player.getLife() <= 0) {
                gameEnded = true;
                showAlert("Game Over", "You ran out of life. Game Over!");
            } else if (player.getSteps() >= gameEngine.getTimeLimit()) {
                gameEnded = true;
                showAlert("Game Over", "You went above the steps limit! Game Over!");
            }
        }

    }

    private void clearPreviousIcons() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                Pane pane = (Pane) gameMap.getChildren().get(i * MAP_SIZE + j);
                pane.setStyle(null);
                pane.setStyle("-fx-background-color: lightgrey;");
            }
        }
    }

    private boolean isValidDifficulty(String input) {
        try {
            int difficulty = Integer.parseInt(input);
            return difficulty >= 0 && difficulty <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
