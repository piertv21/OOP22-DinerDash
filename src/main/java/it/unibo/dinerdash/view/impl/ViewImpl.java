package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.view.api.View;

import java.awt.*;

/*
 * Main View.
 */
public class ViewImpl extends JFrame implements View {

    public static final String FRAME_NAME = "Diner Dash";

    private Controller controller;
    private StartView menuView;
    private GameView gameView;

    public ViewImpl(Controller controller) {
        super(FRAME_NAME);
        this.controller = controller;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setResizable(true);

        final var screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenDim.getWidth();
        final int height = (int) screenDim.getHeight();
        this.setSize((int)(width * 0.5), (int)(height * 0.75));

        this.showMainMenu();
        this.setVisible(true);
    }

    @Override
    public void showMainMenu() {
        this.menuView = new StartView(this);
        this.gameView = null;
        
        this.setContentPane(menuView);
        this.validate();
        this.repaint();
    }

    @Override
    public void startGame() {
        this.menuView = null;
        this.gameView = new GameView(this);
        
        this.setContentPane(gameView);
        this.validate();
        this.repaint();
    }

    @Override
    public void quit() {
        this.dispose();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public GameView getGamePanel() {
        return this.gameView;
    }

}
