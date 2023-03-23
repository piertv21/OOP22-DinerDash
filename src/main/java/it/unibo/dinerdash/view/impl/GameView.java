package it.unibo.dinerdash.view.impl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import it.unibo.dinerdash.controller.impl.GameController;
import it.unibo.dinerdash.view.api.GenericPanel;
import it.unibo.dinerdash.view.api.StartView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameView extends JFrame { //TODO Aggiungi interfaccia

    private static final String FRAME_NAME = "Diner Dash";
    private static final String QUIT = "Quit";
    private static final String RESTART = "Restart";

    private GameController controller;
    private GenericPanel menuView;
    private GenericPanel gameView;

    private JPanel mainPanel;
    private JLabel timeLabel;
    private JPanel buttonsPanel;
    private JButton restartButton;
    private JButton quitButton;
    private JPanel powerUpsPanel;
    private JButton powerupButton1;
    private JButton powerupButton2;
    private JButton powerupButton3;
    private JButton powerupButton4;
    private HashMap<Integer,Image> customerImagesMap;

    private ImageIcon backgroundImage;

    public GameView() {
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

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void draw() {
        
    }

    public void closeWindow() {
        dispose();
    }   
    
    public Image getCustomerImage(int i) {
        return this.customerImagesMap.get(i);
    }

}
