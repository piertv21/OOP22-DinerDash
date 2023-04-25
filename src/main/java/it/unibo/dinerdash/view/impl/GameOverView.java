package it.unibo.dinerdash.view.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

public class GameOverView extends GamePanel {

    private JButton playAgainButton;
    private JButton exitButton;
    private JLabel gameOverLabel;
    private JLabel coinLabel;

    public GameOverView(View mainFrame) {
        super(mainFrame);

        setLayout(new GridBagLayout());

        var gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);

        gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(gameOverLabel, gridBagConstraints);

        var controller = this.getMainFrame().getController();
        gridBagConstraints.gridy = 1;
        coinLabel = new JLabel("Total coins: " + controller.getCoins());
        coinLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(coinLabel, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        playAgainButton = new JButton("Play again");
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(e -> {
            this.getMainFrame().playAgain();
        });
        buttonPanel.add(playAgainButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> {
            controller.quit();
        });
        buttonPanel.add(exitButton);

        add(buttonPanel, gridBagConstraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = getHeight();
                int width = getWidth();

                gameOverLabel.setFont(new Font("Arial", Font.BOLD, (int)(height * 0.15)));
                coinLabel.setFont(new Font("Arial", Font.PLAIN, (int)(height * 0.07)));

                int buttonWidth = (int) (width * 0.6);
                int buttonHeight = (int) (height * 0.2);

                playAgainButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        });
    }

}
