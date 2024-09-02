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
    JPanel statsPanel = new JPanel();                               //Create a new panel for better component alignment
    JLabel gameOverLabel = new JLabel("Game Over");
    JLabel replayLabel = new JLabel("Press space bar to replay");
    JLabel highScoreLabel = new JLabel();
    JLabel player1Score = new JLabel();
    JLabel player2Score = new JLabel();
    JButton mainMenuButton = new JButton(mainMenuBtnText);

    boolean isMultiPlayer = false;
    int highScore = 0;
    int p1Score = 0;
    int p2Score = 0;

    public GameOverPanel(int windowWidth, int windowHeight) {
        this.setSize(windowWidth, windowHeight);
    }

    public void initialize() {
        this.add(statsPanel);
        Insets inset_top_10 = new Insets(10, 0, 0, 0);
        Insets inset_top_20 = new Insets(20, 0, 0, 0);
        Insets inset_top_150 = new Insets(150, 0, 0, 0);


        statsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = inset_top_150;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;

        gameOverLabel.setFont(new Font(gameOverLabel.getFont().getFontName(), Font.BOLD, 52));

        statsPanel.add(gameOverLabel, constraints);

        constraints.insets = inset_top_10;
        constraints.gridy = 1;
        highScoreLabel.setText("Current High Score: " + highScore);
        highScoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        statsPanel.add(highScoreLabel, constraints);

        if(isMultiPlayer) {
            constraints.gridy = 2;
            player1Score.setText("Right Player Score: " + p1Score);
            player1Score.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
            statsPanel.add(player1Score, constraints);

            constraints.gridy = 3;
            player2Score.setText("Left Player Score: " + p2Score);
            player2Score.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
            statsPanel.add(player2Score, constraints);
        } else {
            constraints.gridy = 2;
            player1Score.setText("Your Score: " + p1Score);
            player1Score.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
            statsPanel.add(player1Score, constraints);
        }

        constraints.insets = inset_top_20;
//        constraints.gridy = 4;
//        statsPanel.add(replayLabel, constraints);

        constraints.gridy = 5;
        mainMenuButton.addActionListener(this);
        statsPanel.add(mainMenuButton, constraints);
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setP1Score(int score) {
        this.p1Score = score;
    }

    public void setP2Score(int score) {
        this.p2Score = score;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(mainMenuBtnText)) {
            this.setVisible(false);
            this.removeAll();
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

    public void setIsMultiPlayer(boolean isMultiPlayer) {
        this.isMultiPlayer = isMultiPlayer;
    }

    //Unused
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
