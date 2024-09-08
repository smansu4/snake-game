package com.smansu4.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.smansu4.constants.Constants.*;

public class GameOverPanel extends JPanel implements ActionListener, KeyListener {
    private final String mainMenuBtnText = "Main Menu";
    private final JButton mainMenuButton = new JButton(mainMenuBtnText);
    private final JLabel gameOverLabel = new JLabel("Game Over");
    private final JLabel replayLabel = new JLabel("Press space bar to replay");
    private final JLabel highScoreLabel = new JLabel();
    private final JLabel player1Score = new JLabel();
    private final JLabel player2Score = new JLabel();

    private boolean isMultiPlayer = false;
    private int highScore = 0;
    private int p1Score = 0;
    private int p2Score = 0;

    public GameOverPanel(int windowWidth, int windowHeight) {
        this.setSize(windowWidth, windowHeight);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    public void initialize() {
        Insets inset_top_10 = new Insets(10, 0, 0, 0);
        Insets inset_top_20 = new Insets(20, 0, 0, 0);

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        gameOverLabel.setFont(new Font(gameOverLabel.getFont().getFontName(), Font.BOLD, 52));

        this.add(gameOverLabel, constraints);

        constraints.insets = inset_top_10;
        constraints.gridy = 1;
        highScoreLabel.setText("Current High Score: " + highScore);
        String TEXT_FONT = "Lucida Grande";
        highScoreLabel.setFont(new Font(TEXT_FONT, Font.PLAIN, 18));
        this.add(highScoreLabel, constraints);

        if(isMultiPlayer) {
            constraints.gridy = 2;
            player1Score.setText("Right Player Score: " + p1Score);
            player1Score.setFont(new Font(TEXT_FONT, Font.PLAIN, 18));
            this.add(player1Score, constraints);

            constraints.gridy = 3;
            player2Score.setText("Left Player Score: " + p2Score);
            player2Score.setFont(new Font(TEXT_FONT, Font.PLAIN, 18));
            this.add(player2Score, constraints);
        } else {
            constraints.gridy = 2;
            player1Score.setText("Your Score: " + p1Score);
            player1Score.setFont(new Font(TEXT_FONT, Font.PLAIN, 18));
            this.add(player1Score, constraints);
        }

        constraints.gridy = 4;
        replayLabel.setFont(new Font(TEXT_FONT, Font.PLAIN, 12));
        this.add(replayLabel, constraints);

        constraints.insets = inset_top_20;
        constraints.gridy = 5;
        mainMenuButton.addActionListener(this);
        this.add(mainMenuButton, constraints);

        //Once initialized and made visible, the component can be focused on;
        requestFocus();
    }

    public void setIsMultiPlayer(boolean isMultiPlayer) {
        this.isMultiPlayer = isMultiPlayer;
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
            this.firePropertyChange(GO_TO_MENU, false, true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.firePropertyChange(REPLAY, false, true);
        }
    }

    //Not used
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
