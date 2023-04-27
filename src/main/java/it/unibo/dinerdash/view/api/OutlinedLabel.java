package it.unibo.dinerdash.view.api;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/*
 * Simple Class for drawing Outlined Labels.
 */
public class OutlinedLabel extends JLabel {

    private Color outlineColor;
    private int outlineWidth;

    public OutlinedLabel(String text, Color outlineColor) {
        super(text);
        this.outlineColor = outlineColor;
        this.outlineWidth = 2;
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        setFont(new Font("Arial", Font.BOLD, 14));
    }

    public void setOutlineColor(Color color) {
        this.outlineColor = color;
    }

    public void setOutlineWidth(int width) {
        this.outlineWidth = width;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g2.setColor(outlineColor);
        IntStream.rangeClosed(1, outlineWidth).forEach(i -> {
            g2.drawString(getText(), i + getInsets().left - outlineWidth, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 1);
            g2.drawString(getText(), i + getInsets().left - outlineWidth, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 + 1);
            g2.drawString(getText(), i + 1 + getInsets().left - outlineWidth, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2);
            g2.drawString(getText(), i - 1 + getInsets().left - outlineWidth, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2);
        });
        
        g2.setColor(getForeground());
        g2.drawString(getText(), getInsets().left - outlineWidth, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2);
    }
    


}
