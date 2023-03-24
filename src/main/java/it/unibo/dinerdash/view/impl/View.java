package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;
import it.unibo.dinerdash.controller.impl.Controller;
import it.unibo.dinerdash.view.api.FramePanel;
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
    private FramePanel menuView;
    private FramePanel gameView;

    public View(Controller controller) {
        super(FRAME_NAME);
        this.controller = controller;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.setLocationByPlatform(true);
        this.setResizable(true);        

        final var screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenDim.getWidth();
        final int height = (int) screenDim.getHeight();
        this.setSize(width / 2, height / 2);

        this.showMainMenu();
        this.setVisible(true);
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
        this.dispose();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

}
