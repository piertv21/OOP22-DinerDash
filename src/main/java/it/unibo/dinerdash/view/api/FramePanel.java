package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;

import it.unibo.dinerdash.view.impl.View;

/*
 * Generic View panel
 */
public class FramePanel extends JPanel {

    private final View mainFrame;

    public FramePanel(View mainFrame) {
        this.mainFrame = mainFrame;
    }

    public View getMainFrame() {
        return this.mainFrame;
    }

}
