package com.smansu4;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CardFrame {

    JFrame frame;
    JPanel cards;
    JPanel optionsPanel;
    JPanel gamePanel;
    JPanel gameOverPanel;

    CardLayout cardLayout;

    public CardFrame() {
        frame = new JFrame();
        cardLayout = new CardLayout();
        //cardLayout.addLayoutComponent(optionsPanel, "optionsPanel");
        cards = new JPanel(cardLayout);

        optionsPanel = new OptionsPanel(this);
        gamePanel = new GamePanel();
        gameOverPanel = new GameOverPanel();


        cards.add(optionsPanel, "options");
        cards.add(gamePanel, "game");
        cards.add(gameOverPanel, "game_over");

        //cardLayout = (CardLayout)(cards.getLayout());
        //cardLayout.show(optionsPanel, "options");

        frame = new JFrame();
        frame.add(cards);
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);          //open window in middle of screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        optionsPanel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName() == "Play"){
                    cardLayout.show(cards, "game");
                }
            }
        });

    }

    public void notify(String panel) {
        this.cardLayout.show(gameOverPanel, "game_over");
    }

    public static void main(String[] args) {
        CardFrame cardFrame = new CardFrame();
    }
}
