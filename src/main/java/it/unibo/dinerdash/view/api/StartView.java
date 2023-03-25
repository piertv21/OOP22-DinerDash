package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.view.impl.View;

import java.awt.*;
import javax.swing.*;

/*
 * Starting Game Menu with 2 buttons
 * TODO: Aggiungi eventuale immagine di sfondo
 */
public class StartView extends FramePanel {
    
    private static final String START = "Start game";
    private static final String EXIT = "Exit";

    private JLabel titleLabel;
    private JButton startButton;
    private JButton exitButton;

    public StartView(View mainFrame) {        
        super(mainFrame);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 10, 0);
        
        titleLabel = new JLabel(View.FRAME_NAME);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        add(titleLabel, c);

        c.gridy = 1;
        startButton = new JButton(START);
        startButton.addActionListener((e) -> this.getMainFrame().startGame());
        startButton.setPreferredSize(new Dimension(300, 40));
        add(startButton, c);

        c.gridy = 2;        
        exitButton = new JButton(EXIT);        
        exitButton.addActionListener((e) -> this.getMainFrame().getController().quit());
        exitButton.setPreferredSize(new Dimension(300, 40));
        add(exitButton, c);
    }

}
