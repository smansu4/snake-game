package com.smansu4;

import com.smansu4.theme.Theme;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private final int windowWidth = 600;
    private final int windowHeight = 600;
    private final Theme colorTheme = Theme.getInstance();
    private boolean isMultiplayer = false;

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
        gamePanel = new GamePanel(windowWidth, windowHeight, colorTheme, isMultiplayer);
        gameOverPanel = new GameOverPanel(windowWidth, windowHeight);

        //Add panels to the card panel
        cards.add(optionsPanel, Panels.OPTION.toString());
        cards.add(gamePanel, Panels.GAME.toString());
        cards.add(gameOverPanel, Panels.GAME_OVER.toString());

        //Add a listener for Options panel state changes
        optionsPanel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "Play":
                    cardLayout.show(cards, Panels.GAME.toString());
                    gamePanel.startGame();
                    break;
                case "Multi Player":
                    isMultiplayer = true;
                    break;
                case "Single Player":
                    isMultiplayer = false;
                    break;
                case "Dark":
                    colorTheme.setTheme(Theme.ThemeEnum.DARK_THEME);
                    break;
                case "Light":
                    colorTheme.setTheme(Theme.ThemeEnum.LIGHT_THEME);
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
                cardLayout.show(cards, Panels.GAME_OVER.toString());
            }
        });

//        //Add a listener for game over panel state changes
        //main menu not needed because the card shows the top card in deck so action not needed to change cards here.
        gameOverPanel.addPropertyChangeListener(evt -> {
            System.out.println(evt.getPropertyName());
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
