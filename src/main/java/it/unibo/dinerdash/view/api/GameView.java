package it.unibo.dinerdash.view.api;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import it.unibo.dinerdash.view.impl.View;

/*
 * Main Game View Panel
 */
public class GameView extends GenericPanel {
    
    private static final String QUIT = "Quit";
    private static final String RESTART = "Restart";
    
    private ImageIcon backgroundImage;
    private JLabel timeLabel;
    private JLabel coinLabel;
    private JButton exitButton;
    private JButton restartButton;
    private JButton powerupButton1;
    private JButton powerupButton2;
    private JButton powerupButton3;
    private JButton powerupButton4;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private HashMap<Integer,Image> customerImagesMap;

    public GameView(View mainFrame) {
        super(mainFrame);

        //backgroundImage = new ImageIcon(this.getClass().getResource("resources/background.png"));

        setLayout(new BorderLayout());
        
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(0, 30));

        timeLabel = new JLabel("Time: 0");
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        coinLabel = new JLabel("Coins: 0");
        coinLabel.setForeground(Color.BLACK);
        coinLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(coinLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(0, 30));
        
        // TO DO ActionListener
        exitButton = new JButton("Exit");
        bottomPanel.add(exitButton, BorderLayout.EAST);
        
        // TO DO ActionListener
        restartButton = new JButton("Restart");
        bottomPanel.add(restartButton);
        add(bottomPanel, BorderLayout.SOUTH);

        rightPanel = new JPanel(new GridBagLayout());

        var c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets.top = 6;
        c.insets.right = 8;

        powerupButton1 = new JButton("1");
        rightPanel.add(powerupButton1, c);

        c.gridy = 1;
        powerupButton2 = new JButton("2");
        rightPanel.add(powerupButton2, c);

        c.gridy = 2;
        powerupButton3 = new JButton("3");
        rightPanel.add(powerupButton3, c);

        c.gridy = 3;
        powerupButton4 = new JButton("4");
        rightPanel.add(powerupButton4, c);
        
        add(rightPanel, BorderLayout.EAST);

        //add images in customersImage Hashmap
        /*try {
            this.customerImagesMap.put(1, ImageIO.read(new File("client1.png")));
            this.customerImagesMap.put(2, ImageIO.read(new File("client2.png")));
            this.customerImagesMap.put(3, ImageIO.read(new File("client3.png")));
            this.customerImagesMap.put(4, ImageIO.read(new File("client4.png")));
        } catch (IOException e1) {
            
            e1.printStackTrace();
        }*/        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //backgroundImage.paintIcon(this, g, 0, 0);
    }

    public Image getCustomerImage(int i) {
        return this.customerImagesMap.get(i);
    }
}
