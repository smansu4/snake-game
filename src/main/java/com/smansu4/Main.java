package com.smansu4;

import com.smansu4.panels.GameOverPanel;
import com.smansu4.panels.GamePanel;
import com.smansu4.panels.OptionsPanel;
import com.smansu4.theme.Theme;

import javax.swing.*;
import java.awt.*;

import static com.smansu4.constants.Constants.*;

public class Main {
    private JFrame frame;
    private final int windowWidth = 600;
    private final int windowHeight = 628;

    private JPanel cards;
    private CardLayout cardLayout;
    private OptionsPanel optionsPanel;
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;

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
        cards.add(optionsPanel, OPTIONS_PANEL);
        cards.add(gamePanel, GAME_PANEL);
        cards.add(gameOverPanel, GAME_OVER_PANEL);

        frame.pack();

        //Add a listener for Options panel state changes
        optionsPanel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "Play":
                    cardLayout.show(cards, GAME_PANEL);
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
            if(GAME_OVER.equals(evt.getPropertyName())){
                cardLayout.show(cards, GAME_OVER_PANEL);
                gameOverPanel.initialize();
            } if(HIGH_SCORE.equals(evt.getPropertyName())) {
                gameOverPanel.setHighScore((Integer) evt.getNewValue());
            } if(P1_SCORE.equals(evt.getPropertyName())) {
                gameOverPanel.setP1Score((Integer) evt.getNewValue());
            } if(P2_SCORE.equals(evt.getPropertyName())) {
                gameOverPanel.setP2Score((Integer) evt.getNewValue());
            }
        });

        //Add a listener for game over panel state changes
        //main menu not needed because the card shows the top
        // card in deck so action not needed to change cards here
        gameOverPanel.addPropertyChangeListener(evt -> {
            if(REPLAY.equals(evt.getPropertyName())) {
                cardLayout.show(cards, GAME_PANEL);
                gamePanel.startGame();
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}
