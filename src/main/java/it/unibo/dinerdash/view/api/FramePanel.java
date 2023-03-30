package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;

import it.unibo.dinerdash.view.impl.ViewImpl;

/*
 * Generic View panel
 */
public class FramePanel extends JPanel {

    private final ViewImpl mainFrame;

    public FramePanel(ViewImpl mainFrame) {
        this.mainFrame = mainFrame;
    }

    public ViewImpl getMainFrame() {
        return this.mainFrame;
    }

}
