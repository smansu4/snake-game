package com.smansu4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel implements ActionListener {
    JLabel gameOverLabel = new JLabel("Game Over");
    JLabel highScoreLabel = new JLabel("High Score: ");
    JLabel replayLabel = new JLabel("Press space bar to replay");
    JButton mainMenuButton = new JButton("Main Menu");

    public GameOverPanel(int windowWidth, int windowHeight) {
        this.setSize(windowWidth, windowHeight);

        initializePanel();
    }

    public void initializePanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        this.add(gameOverLabel, constraints);

        constraints.gridy = 1;
        this.add(highScoreLabel, constraints);

        constraints.gridy = 2;

        this.add(replayLabel, constraints);

        constraints.gridy = 3;
        mainMenuButton.addActionListener(this);
        this.add(mainMenuButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (e.getActionCommand().equals("Main Menu")) {
            this.setVisible(false);
            this.firePropertyChange(GameStateAction.GO_TO_MENU.toString(), false, true);
        }
    }
}
