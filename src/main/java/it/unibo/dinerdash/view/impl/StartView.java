package it.unibo.dinerdash.view.impl;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

/**
 * Represents the initial panel of the game
 * with the startup screen.
 */
public class StartView implements GamePanel<JPanel> {

    private final View mainFrame;
    private final JPanel panel;

    private static final String START = "Start game";
    private static final String EXIT = "Exit";

    private static final int FONT_SIZE = 60;
    private static final double FONT_SIZE_REL = 0.15;

    private final JLabel titleLabel;
    private final JButton startButton;
    private final JButton exitButton;

    /**
     * Class constructor.
     * 
     * @param mainFrame is the reference to main View
     */
    public StartView(final View mainFrame) {
        this.mainFrame = mainFrame;
        this.panel = new JPanel();

        this.panel.setLayout(new GridBagLayout());

        final var gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);

        titleLabel = new JLabel(Constants.GAME_NAME);
        titleLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        this.panel.add(titleLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        startButton = new JButton(START);
        startButton.addActionListener((e) -> mainFrame.showGameView());
        this.panel.add(startButton, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        exitButton = new JButton(EXIT);
        exitButton.addActionListener((e) -> mainFrame.showExitDialog());
        this.panel.add(exitButton, gridBagConstraints);

        this.panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int height = panel.getHeight();
                final int width = panel.getWidth();

                titleLabel.setFont(new Font("Arial", Font.BOLD, (int) (height * FONT_SIZE_REL)));

                final int buttonWidth = (int) (width * 0.3);
                final int buttonHeight = (int) (height * 0.08);

                startButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        });

        this.panel.setVisible(true);
    }

    @Override
    public View getUserInterface() {
        return this.mainFrame;
    }

    @Override
    public JPanel getComponent() {
        return this.panel;
    }

}
