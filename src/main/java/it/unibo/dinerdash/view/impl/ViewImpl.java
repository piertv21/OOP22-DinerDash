package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/*
 * Main View.
 */
public class ViewImpl extends JFrame implements View {

    public static final String FRAME_NAME = "Diner Dash";
    public static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = 600;

    private Controller controller;
    private GamePanel menuView;
    private GamePanel gameView;

    private double widthRatio;
    private double heightRatio;

    public ViewImpl(Controller controller) {
        super(FRAME_NAME);
        this.controller = controller;
        this.menuView = null;
        this.gameView = null;
        
        this.widthRatio = 0;
        this.heightRatio = 0;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setResizable(true);
        
        this.setSize(1280, 720); //TODO DEBUG 720p
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                widthRatio = screenSize.getWidth() / controller.getRestaurantWidth();
                heightRatio = screenSize.getHeight() / controller.getRestaurantHeight();     
            }
        });

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
    public void playAgain() {
        this.startGame();
    }

    public double getWidthRatio(){
        return this.widthRatio;
    }

    public double getHeightRatio() {
        return this.heightRatio;
    }

}
