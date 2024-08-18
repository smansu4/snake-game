import javax.swing.*;

public class Main {

    JFrame frame;
    int windowWidth = 600;
    int windowHeight = windowWidth;

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

        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

    public static void main(String[] args) {
        new Main();
    }
}
