package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.view.impl.GameView;

import java.awt.*;
import javax.swing.*;

/*
 * Starting Game Menu with 2 buttons
 */
public class StartView extends GenericPanel {

    public StartView(GameView mainFrame) {
        super(mainFrame);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 300));
        
        // Aggiunge un titolo al centro del pannello
        JLabel titleLabel = new JLabel("DinerDash");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(titleLabel, createGridBagConstraints(0, 0, 2, 1));
        
        // Aggiunge il bottone "Start Game"
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            // Azione del bottone "Start Game"
            System.out.println("Avvio del gioco...");
        });
        add(startButton, createGridBagConstraints(0, 1, 1, 1));
        
        // Aggiunge il bottone "Exit"
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            // Azione del bottone "Exit"
            System.exit(0);
        });
        add(exitButton, createGridBagConstraints(1, 1, 1, 1));

        setVisible(true);
    }

    private static GridBagConstraints createGridBagConstraints(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); //imposta i margini a 5 pixel su tutti i lati
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }
    
}
