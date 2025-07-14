// Yuliia Kruta - ICT221
package Fallin.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class GameEngine {

    private Cell[][] map;
    private Player player;
    private List<MutantData> mutantDataList;
    private int timeLimit = 100;
    private static final String TOP_SCORES_FILE = "top_scores.txt";
    private static List<Score> topScores = new ArrayList<>();

    /**
     * Constructor for GameEngine.
     * Creates a game board.
     *
     * @param size the width and height of the map.
     * @param difficulty the difficulty of the game.
     */
    public GameEngine(int size, int difficulty) {
        map = new Cell[size][size];
        player = new Player(size-1, 0); //Placing player on the bottom left cell (the entrance)
        mutantDataList = new ArrayList<>();
        generateMap(size, difficulty);
    }

    /* Getters for instance variables */

    public Cell[][] getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public List<MutantData> getMutants() {
        return mutantDataList;
    }

    public static List<Score> getTopScores() {
        return topScores;
    }

    /**
     * Generates a map.
     *
     * @param size the width and height of the map.
     * @param difficulty the difficulty of the game (number of mutants to create).
     */
    public void generateMap(int size, int difficulty){
        map[size-1][0] = new Entrance(size-1, 0);
        map[0][size-1] = new Exit (0, size-1);
        placeItems(size, Treasure.class, 8);
        placeItems(size, MedicalUnit.class, 2);
        placeItems(size, Trap.class, 5);
        placeItems(size, Mutant.class, difficulty);
    }


    /**
     * Places items randomly on the map.
     *
     * @param size the width and height of the map.
     * @param CellClass the class of the item to be placed on map (must be subclass of Cell).
     * @param quantity the number of items to put on the map.
     */
    public void placeItems(int size, Class<? extends Cell> CellClass, int quantity){
        Random random = new Random();
        int placed = 0;
        while (placed < quantity){
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (map[x][y] == null){ // If the cell is empty
                try{
                    /* Gets constructor of the item's class and creates an object of that class passing x and y coordinates as parameters */
                    map[x][y] = CellClass.getConstructor(int.class, int.class).newInstance(x, y);
                    if (map[x][y] instanceof Mutant) mutantDataList.add(new MutantData(x, y));
                    placed++;
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Checks if the coordinates of the move are within the map.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return true if move is valid, false otherwise.
     */
    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map.length;
    }

    /**
     * Validates inputted difficulty of the game.
     *
     * @param difficulty number of mutants.
     * @return true if difficulty value is valid.
     */
    public static boolean checkDifficulty(int difficulty){
        return difficulty>=0 && difficulty<=10;
    }

    /**
     * Moves the player (changes coordinates of the player).
     *
     * @param direction the direction to move.
     */
    public void movePlayer(int direction){
        int newX = player.getX();
        int newY = player.getY();

        switch (direction){
            case 1:
                newY--;
                break;
            case 2:
                newX--;
                break;
            case 3:
                newY++;
                break;
            case 4:
                newX++;
        }

        if((isValidMove(newX, newY))){
            player.incrementSteps();
            handleCell(newX, newY);
            player.setX(newX);
            player.setY(newY);
        }
    }

    /**
     * Randomly moves mutants on the map.
     */
    public void moveMutants() {
        Random random = new Random();
        List<MutantData> updatedMutants = new ArrayList<>();

        for (MutantData mutant : mutantDataList) {
            int x = mutant.x;
            int y = mutant.y;
            int newX = x;
            int newY = y;

            switch (random.nextInt(4)) {
                case 0: newX--; break;
                case 1: newX++; break;
                case 2: newY--; break;
                case 3: newY++; break;
            }

            if (isValidMove(newX, newY) && map[newX][newY] == null) {
                map[x][y] = null;
                map[newX][newY] = new Mutant(newX, newY);
                updatedMutants.add(new MutantData(newX, newY));
            } else {
                updatedMutants.add(new MutantData(x, y)); // keep original position
            }
        }

        mutantDataList = updatedMutants;
    }


    /**
     * Handles interaction with the cell.
     *
     * @param x coordinate of the cell.
     * @param y coordinate of the cell.
     */
    public void handleCell(int x, int y){
        Cell cell = map[x][y];
        if (cell != null) {
            if (cell instanceof Mutant) {
                removeMutantAt(x, y);
            }
            cell.handleInteraction(player, null, map, x, y);

        }
    }
    private void removeMutantAt(int x, int y) {
        mutantDataList.removeIf(m -> m.x == x && m.y == y);
    }



    /**
     * The size of the current game.
     *
     * @return this is both the width and the height.
     */
    public int getSize() {
        return map.length;
    }


    /**
     * Loads top scores from the file.
     */
    public static void loadTopScores() {
        topScores.clear(); // Clear before loading to prevent duplicates
        try (Scanner scanner = new Scanner(new File(TOP_SCORES_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                int score = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                topScores.add(new Score(score, date));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Top scores file not found. Creating a new one.");
        }
    }



    /**
     * Saves top scores records to the file.
     */
    private static void saveTopScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TOP_SCORES_FILE))) {
            for (Score score : topScores) {
                writer.println(score.getScore() + " " + score.getDate());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles updating top scores.
     *
     * @param score the score.
     */
    public static void handleTopScores(int score) {
        loadTopScores();
        LocalDate today = LocalDate.now();
        topScores.add(new Score(score, today));
        Collections.sort(topScores);
        if (topScores.size() > 5) {
            topScores.remove(topScores.size() - 1);
        }
        saveTopScores();
    }

    /**
     * Displays top scores.
     */
    private static void displayTopScores() {
        topScores.clear();
        loadTopScores();
        System.out.println("Top 5 Plays:");
        for (int i = 0; i < topScores.size(); i++) {
            Score s = topScores.get(i);
            System.out.println("#" + (i + 1) + " " + s.getScore() + " " + s.getDate());
        }
    }


    /**
     * Plays a text-based game
     */
    public static void main(String[] args) {
        while (true) {
            System.out.println("Choose the difficulty of the game (0-10): ");
            Scanner scanner = new Scanner(System.in);
            int difficulty = 5;
            while (true) {
                difficulty = scanner.nextInt();
                if (checkDifficulty(difficulty)) {
                    break;
                } else {
                    System.out.println("Please enter valid difficulty of the game (within 0-10 range): ");
                }
            }
            GameEngine engine = new GameEngine(10, difficulty);
            String result = "win";
            int score = -1;
            while (engine.player.getLife() > 0 && engine.player.getSteps() < 100) {
                int currentX = engine.player.getX();
                int currentY = engine.player.getY();
                if (engine.map[currentX][currentY] instanceof Exit) {
                    result = "win";
                    break;
                }
                engine.printMap();
                System.out.println("Please enter 1 to move left, 2 to move up, 3 to move right, 4 to move down: ");
                int nextMove;
                while (true) {
                    nextMove = scanner.nextInt();
                    if (nextMove == 1 || nextMove == 2 || nextMove == 3 || nextMove == 4) {
                        break;
                    } else {
                        System.out.println("Please enter valid move command (1 to move left, 2 to move up, 3 to move right, 4 to move down): ");
                    }
                }
                engine.moveMutants();
                engine.movePlayer(nextMove);
                result = "lose";
            }
            if (result.equals("win")) {
                score = 20 * engine.player.getTreasures() + (engine.timeLimit - engine.player.getSteps());
                System.out.println("Congratulations, you won! Your score is " + score);
            } else {
                System.out.println("Oh no, you lost! Your score is " + score);
            }

            if (score != -1) {
                handleTopScores(score);
            }
            displayTopScores();
            System.out.println("Would you like to play another game? Type Y for Yes or N for No");
            Scanner scanner2 = new Scanner(System.in);
            String anotherGame = scanner2.nextLine().toLowerCase();
            if(!anotherGame.equals("y"))break;
        }
    }

    /**
     * Prints map for text-based game
     */
    private void printMap() {
        System.out.println("Map legend: \nP - position of the player, S - start, E - exit, G - treasure (gold), T - trap, M - medical unit, X - mutant");
        System.out.println("Steps: " + player.getSteps() + ", Life: " + player.getLife() + ", Treasures: " + player.getTreasures());
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i == player.getX() && j == player.getY()) {
                    System.out.print("P ");
                } else if (map[i][j] != null) {
                    System.out.print(map[i][j].getSymbol() + " ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    private static SerializableCell[][] convertMapToSerializable(Cell[][] map) {
        int size = map.length;
        SerializableCell[][] serializableMap = new SerializableCell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] != null) {
                    serializableMap[i][j] = new SerializableCell(i, j, map[i][j].getClass().getSimpleName());
                }
            }
        }

        return serializableMap;
    }

    /**
     * Saves the current game state to a file.
     *
     * @param fileName the name of the file to save the game state to.
     * @throws IOException
     */
    public static void saveGame(String fileName, GameEngine engine) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(convertMapToSerializable(engine.map));
            outputStream.writeObject(engine.player);
            outputStream.writeObject(engine.mutantDataList);
            outputStream.writeInt(engine.timeLimit);
            outputStream.writeObject(topScores);
        }
    }

    private Cell[][] convertToCellMap(SerializableCell[][] sMap) {
        int size = sMap.length;
        Cell[][] map = new Cell[size][size];
        mutantDataList = new ArrayList<>(); // reset or reinitialize it

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                SerializableCell sc = sMap[i][j];
                if (sc != null) {
                    String type = sc.getType();
                    switch (type) {
                        case "Treasure":
                            map[i][j] = new Treasure(i, j);
                            break;
                        case "Trap":
                            map[i][j] = new Trap(i, j);
                            break;
                        case "MedicalUnit":
                            map[i][j] = new MedicalUnit(i, j);
                            break;
                        case "Mutant":
                            map[i][j] = new Mutant(i, j);
                            mutantDataList.add(new MutantData(i, j));
                            break;
                        case "Entrance":
                            map[i][j] = new Entrance(i, j);
                            break;
                        case "Exit":
                            map[i][j] = new Exit(i, j);
                            break;
                    }
                }
            }
        }

        return map;
    }




    /**
     * Loads a saved game state from a file.
     *
     * @param fileName the name of the file containing the saved game state.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void loadGame(String fileName, GameEngine engine) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            SerializableCell[][] sMap = (SerializableCell[][]) inputStream.readObject();
            engine.map = engine.convertToCellMap(sMap);
            engine.player = (Player) inputStream.readObject();
            engine.mutantDataList = (List<MutantData>) inputStream.readObject();
            engine.timeLimit = inputStream.readInt();
            topScores = (List<Score>) inputStream.readObject();
        }
    }




}
