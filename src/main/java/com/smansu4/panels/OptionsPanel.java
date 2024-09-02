package com.smansu4.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel implements ActionListener {
    private static final String panelLabel = "Snake Game";
    private static final String playBtnText = "Play";
    private static final String darkThemeRadioBtnText = "Dark";
    private static final String lightThemeRadioBtnText = "Light";
    private static final String singlePlayerRadioBtnTxt = "Single Player";
    private static final String multiPlayerRadioBtnTxt = "Multi Player";

    private JRadioButton darkThemeRadioBtn;
    private JRadioButton lightThemeRadioBtn;
    private JRadioButton singlePlayerRadioBtn;
    private JRadioButton multiPlayerRadioBtn;

    public OptionsPanel(int windowWidth, int windowHeight) {
        this.setSize(windowWidth, windowHeight);
        setUpOptionsMenu();
    }

    public void setUpOptionsMenu() {
        Insets inset_top_0 = new Insets(0, 0, 0, 0);
        Insets inset_top_20 = new Insets(20, 0, 0, 0);

        JLabel optionsLabel = new JLabel(panelLabel);
        optionsLabel.setFont(new Font("LucidaGrande", Font.PLAIN, 48));
        optionsLabel.setFont(optionsLabel.getFont().deriveFont(Font.BOLD));

        this.setLayout(new GridBagLayout());
        GridBagConstraints optionsPanelLayoutConstraint = new GridBagConstraints();

        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.fill = GridBagConstraints.HORIZONTAL;
        optionsPanelLayoutConstraint.gridx = 0;
        optionsPanelLayoutConstraint.gridy = 0;
        this.add(optionsLabel, optionsPanelLayoutConstraint);


        //Color Theme Layout
        JLabel themeSelectionLabel = new JLabel("Select a Color Theme: ");

        optionsPanelLayoutConstraint.insets = inset_top_20;
        optionsPanelLayoutConstraint.gridy = 1;
        this.add(themeSelectionLabel, optionsPanelLayoutConstraint);


        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.gridy = 2;

        darkThemeRadioBtn = new JRadioButton(darkThemeRadioBtnText);
        darkThemeRadioBtn.setSelected(true);
        darkThemeRadioBtn.addActionListener(this);
        this.add(darkThemeRadioBtn,optionsPanelLayoutConstraint);

        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.gridy = 3;

        lightThemeRadioBtn = new JRadioButton(lightThemeRadioBtnText);
        lightThemeRadioBtn.addActionListener(this);
        this.add(lightThemeRadioBtn, optionsPanelLayoutConstraint);


        //Game Mode Layout
        JLabel gameModeLabel = new JLabel("Select a Game Mode: ");

        optionsPanelLayoutConstraint.insets = inset_top_20;
        optionsPanelLayoutConstraint.gridy = 4;
        this.add(gameModeLabel, optionsPanelLayoutConstraint);


        optionsPanelLayoutConstraint.insets = inset_top_0;
        optionsPanelLayoutConstraint.gridy = 5;

        singlePlayerRadioBtn = new JRadioButton(singlePlayerRadioBtnTxt);
        singlePlayerRadioBtn.setSelected(true);
        singlePlayerRadioBtn.addActionListener(this);
        this.add(singlePlayerRadioBtn, optionsPanelLayoutConstraint);

        optionsPanelLayoutConstraint.gridy = 6;

        multiPlayerRadioBtn = new JRadioButton(multiPlayerRadioBtnTxt);
        multiPlayerRadioBtn.addActionListener(this);
        this.add(multiPlayerRadioBtn, optionsPanelLayoutConstraint);


        //Back Button Layout
        JButton playButton = new JButton(playBtnText);

        optionsPanelLayoutConstraint.insets = inset_top_20;
        optionsPanelLayoutConstraint.anchor = GridBagConstraints.PAGE_END; //bottom of space
        optionsPanelLayoutConstraint.gridx = 0;
        optionsPanelLayoutConstraint.gridy = 7;

        this.add(playButton, optionsPanelLayoutConstraint);
        playButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case darkThemeRadioBtnText:
                darkThemeRadioBtn.setSelected(true);
                lightThemeRadioBtn.setSelected(false);
                this.firePropertyChange(darkThemeRadioBtnText, false, true);
                break;
            case lightThemeRadioBtnText:
                darkThemeRadioBtn.setSelected(false);
                lightThemeRadioBtn.setSelected(true);
                this.firePropertyChange(lightThemeRadioBtnText, false, true);
                break;
            case singlePlayerRadioBtnTxt:
                singlePlayerRadioBtn.setSelected(true);
                multiPlayerRadioBtn.setSelected(false);
                this.firePropertyChange(singlePlayerRadioBtnTxt, false, true);
                break;
            case multiPlayerRadioBtnTxt:
                singlePlayerRadioBtn.setSelected(false);
                multiPlayerRadioBtn.setSelected(true);
                this.firePropertyChange(multiPlayerRadioBtnTxt, false, true);
                break;
            case playBtnText:
                this.setVisible(false);
                this.firePropertyChange(playBtnText, false, true);
                break;
            default:
                System.out.println("Unimplemented action selected: " + e.getActionCommand());
        }
    }
}
