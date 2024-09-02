package com.smansu4.panels;

import com.smansu4.enums.GameStateAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameOverPanel extends JPanel implements ActionListener, KeyListener {
    private String mainMenuBtnText = "Main Menu";

    JLabel gameOverLabel = new JLabel("Game Over");
    JLabel highScoreLabel = new JLabel();
    JLabel player1Score = new JLabel();
    JLabel player2Score = new JLabel();
    JLabel replayLabel = new JLabel("Press space bar to replay");
    JButton mainMenuButton = new JButton(mainMenuBtnText);

    int highScore = 0;
    int p1Score = 0;
    int p2Score = -1;

    public GameOverPanel(int windowWidth, int windowHeight) {
        this.setSize(windowWidth, windowHeight);
    }

    public void initialize() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        this.add(gameOverLabel, constraints);

        constraints.gridy = 1;
        highScoreLabel.setText("High Score: " + highScore);
        this.add(highScoreLabel, constraints);

        constraints.gridy = 2;
        player1Score.setText("Player 1 Score: " + p1Score);
        this.add(player1Score, constraints);

        if(p2Score != -1) {
            constraints.gridy = 3;
            player2Score.setText("Player 2 Score: " + p2Score);
            this.add(player2Score, constraints);
        };

        constraints.gridy = 4;
        this.add(replayLabel, constraints);

        constraints.gridy = 5;
        mainMenuButton.addActionListener(this);
        this.add(mainMenuButton, constraints);
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setP1Score(int score) {
        this.p1Score = score;
    }

    public void serP2Score(int score) {
        this.p2Score = score;
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
