package com.smansu4.panels;

import com.smansu4.snake.SnakeGame;
import com.smansu4.enums.GameStateAction;

import javax.swing.*;

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
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        SnakeGame snakeGame = new SnakeGame(GAME_WIDTH, GAME_HEIGHT, isMultiplayer);
        this.setVisible(true);
        this.add(snakeGame);
        snakeGame.requestFocus();

        snakeGame.addPropertyChangeListener(evt -> {
            if(GameStateAction.GAME_OVER.toString().equals(evt.getPropertyName())){
                snakeGame.removeAll();
                snakeGame.setVisible(false);
                remove(snakeGame);
                setVisible(false);
                firePropertyChange(GameStateAction.GAME_OVER.toString(), false, true);
            } else if ("highScore".equals(evt.getPropertyName())){
                firePropertyChange("highScore", -1, evt.getNewValue());
            } else if ("p1Score".equals(evt.getPropertyName())){
                firePropertyChange("p1Score", -1, evt.getNewValue());
            }else if ("p2Score".equals(evt.getPropertyName())){
                firePropertyChange("p2Score", -1, evt.getNewValue());
            }
        });
        snakeGame.startGame();
    }

    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }
}
