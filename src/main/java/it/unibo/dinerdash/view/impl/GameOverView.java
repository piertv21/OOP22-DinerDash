package it.unibo.dinerdash.view.impl;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

public class GameOverView extends GamePanel {

    private JButton rigiocaButton;
    private JButton esciButton;
    private JLabel gameoverLabel;
    private JLabel coinLabel;

    public GameOverView(View mainFrame) {
        super(mainFrame);

        setLayout(new GridBagLayout());

        var gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);

        gameoverLabel = new JLabel("Game Over");
        gameoverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameoverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(gameoverLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        coinLabel = new JLabel("Your coins: " + this.getMainFrame().getController().getCoins());
        coinLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(coinLabel, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        rigiocaButton = new JButton("Play again");
        rigiocaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rigiocaButton.addActionListener(e -> {
            this.getMainFrame().playAgain();
        });
        buttonPanel.add(rigiocaButton);
        buttonPanel.add(Box.createVerticalStrut(10)); //TODO Magic number

        esciButton = new JButton("Exit");
        esciButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        esciButton.addActionListener(e -> {
            this.getMainFrame().getController().quit();
        });
        buttonPanel.add(esciButton);

        add(buttonPanel, gridBagConstraints);
    }

}
