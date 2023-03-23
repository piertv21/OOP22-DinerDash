package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;
import it.unibo.dinerdash.controller.impl.Controller;
import it.unibo.dinerdash.view.api.GenericPanel;
import it.unibo.dinerdash.view.api.GUI;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.StartView;

import java.awt.*;

/*
 * Main View.
 */
public class View extends JFrame implements GUI {

    private static final String FRAME_NAME = "Diner Dash";

    private Controller controller;
    private GenericPanel menuView;
    private GenericPanel gameView;

    public View(Controller controller) {
        super(FRAME_NAME);
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setLocationByPlatform(true);
        setResizable(true);
        setVisible(true);

        // TO DO usare this.pack(); -----
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screen.getWidth();
        final int height = (int) screen.getHeight();
        this.setSize(width / 2, height / 2);
        // ---

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

    public void closeWindow() {
        dispose();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

}
