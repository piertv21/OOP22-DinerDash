package it.unibo.dinerdash.view.impl;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

/*
 * Starting Game Menu with 2 buttons
 */
public class StartView extends GamePanel {
    
    private static final String START = "Start game";
    private static final String EXIT = "Exit";

    private JLabel titleLabel;
    private JButton startButton;
    private JButton exitButton;

    public StartView(View mainFrame) {        
        super(mainFrame);
        setLayout(new GridBagLayout());

        var gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        
        titleLabel = new JLabel(Constants.GAME_NAME);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        add(titleLabel, gridBagConstraints);
        
        gridBagConstraints.gridy = 1;
        startButton = new JButton(START);
        startButton.addActionListener((e) -> mainFrame.showGameView());
        add(startButton, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        exitButton = new JButton(EXIT);        
        exitButton.addActionListener((e) -> mainFrame.showExitDialog());
        add(exitButton, gridBagConstraints);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = getHeight();
                int width = getWidth();

                titleLabel.setFont(new Font("Arial", Font.BOLD, (int)(height * 0.15)));

                int buttonWidth = (int) (width * 0.3);
                int buttonHeight = (int) (height * 0.08);

                startButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        });
    }
}
