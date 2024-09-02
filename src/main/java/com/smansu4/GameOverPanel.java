package com.smansu4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameOverPanel extends JPanel implements ActionListener, KeyListener {
    private String mainMenuBtnText = "Main Menu";

    JLabel gameOverLabel = new JLabel("Game Over");
    JLabel highScoreLabel = new JLabel("High Score: ");
    JLabel replayLabel = new JLabel("Press space bar to replay");
    JButton mainMenuButton = new JButton(mainMenuBtnText);

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
        if (e.getActionCommand().equals(mainMenuBtnText)) {
            this.setVisible(false);
            this.firePropertyChange(GameStateAction.GO_TO_MENU.toString(), false, true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.setVisible(false);
            this.firePropertyChange(GameStateAction.PLAY.toString(), false, true);
        }
    }

    //Unused
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
