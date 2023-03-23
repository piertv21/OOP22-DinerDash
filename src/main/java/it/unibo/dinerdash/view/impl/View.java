package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;
import it.unibo.dinerdash.controller.impl.Controller;
import it.unibo.dinerdash.view.api.GenericPanel;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.StartView;

import java.awt.*;

/*
 * Main View.
 * TODO Crea interfaccia
 */
public class View extends JFrame {

    private static final String FRAME_NAME = "Diner Dash";

    private Controller controller;
    private GenericPanel menuView;
    private GenericPanel gameView;

    public View() {
        super(FRAME_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setLocationByPlatform(true);
        setResizable(true);
        setVisible(true);

        // TEMP -----
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screen.getWidth();
        final int height = (int) screen.getHeight();
        this.setSize(width / 2, height / 2);
        // --- usare this.pack();

        showMainMenu();
    }

    public void showMainMenu() {
        this.menuView = new StartView(this);
        this.gameView = null;
        
        this.setContentPane(menuView);
        this.validate();
        this.repaint();
    }

    public void startGame() {
        this.menuView = null;
        this.gameView = new GameView(this);
        
        this.setContentPane(gameView);
        this.validate();
        this.repaint();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void closeWindow() {
        dispose();
    }
    
    public void exit() {
        this.controller.quit();
    }

}
