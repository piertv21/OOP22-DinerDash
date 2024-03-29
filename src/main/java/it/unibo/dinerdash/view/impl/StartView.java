package it.unibo.dinerdash.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

/**
 * Represents the initial panel of the game
 * with the startup screen.
 */
public class StartView implements GamePanel<JPanel> {

    private static final int FONT_SIZE = 60;
    private static final double FONT_SCALE_RATIO = 0.15;
    private static final double IMAGE_WIDTH = 0.16;
    private static final double IMAGE_HEIGHT = IMAGE_WIDTH / 2;
    private static final double IMAGE_REL_POS = 0.7;

    private final JPanel panel;
    private final JLabel titleLabel;
    private final JButton startButton;
    private final JButton exitButton;

    /**
     * Class constructor.
     * 
     * @param mainFrame is the reference to main View
     */
    public StartView(final View mainFrame) {
        this.panel = new JPanel() {
            @Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.decode("#FFEFDE"));
                g.fillRect(0, 0, getWidth(), getHeight());

                g.drawImage(mainFrame.getImageCacher().getCachedImage("startBacgkround").getImage(),
                    (int) (getWidth() * IMAGE_WIDTH), (int) (getHeight() * IMAGE_HEIGHT),
                    (int) (getWidth() * IMAGE_REL_POS), (int) (getHeight() * IMAGE_REL_POS),
                    this
                );
            }
        };

        this.panel.setLayout(new GridBagLayout());

        final var gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);

        titleLabel = new JLabel(Constants.GAME_NAME);
        titleLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        this.panel.add(titleLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        startButton = new JButton("Start game");
        startButton.addActionListener((e) -> mainFrame.showGameView());
        this.panel.add(startButton, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        exitButton = new JButton("Exit");
        exitButton.addActionListener((e) -> mainFrame.showExitDialog());
        this.panel.add(exitButton, gridBagConstraints);

        this.panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int height = panel.getHeight();
                final int width = panel.getWidth();

                titleLabel.setFont(new Font("Arial", Font.BOLD, (int) (height * FONT_SCALE_RATIO)));

                final int buttonWidth = (int) (width * 0.3);
                final int buttonHeight = (int) (height * 0.08);

                startButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        });
    }

    /**
     * Returns the component.
     * 
     * @return the Starting view JPanel
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component is used in a JFrame"
        + "therefore it is necessary to provide a reference to it")
    public JPanel getComponent() {
        return this.panel;
    }

}
