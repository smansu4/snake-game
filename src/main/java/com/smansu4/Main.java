package com.smansu4;

import com.smansu4.enums.GameStateAction;
import com.smansu4.enums.Panels;
import com.smansu4.panels.GameOverPanel;
import com.smansu4.panels.GamePanel;
import com.smansu4.panels.OptionsPanel;
import com.smansu4.theme.Theme;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private final int windowWidth = 600;
    private final int windowHeight = 628;

    JPanel cards;
    CardLayout cardLayout;
    OptionsPanel optionsPanel;
    GamePanel gamePanel;
    GameOverPanel gameOverPanel;

    public Main() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
        frame.setLocationRelativeTo(null);          //open window in middle of screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardSetUp();
    }

    public void cardSetUp() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        frame.add(cards);

        optionsPanel = new OptionsPanel(windowWidth, windowHeight);
        gamePanel = new GamePanel();
        gameOverPanel = new GameOverPanel(windowWidth, windowHeight);

        //Add panels to the card panel
        cards.add(optionsPanel, Panels.OPTION.toString());
        cards.add(gamePanel, Panels.GAME.toString());
        cards.add(gameOverPanel, Panels.GAME_OVER.toString());

        frame.pack();

        //Add a listener for Options panel state changes
        optionsPanel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "Play":
                    cardLayout.show(cards, Panels.GAME.toString());
                    gamePanel.startGame();
                    break;
                case "Multi Player":
                    gamePanel.setMultiplayer(true);
                    gameOverPanel.setIsMultiPlayer(true);
                    break;
                case "Single Player":
                    gamePanel.setMultiplayer(false);
                    gameOverPanel.setIsMultiPlayer(false);
                    break;
                case "Dark":
                    Theme.getInstance().setTheme(Theme.ThemeEnum.DARK_THEME);
                    break;
                case "Light":
                    Theme.getInstance().setTheme(Theme.ThemeEnum.LIGHT_THEME);
                    break;
                default:
                    System.out.println("Unknown property: " + evt.getPropertyName());
            }
        });

        //Add a listener for game panel state changes
        gamePanel.addPropertyChangeListener(evt -> {
            if(GameStateAction.GAME_OVER.toString().equals(evt.getPropertyName())){
                gameOverPanel.setFocusable(true);
                gameOverPanel.requestFocusInWindow();
                gameOverPanel.initialize();
                cardLayout.show(cards, Panels.GAME_OVER.toString());
            } if("highScore".equals(evt.getPropertyName())) {
                gameOverPanel.setHighScore((Integer) evt.getNewValue());
            }if("p1Score".equals(evt.getPropertyName())) {
                gameOverPanel.setP1Score((Integer) evt.getNewValue());
            }if("p2Score".equals(evt.getPropertyName())) {
                gameOverPanel.setP2Score((Integer) evt.getNewValue());
            }
        });

        //Add a listener for game over panel state changes
        //main menu not needed because the card shows the top
        // card in deck so action not needed to change cards here
        gameOverPanel.addPropertyChangeListener(evt -> {
            if(GameStateAction.PLAY.toString().equals(evt.getPropertyName())) {
                cardLayout.show(cards, Panels.GAME.toString());
                gamePanel.startGame();
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}
