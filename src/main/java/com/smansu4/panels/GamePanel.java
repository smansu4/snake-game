package com.smansu4.panels;

import com.smansu4.panels.snake.SnakeGame;
import com.smansu4.enums.GameStateAction;
import com.smansu4.theme.Theme;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GamePanel extends JPanel {
    private final int windowWidth;
    private final int windowHeight;
    private boolean isMultiplayer;

    public GamePanel(int windowWidth, int windowHeight) {
        this.setSize(windowWidth, windowHeight);

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.isMultiplayer = false;
    }

    public void startGame() {
        // create new game each play to clear state;
        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight, isMultiplayer);
        this.setVisible(true);
        this.add(snakeGame);
        snakeGame.requestFocus();

        snakeGame.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(GameStateAction.GAME_OVER.toString().equals(evt.getPropertyName())){
                    snakeGame.removeAll();
                    snakeGame.setVisible(false);
                    remove(snakeGame);
                    setVisible(false);
                    firePropertyChange(GameStateAction.GAME_OVER.toString(), false, true);
                } else if ("highScore".equals(evt.getPropertyName())){
                    System.out.println(evt.getPropertyName());
                    firePropertyChange("highScore", -1, evt.getNewValue());
                } else if ("p1Score".equals(evt.getPropertyName())){
                    System.out.println(evt.getPropertyName());
                    firePropertyChange("p1Score", -1, evt.getNewValue());
                }else if ("p2Score".equals(evt.getPropertyName())){
                    System.out.println(evt.getPropertyName());
                    firePropertyChange("p2Score", -1, evt.getNewValue());
                }
            }
        });
        snakeGame.startGame();
    }

    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }
}
