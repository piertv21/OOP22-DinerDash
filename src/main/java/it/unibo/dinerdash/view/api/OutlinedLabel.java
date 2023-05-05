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

    private Color outlineColor;
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
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * Set the border color.
     * 
     * @param color is the border color
     */
    public void setOutlineColor(final Color color) {
        this.outlineColor = color;
    }

    /**
     * Set the border width.
     * 
     * @param width is the border width
     */
    public void setOutlineWidth(final int width) {
        this.outlineWidth = width;
    }

    /**
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
