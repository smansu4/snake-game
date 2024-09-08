package com.smansu4.panels;

import com.smansu4.snake.SnakeGame;

import javax.swing.*;

import static com.smansu4.constants.Constants.*;

public class GamePanel extends JPanel {
    // Dimensions for game board; smaller than frame size to fit window
    // Using the frame size will push bottom of game panel past bottom
    // of game window. Frame size accounts for menu panel on top of window
    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 600;
    private boolean isMultiplayer;

    public GamePanel() {
        this.isMultiplayer = false;
    }

    public void startGame() {
        // create new game each play to clear state;
        SnakeGame snakeGame = new SnakeGame(GAME_WIDTH, GAME_HEIGHT, isMultiplayer);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(true);
        this.add(snakeGame);
        snakeGame.requestFocus();

        snakeGame.addPropertyChangeListener(evt -> {
            int defaultOldValue = -1;
            if(GAME_OVER.equals(evt.getPropertyName())){
                snakeGame.removeAll();
                snakeGame.setVisible(false);
                remove(snakeGame);
                setVisible(false);
                firePropertyChange(GAME_OVER, false, true);
            } else if (HIGH_SCORE.equals(evt.getPropertyName())){
                firePropertyChange(HIGH_SCORE, defaultOldValue, evt.getNewValue());
            } else if (P1_SCORE.equals(evt.getPropertyName())){
                firePropertyChange(P1_SCORE, defaultOldValue, evt.getNewValue());
            } else if (P2_SCORE.equals(evt.getPropertyName())){
                firePropertyChange(P2_SCORE, defaultOldValue, evt.getNewValue());
            }
        });
        snakeGame.startGame();
    }

    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }
}
