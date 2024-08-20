import javax.swing.*;
import java.awt.*;


/**
 * TODO:
 * - fix main menu
 */
public class Main {

    private JFrame frame;
    private int windowWidth = 600;
    private int windowHeight = windowWidth;

    public Main() {
        initialize();
        startGame();
    }

    public void initialize() {
        frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);          //open window in middle of screen
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

    public static void main(String[] args) {
        new Main();
    }
}
