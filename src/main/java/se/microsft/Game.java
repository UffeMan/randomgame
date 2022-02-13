package se.microsft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Game {


    public static void main(String[] args) throws IOException, InterruptedException {

        char number;
        String playerName = "";
        String gameResult = "";
        List<String> gameResults = new ArrayList<>();
        EnemyFactory enemyFactory = new EnemyFactory();

        GameState gameState = GameState.START;

        // Game loop pattern
        Scanner sc = new Scanner(System.in);
        while (gameState != GameState.QUIT) {
            // State machine pattern
            switch (gameState) {
                case START:
                    spacer();
                    System.out.println("Hello! Vad heter du?\n>>> ");
                    playerName = sc.nextLine();
                    System.out.println("Hej, " + playerName);
                    gameState = GameState.MAINMENU;
                    break;
                case MAINMENU:
                    printMainMenu();
                    number = (char) System.in.read();
                    System.in.read();
                    gameState = switch (number) {
                        case '1' -> GameState.PLAY;
                        case '2' -> GameState.START;
                        case '3' -> GameState.STATISTICSMENU;
                        case '4' -> GameState.QUIT;
                        default -> gameState;
                    };
                    break;
                case PLAY:
                    String[] enemyNames = {"stalker", "timely", "sloppy"};
                    Enemy e, e1, e2;
                    char[] bet = {'1', '2', '3'};
                    int minPoang = 0;
                    int[] enemyPoang = {0, 0, 0};
                    for (int i = 0; i < enemyNames.length; i++) {
                        e = enemyFactory.createEnemy(enemyNames[i]);
                        e1 = enemyFactory.createEnemy(enemyNames[(i + 1) % enemyNames.length]);
                        e2 = enemyFactory.createEnemy(enemyNames[(i + 2) % enemyNames.length]);
                        number = ' ';
                        while (!(number == '1' || number == '2' || number == '3')) {
                            printChoice(enemyNames[i]);
                            number = (char) System.in.read();
                            System.in.read();
                        }
                        int choice = e.getEnemyMove(playerName);
                        String winner = evalMyGame(number, bet[choice], playerName, enemyNames[i]);
                        if (winner.matches(".*?\\bvann\\b.*?")) minPoang++;
                        if (winner.matches(".*?\\bförlorade\\b.*?")) enemyPoang[i]++;
                        System.out.println(winner);
                        System.out.println(minPoang);
                        System.out.println(enemyPoang[i]);
                        int choice1 = e1.getEnemyMove(enemyNames[(i + 1) % enemyNames.length]);
                        int choice2 = e2.getEnemyMove(enemyNames[(i + 2) % enemyNames.length]);
                        winner = evalMyGame(bet[choice1], bet[choice2], enemyNames[(i + 1) % enemyNames.length], enemyNames[(i + 2) % enemyNames.length]);
                        if (Pattern.compile(".*?\\bvann\\b.*?").matcher(winner).matches())
                            enemyPoang[(i + 1) % enemyNames.length]++;
                        if (Pattern.compile(".*?\\bförlorade\\b.*?").matcher(winner).matches())
                            enemyPoang[(i + 2) % enemyNames.length]++;
                        System.out.println(winner);
                        System.out.println(enemyPoang[(i + 1) % enemyNames.length]);
                        System.out.println(enemyPoang[(i + 2) % enemyNames.length]);
                    }

                    gameResult = "---------------\n";

                    gameResult = gameResult + playerName + ": " + minPoang + "\n";
                    for (int i = 0; i < enemyNames.length; i++) {
                        gameResult = gameResult + enemyNames[i] + ": " + enemyPoang[i] + "\n";
                    }
                    gameResult += "---------------\n";
                    gameResults.add(gameResult);
                    System.out.println(gameResult);

                    gameState = GameState.MAINMENU;
                    break;
                case STATISTICSMENU:
                    printStaticMenu();
                    number = (char) System.in.read();
                    System.in.read();
                    gameState = switch (number) {
                        case '1' -> GameState.SHOWLAST;
                        case '2' -> GameState.SHOWHISTORY;
                        case '3' -> GameState.MAINMENU;
                        default -> gameState;
                    };
                    break;
                case SHOWHISTORY:
                    for (int i = 0; i < gameResults.size(); i++) {
                        System.out.println(gameResults.get(i));
                        gameState = gameState.STATISTICSMENU;
                    }
                    break;
                case SHOWLAST:
                    System.out.println(gameResults.get(gameResults.size() - 1));
                    gameState = gameState.STATISTICSMENU;
                    break;
                default:
                    break;
            }
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }

    private static void spacer() {
        System.out.println("\n\n");
    }

    private static void printMainMenu() {
        spacer();
        System.out.println("1. Nytt spel");
        System.out.println("2. Byt namn");
        System.out.println("3. Tidigare spelomgångar");
        System.out.println("4. Avsluta");
    }

    private static void printStaticMenu() {
        spacer();
        System.out.println("1. Visa sista spelomgången");
        System.out.println("2. Visa alla spelomgångar");
        System.out.println("3. Tillbaka");
    }

    private static void printChoice(String enemyName) {
        spacer();
        System.out.println("Du spelar mot " + enemyName);
        System.out.println("Jag satsar på.....");
        System.out.println("1. Sten");
        System.out.println("2. Sax");
        System.out.println("3. Påse");
    }

    private static String evalMyGame(char playerNumber, char enemyChoice, String playerName, String enemyName) {
        String r = "";
        if (playerNumber == enemyChoice) r = "Oavgjort mellan " + playerName + " och " + enemyName;
        else if (playerNumber == '1' && enemyChoice == '2')
            r = playerName + " vann mot " + enemyName;           //sten-sax
        else if (playerNumber == '1' && enemyChoice == '3')
            r = playerName + " förlorade mot " + enemyName;      //sten-påse
        else if (playerNumber == '2' && enemyChoice == '1')
            r = playerName + " förlorade mot " + enemyName;      //sax-sten
        else if (playerNumber == '2' && enemyChoice == '3')
            r = playerName + " vann mot " + enemyName;           //sax-påse
        else if (playerNumber == '3' && enemyChoice == '1')
            r = playerName + " vann mot " + enemyName;           //påse-sten
        else if (playerNumber == '3' && enemyChoice == '2')
            r = playerName + " förlorade mot " + enemyName;      //påse-sax
        return r;
    }
}
