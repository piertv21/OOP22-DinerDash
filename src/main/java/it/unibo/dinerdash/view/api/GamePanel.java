package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;

/*
 * Generic View panel
 */
public class GamePanel extends JPanel {

    private final View mainFrame;

    public GamePanel(final View mainFrame) {
        this.mainFrame = mainFrame;
    }

    public View getMainFrame() {
        return this.mainFrame;
    }

}
