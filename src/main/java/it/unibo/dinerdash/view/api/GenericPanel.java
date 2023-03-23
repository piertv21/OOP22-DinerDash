package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;

import it.unibo.dinerdash.view.impl.GameView;

public class GenericPanel extends JPanel {

    private final GameView mainFrame;

    public GenericPanel(GameView mainFrame) {
        this.mainFrame = mainFrame;
    }

    protected GameView getUserInterface() {        
        return this.mainFrame;
    }

}
