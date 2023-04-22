package it.unibo.dinerdash.view.impl;

import java.awt.*;
import javax.swing.*;

import it.unibo.dinerdash.view.api.GamePanel;

/*
 * Starting Game Menu with 2 buttons
 * TODO: Aggiungi eventuale immagine di sfondo
 */
public class StartView extends GamePanel {
    
    private static final String START = "Start game";
    private static final String EXIT = "Exit";

    private JLabel titleLabel;
    private JButton startButton;
    private JButton exitButton;

    public StartView(ViewImpl mainFrame) {        
        super(mainFrame);
        setLayout(new GridBagLayout());

        var gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        
        titleLabel = new JLabel(ViewImpl.FRAME_NAME);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        add(titleLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        startButton = new JButton(START);
        startButton.addActionListener((e) -> this.getMainFrame().showGameView());
        startButton.setPreferredSize(new Dimension(300, 40));
        add(startButton, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        exitButton = new JButton(EXIT);        
        exitButton.addActionListener((e) -> this.getMainFrame().getController().quitWithoutPlaying());
        exitButton.setPreferredSize(new Dimension(300, 40));
        add(exitButton, gridBagConstraints);
    }

}
