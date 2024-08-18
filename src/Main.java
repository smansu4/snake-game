import theme.OkabeItoTheme;

import javax.swing.*;

public class Main {

    private JFrame frame;
    private int windowWidth = 600;
    private int windowHeight = windowWidth;

    public Main() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);          //open window in middle of screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight, new OkabeItoTheme());
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

    public static void main(String[] args) {
        new Main();
    }
}
