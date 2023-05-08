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

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

/**
 * Represents the final panel of the program
 * with the Game Over screen.
 */
public class GameOverView extends GamePanel {

    private static final long serialVersionUID = -6526284470262030339L;

    private static final int BIG_FONT_SIZE = 48;
    private static final double BIG_FONT_SIZE_REL = 0.15;
    private static final int MEDIUM_FONT_SIZE = BIG_FONT_SIZE / 2;
    private static final double MEDIUM_FONT_SIZE_REL = BIG_FONT_SIZE_REL / 2;

    private final JButton playAgainButton;
    private final JButton exitButton;
    private final JLabel gameOverLabel;
    private final JLabel coinLabel;

    /**
     * Class constructor.
     * 
     * @param mainFrame is the reference to main View
     */
    public GameOverView(final View mainFrame) {
        super(mainFrame);

        setLayout(new GridBagLayout());

        final var gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);

        gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, BIG_FONT_SIZE));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(gameOverLabel, gridBagConstraints);

        final var controller = this.getMainFrame().getController();
        gridBagConstraints.gridy = 1;
        coinLabel = new JLabel("Total coins: " + controller.getCoins());
        coinLabel.setFont(new Font(Constants.GAME_FONT, Font.PLAIN, MEDIUM_FONT_SIZE));
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(coinLabel, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        final JPanel buttonPanel = new JPanel();
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
            public void componentResized(final ComponentEvent e) {
                final int height = getHeight();
                final int width = getWidth();

                gameOverLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, (int) (height * BIG_FONT_SIZE_REL)));
                coinLabel.setFont(new Font(Constants.GAME_FONT, Font.PLAIN, (int) (height * MEDIUM_FONT_SIZE_REL)));

                final int buttonWidth = (int) (width * 0.6);
                final int buttonHeight = (int) (height * 0.2);

                playAgainButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        });
    }

}
