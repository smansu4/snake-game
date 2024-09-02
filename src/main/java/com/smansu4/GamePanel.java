package com.smansu4;

import com.smansu4.theme.Theme;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GamePanel extends JPanel {
    private final int windowWidth;
    private final int windowHeight;
    private final Theme theme;
    private final boolean isMultiplayer;

    public GamePanel(int windowWidth, int windowHeight, Theme colorTheme, boolean isMultiplayer) {
        this.setSize(windowWidth, windowHeight);

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.theme = colorTheme;
        this.isMultiplayer = isMultiplayer;
    }

    public void startGame() {
        // create new game each play to clear state;
        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight, theme, isMultiplayer);
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
                }
            }
        });
        snakeGame.startGame();
    }
}
