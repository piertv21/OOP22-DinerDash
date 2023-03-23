package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;

import it.unibo.dinerdash.view.impl.View;

/*
 * Generic View panel
 */
public class GenericPanel extends JPanel {

    private final View mainFrame;

    public GenericPanel(View mainFrame) {
        this.mainFrame = mainFrame;
    }

    protected View getUserInterface() {        
        return this.mainFrame;
    }

}
