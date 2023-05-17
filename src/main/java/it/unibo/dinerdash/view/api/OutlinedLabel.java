package it.unibo.dinerdash.view.api;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Simple class for representing Labels with a border.
 */
public class OutlinedLabel extends JLabel {

    private static final long serialVersionUID = -2717934030502230363L;

    private static final int BORDER_THICKNESS = 5;
    private static final int FONT_SIZE = 14;

    // Label Outline color
    private Color outlineColor;

    // Label Outline width
    private int outlineWidth;

    /**
     * Class constructor.
     * 
     * @param text is the base label text
     * @param outlineColor is the border color
     */
    public OutlinedLabel(final String text, final Color outlineColor) {
        super(text);
        this.outlineColor = outlineColor;
        this.outlineWidth = 2;
        setBorder(BorderFactory.createEmptyBorder(0, BORDER_THICKNESS, 0, BORDER_THICKNESS));
        setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
    }

    /**
     * {@inheritDoc}
     * 
     * Draw a normal label plus an outer border.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setColor(outlineColor);
        IntStream.rangeClosed(1, outlineWidth).forEach(i -> {
            g2.drawString(
                getText(), i + getInsets().left - outlineWidth,
                getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 1
            );
            g2.drawString(
                getText(), i + getInsets().left - outlineWidth,
                getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 + 1
            );
            g2.drawString(
                getText(), i + 1 + getInsets().left - outlineWidth,
                getHeight() / 2 + g2.getFontMetrics().getAscent() / 2
            );
            g2.drawString(
                getText(), i - 1 + getInsets().left - outlineWidth,
                getHeight() / 2 + g2.getFontMetrics().getAscent() / 2
            );
        });

        g2.setColor(getForeground());
        g2.drawString(getText(), getInsets().left - outlineWidth, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2);
    }

}
