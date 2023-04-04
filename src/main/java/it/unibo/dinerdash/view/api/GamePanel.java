package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;
import it.unibo.dinerdash.view.impl.ViewImpl;

/*
 * Generic View panel
 */
public class GamePanel extends JPanel {

    private final ViewImpl mainFrame;

    public GamePanel(ViewImpl mainFrame) {
        this.mainFrame = mainFrame;
    }

    public ViewImpl getMainFrame() {
        return this.mainFrame;
    }

}
