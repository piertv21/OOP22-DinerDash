package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;

/*
 * This class represents a game panel.
 */
public class GamePanel extends JPanel {

    private static final long serialVersionUID = -8037294251925043702L;

    private final View mainFrame;

    /**
     * Class constructor.
     * 
     * @param mainFrame is the reference to the program mother View.
     */
    public GamePanel(final View mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Return the main View.
     * 
     * @return the main View
     */
    public View getMainFrame() {
        return this.mainFrame;
    }

}
