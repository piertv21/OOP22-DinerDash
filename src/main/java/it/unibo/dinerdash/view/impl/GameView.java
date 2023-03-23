package it.unibo.dinerdash.view.impl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import it.unibo.dinerdash.controller.impl.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameView {

    private static final String FRAME_NAME = "Diner Dash";
    private static final String QUIT = "Quit";
    private static final String RESTART = "Restart";
    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;

    private GameController controller;
    private JFrame frame;
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
        // Main Frame
        this.frame = new JFrame(FRAME_NAME);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.frame.setResizable(true);

        // Main Panel
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        var border = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        // Time Label
        this.timeLabel = new JLabel("Time: 0");
        this.timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.timeLabel.setVerticalAlignment(SwingConstants.TOP);
        this.timeLabel.setBorder(border);

        // PowerUp Buttons
        this.powerupButton1 = new JButton("1");
        this.powerupButton2 = new JButton("2");
        this.powerupButton3 = new JButton("3");
        this.powerupButton4 = new JButton("4");

        // PowerUp Panel
        this.powerUpsPanel = new JPanel();
        this.powerUpsPanel.setLayout(new BoxLayout(this.powerUpsPanel, BoxLayout.Y_AXIS));
        this.powerUpsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        this.powerUpsPanel.setBorder(border);
        this.powerUpsPanel.add(Box.createVerticalGlue());
        this.powerUpsPanel.add(this.powerupButton1);
        this.powerUpsPanel.add(this.powerupButton2);
        this.powerUpsPanel.add(this.powerupButton3);
        this.powerUpsPanel.add(this.powerupButton4);
        this.powerUpsPanel.add(Box.createVerticalGlue());        

        // Restart button
        this.restartButton = new JButton(RESTART);
        this.restartButton.addActionListener(e -> this.controller.restart());

        // Quit Button
        this.quitButton = new JButton(QUIT);
        this.quitButton.addActionListener(e -> this.controller.quit());

        // Buttons Panel
        var border2 = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        this.buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.buttonsPanel.setBorder(border2);
        this.buttonsPanel.add(this.restartButton);
        this.buttonsPanel.add(this.quitButton);

        // Add Main Panel
        this.mainPanel.add(timeLabel, BorderLayout.NORTH);
        this.mainPanel.add(powerUpsPanel, BorderLayout.EAST);
        this.mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Background Image
        backgroundImage = new ImageIcon("resources/background.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);

        // Last settings
        this.frame.add(mainPanel);
        this.frame.pack();
        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(true);

        //add images in customersImage Hashmap
        try {
            this.customerImagesMap.put(1, ImageIO.read(new File("client1.png")));
            this.customerImagesMap.put(2, ImageIO.read(new File("client2.png")));
            this.customerImagesMap.put(3, ImageIO.read(new File("client3.png")));
            this.customerImagesMap.put(4, ImageIO.read(new File("client4.png")));
        } catch (IOException e1) {
            
            e1.printStackTrace();
        }
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void draw() {
        
    }

    public void closeWindow() {
        this.frame.dispose();
    }   
    
    public Image getCustomerImage(int i) {
        return this.customerImagesMap.get(i);
    }

}
