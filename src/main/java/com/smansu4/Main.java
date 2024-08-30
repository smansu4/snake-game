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

    JPanel titlePanel = new JPanel();
    JPanel optionsMenuPanel = new JPanel();

    JButton playButton = new JButton("Play");
    JButton backButton = new JButton("Back");
    JRadioButton classicThemeRadioBtn = new JRadioButton("Classic");
    JRadioButton okabeItoThemeRadioBtn = new JRadioButton("Okabe Ito");

    JRadioButton singlePlayerRadioBtn = new JRadioButton("Single Player");
    JRadioButton multiPlayerRadioBtn = new JRadioButton("Multi Player");


    public Main() {
        initialize();

        setUpTitlePage();
        setUpOptionsMenu();
        frame.add(titlePanel);
    }

    public void initialize() {
        frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);          //open window in middle of screen
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight, colorTheme, isMultiplayer);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

    public void setUpTitlePage() {
        JLabel titleLabel = new JLabel(" SNAKE GAME");

        titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 52));
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        titleLabel.setSize(new Dimension(200, 200));
        titleLabel.setForeground(Color.GREEN);

        titlePanel.setBackground(Color.BLACK);
        titlePanel.setLayout(new GridBagLayout());

        GridBagConstraints titlePanelLayoutConstraint = new GridBagConstraints();

        titlePanelLayoutConstraint.insets = new Insets(50,0,0,0);  //top padding
        titlePanelLayoutConstraint.fill = GridBagConstraints.HORIZONTAL;
        titlePanelLayoutConstraint.anchor= GridBagConstraints.CENTER;
        titlePanelLayoutConstraint.gridx = 1;
        titlePanelLayoutConstraint.gridy = 0;

        titlePanel.add(titleLabel, titlePanelLayoutConstraint);

        playButton.setPreferredSize(new Dimension(100, 50));
        playButton.setFont(new Font("Serif", Font.PLAIN, 42));
        playButton.setFont(playButton.getFont().deriveFont(Font.BOLD));
        playButton.addActionListener(this);

        titlePanelLayoutConstraint.ipady = 40;      //make this component tall
        titlePanelLayoutConstraint.weightx = 0.0;
        titlePanelLayoutConstraint.gridwidth = 2;
        titlePanelLayoutConstraint.gridx = 0;
        titlePanelLayoutConstraint.gridy = 1;

        titlePanel.add(playButton, titlePanelLayoutConstraint);

        JButton optionsButton = new JButton("Options");
        optionsButton.addActionListener(this);

        titlePanelLayoutConstraint.insets = new Insets(50,50,0,50);  //top padding
        titlePanelLayoutConstraint.anchor = GridBagConstraints.PAGE_END; //bottom of space
        titlePanelLayoutConstraint.gridwidth = 1;   //2 columns wide
        titlePanelLayoutConstraint.ipady = 0;       //reset to default
        titlePanelLayoutConstraint.gridx = 1;       //aligned with button 2
        titlePanelLayoutConstraint.gridy = 2;       //third row

        titlePanel.add(optionsButton, titlePanelLayoutConstraint);
    }

//    private void test() {
//        JButton button;
//        JLabel label;
//        pane.setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//        label = new JLabel(" SNAKE GAME");
//        label.setFont(new Font("Lucida Grande", Font.PLAIN, 52));
//        label.setFont(label.getFont().deriveFont(Font.BOLD));
//        c.insets = new Insets(50,0,0,0);  //top padding
//
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 1;
//        c.gridy = 0;
//        c.anchor= GridBagConstraints.CENTER;
//
//
//       label.setSize(new Dimension(200, 200));
//        pane.add(label, c);
//
////        button = new JButton("Button 2");
////        c.fill = GridBagConstraints.HORIZONTAL;
////        c.weightx = 0.5;
////        c.gridx = 1;
////        c.gridy = 0;
////        pane.add(button, c);
////
////        button = new JButton("Button 3");
////        c.fill = GridBagConstraints.HORIZONTAL;
////        c.weightx = 0.5;
////        c.gridx = 2;
////        c.gridy = 0;
////        pane.add(button, c);
//
//        button = new JButton("PLAY");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        button.setPreferredSize(new Dimension(100, 50));
//        button.setFont(new Font("Serif", Font.PLAIN, 42));
//        button.setFont(button.getFont().deriveFont(Font.BOLD));
//        //button.setForeground(Color.GREEN);
//        //button.setBackground(Color.YELLOW);
//        //button.setOpaque(true);
//        //button.setBorderPainted(false);
//        //Border border = BorderFactory.createLineBorder(Color.BLACK);
//        //button.setBorder(border);
//
//
//        c.insets = new Insets(50,0,0,0);  //top padding
//
//
//
//        c.ipady = 40;      //make this component tall
//        //c.ipadx = 200;
//        c.weightx = 0.0;
//        c.gridwidth = 2;
//        c.gridx = 0;
//        c.gridy = 1;
//        pane.add(button, c);
//
//        button = new JButton("Options");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 0;       //reset to default
//        //c.weighty = 1.0;   //request any extra vertical space
//        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
//        c.insets = new Insets(50,50,0,50);  //top padding
//        c.gridx = 1;       //aligned with button 2
//        c.gridwidth = 1;   //2 columns wide
//        c.gridy = 2;       //third row
//        pane.add(button, c);
//    }

    public void setUpOptionsMenu() {
        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 48));

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        titleLabel.setSize(70, 70);

        JLabel themeSelectionLabel = new JLabel("Select a Color Theme: ");
        JLabel gameModeLabel = new JLabel("Select a Game Mode: ");
        optionsMenuPanel.setLayout(new BoxLayout(optionsMenuPanel, BoxLayout.Y_AXIS));
        optionsMenuPanel.setBorder(BorderFactory.createEmptyBorder(windowHeight/4, windowHeight/4, windowHeight/4, windowHeight/4));


        optionsMenuPanel.add(titleLabel);

        optionsMenuPanel.add(themeSelectionLabel);
        optionsMenuPanel.add(classicThemeRadioBtn);
        classicThemeRadioBtn.setSelected(true);
        classicThemeRadioBtn.addActionListener(this);

        optionsMenuPanel.add(okabeItoThemeRadioBtn);
        okabeItoThemeRadioBtn.addActionListener(this);

        optionsMenuPanel.add(gameModeLabel);
        optionsMenuPanel.add(singlePlayerRadioBtn);
        singlePlayerRadioBtn.setSelected(true);
        singlePlayerRadioBtn.addActionListener(this);
        optionsMenuPanel.add(multiPlayerRadioBtn);
        multiPlayerRadioBtn.addActionListener(this);

        optionsMenuPanel.add(backButton);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        switch (e.getActionCommand()) {
            case "Classic":
                classicThemeRadioBtn.setSelected(true);
                okabeItoThemeRadioBtn.setSelected(false);
                colorTheme.toggle("Classic");
                break;
            case "Okabe Ito":
                classicThemeRadioBtn.setSelected(false);
                okabeItoThemeRadioBtn.setSelected(true);
                colorTheme.toggle("Okabe Ito");
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
            case "Options":
                frame.remove(titlePanel);
                frame.add(optionsMenuPanel);

                optionsMenuPanel.setVisible(true);
                titlePanel.setVisible(false);

                frame.revalidate();

                break;
            case "Back":
                frame.remove(optionsMenuPanel);
                frame.add(titlePanel);

                optionsMenuPanel.setVisible(false);
                titlePanel.setVisible(true);

                frame.revalidate();

                break;
            case "Play":
                frame.remove(optionsMenuPanel);
                frame.remove(titlePanel);

                optionsMenuPanel.setVisible(false);
                titlePanel.setVisible(false);

                frame.revalidate();

                startGame();
                frame.repaint();

                break;
            default:
                System.out.println("we are in default" + e.getActionCommand());
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
