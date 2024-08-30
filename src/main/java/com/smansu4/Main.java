package com.smansu4;

import com.smansu4.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    private JFrame frame;
    private int windowWidth = 600;
    private int windowHeight = windowWidth;
    private Theme colorTheme = Theme.getInstance();
    private boolean isMultiplayer = false;

    JRadioButton darkThemeRadioBtn = new JRadioButton("Dark");
    JRadioButton lightThemeRadioBtn = new JRadioButton("Light");

    JRadioButton singlePlayerRadioBtn = new JRadioButton("Single Player");
    JRadioButton multiPlayerRadioBtn = new JRadioButton("Multi Player");

    JPanel optionsMenuPanel = setUpOptionsMenu();


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
        frame.add(optionsMenuPanel);
    }

    public void startGame() {
        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight, colorTheme, isMultiplayer);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

    public JPanel setUpOptionsMenu() {
        JPanel optionsMenuPanel = new JPanel();

        Insets inset_top_0 = new Insets(0, 0, 0, 0);
        Insets inset_top_20 = new Insets(20, 0, 0, 0);

        JLabel optionsLabel = new JLabel("Snake Game");
        optionsLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        optionsLabel.setFont(optionsLabel.getFont().deriveFont(Font.BOLD));

        optionsMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints optionsPanelLayoutConstraint = new GridBagConstraints();

        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.fill = GridBagConstraints.HORIZONTAL;
        optionsPanelLayoutConstraint.gridx = 0;
        optionsPanelLayoutConstraint.gridy = 0;
        optionsMenuPanel.add(optionsLabel, optionsPanelLayoutConstraint);


        //Color Theme Layout
        JLabel themeSelectionLabel = new JLabel("Select a Color Theme: ");

        optionsPanelLayoutConstraint.insets = inset_top_20;
        optionsPanelLayoutConstraint.gridy = 1;
        optionsMenuPanel.add(themeSelectionLabel, optionsPanelLayoutConstraint);


        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.gridy = 2;

        darkThemeRadioBtn.setSelected(true);
        darkThemeRadioBtn.addActionListener(this);
        optionsMenuPanel.add(darkThemeRadioBtn,optionsPanelLayoutConstraint);

        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.gridy = 3;
        lightThemeRadioBtn.addActionListener(this);
        optionsMenuPanel.add(lightThemeRadioBtn, optionsPanelLayoutConstraint);


        //Game Mode Layout
        JLabel gameModeLabel = new JLabel("Select a Game Mode: ");

        optionsPanelLayoutConstraint.insets = inset_top_20;
        optionsPanelLayoutConstraint.gridy = 4;
        optionsMenuPanel.add(gameModeLabel, optionsPanelLayoutConstraint);


        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.gridy = 5;
        singlePlayerRadioBtn.setSelected(true);
        singlePlayerRadioBtn.addActionListener(this);
        optionsMenuPanel.add(singlePlayerRadioBtn, optionsPanelLayoutConstraint);

        optionsPanelLayoutConstraint.gridy = 6;
        multiPlayerRadioBtn.addActionListener(this);
        optionsMenuPanel.add(multiPlayerRadioBtn, optionsPanelLayoutConstraint);


        //Back Button Layout
        JButton playButton = new JButton("Play");

        optionsPanelLayoutConstraint.insets = inset_top_20;
        optionsPanelLayoutConstraint.anchor = GridBagConstraints.PAGE_END; //bottom of space
        optionsPanelLayoutConstraint.gridx = 0;
        optionsPanelLayoutConstraint.gridy = 7;

        optionsMenuPanel.add(playButton, optionsPanelLayoutConstraint);
        playButton.addActionListener(this);

        return optionsMenuPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Dark":
                darkThemeRadioBtn.setSelected(true);
                lightThemeRadioBtn.setSelected(false);
                colorTheme.useClassicTheme(true);
                break;
            case "Light":
                darkThemeRadioBtn.setSelected(false);
                lightThemeRadioBtn.setSelected(true);
                colorTheme.useClassicTheme(false);
                break;
            case "Single Player":
                singlePlayerRadioBtn.setSelected(true);
                multiPlayerRadioBtn.setSelected(false);
                isMultiplayer = false;
                break;
            case "Multi Player":
                singlePlayerRadioBtn.setSelected(false);
                multiPlayerRadioBtn.setSelected(true);
                isMultiplayer = true;
                break;
            case "Play":
                frame.remove(optionsMenuPanel);
                optionsMenuPanel.removeAll();
                optionsMenuPanel.setVisible(false);
                frame.revalidate();
                startGame();
                break;
            default:
                System.out.println("User selected unimplemented action" + e.getActionCommand());
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
