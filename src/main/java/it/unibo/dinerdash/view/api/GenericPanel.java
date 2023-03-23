package it.unibo.dinerdash.view.api;

import javax.swing.JPanel;

import it.unibo.dinerdash.controller.impl.Controller;
import it.unibo.dinerdash.view.impl.View;

/*
 * Generic View panel
 */
public class GenericPanel extends JPanel {

    private final View mainFrame;
    private Controller controller;

    public GenericPanel(View mainFrame) {
        this.mainFrame = mainFrame;
        this.setController(this.mainFrame.getController());
    }

    public View getMainFrame() {
        return this.mainFrame;
    }

    public Controller getController() {
        return this.controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
