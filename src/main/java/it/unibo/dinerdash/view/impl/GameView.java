package it.unibo.dinerdash.view.impl;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.utility.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

/*
 * Main Game View Panel
 * //TODO Clear method
 */
public class GameView extends GamePanel {

    private static final String SEP = System.getProperty("file.separator");
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "dinerdash" + SEP;

    private static final String TIME = "Time";
    private static final String COINS = "Coins";
    private static final String QUIT = "Quit";
    private static final String RESTART = "Restart";

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
    private Image backgroundImage;
    
    private LinkedList<GameEntityViewable> customers;
    private HashMap<TableViewable, Optional<GameEntityViewable>> tables;
    private LinkedList<GameEntityViewable> dishes;
    private GameEntityViewable waitress;
    private GameEntityViewable chef;

    public GameView(ViewImpl mainFrame) {
        super(mainFrame);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(0, 30));

        timeLabel = new JLabel(TIME + ": " + this.getMainFrame().getController().getRemainingTime());
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        coinLabel = new JLabel(COINS + ": " + this.getMainFrame().getController().getCoins());
        coinLabel.setFont(new Font("Arial", Font.BOLD, 20));
        coinLabel.setForeground(Color.WHITE);
        coinLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(coinLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(0, 30));

        exitButton = new JButton(QUIT);
        exitButton.addActionListener((e) -> this.getMainFrame().getController().quit());
        bottomPanel.add(exitButton, BorderLayout.EAST);
        
        restartButton = new JButton(RESTART);
        restartButton.addActionListener((e) -> this.getMainFrame().getController().restart());
        bottomPanel.add(restartButton);
        add(bottomPanel, BorderLayout.SOUTH);

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        var c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets.top = 6;
        c.insets.right = 8;

        powerupButton1 = new JButton("1");
        //TODO .addActionListener();  //aumenta velocità cameriera
        rightPanel.add(powerupButton1, c);

        c.gridy = 1;
        powerupButton2 = new JButton("2");
        //TODO .addActionListener(); aumenta velocità di preparazione dei piatti
        rightPanel.add(powerupButton2, c);

        c.gridy = 2;
        powerupButton3 = new JButton("3");
        //TODO .addActionListener(); aumenta il guadagno
        rightPanel.add(powerupButton3, c);

        c.gridy = 3;
        powerupButton4 = new JButton("4");
        //TODO .addActionListener(); aumenta la velocità di consumazione dei clienti
        rightPanel.add(powerupButton4, c);
        
        add(rightPanel, BorderLayout.EAST);

        this.start();
    }

    public void init() {        
        this.customers = new LinkedList<>();
        this.tables = new HashMap<>();
        this.dishes = new LinkedList<>();
    }

    private void start() {
        this.init();
        this.loadResources();
        this.getMainFrame().getController().start();
    }

    //TODO Imposta size delle entità
    private void loadResources() {        
        try {
            this.backgroundImage = loadIcon("background.jpg").getImage();

           // var customeImage = loadIcon("client1.png").getImage();  
            //customers.add(new GameEntityViewable(new Pair<Integer,Integer>(300, 200), customeImage));
            var waitressPosition = new Pair<>(40, 120);
            var waitressImage = loadIcon("waitress.png").getImage();
            this.waitress = new GameEntityViewable(waitressPosition, waitressImage);

            //TODO Add tavoli from controller->model

            var chefPosition = new Pair<>((int)(this.getMainFrame().getWidth() * 0.55), (int)(this.getMainFrame().getWidth() * 0.02));
            var chefImage = loadIcon("chef.png").getImage();
            this.chef = new GameEntityViewable(chefPosition, chefImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCustomerViewable(int num) {
        try {
            this.customers.getLast().setIcon(loadIcon("client"+num+".png").getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDish() {
        //TODO
    }

    // https://stackoverflow.com/questions/49871233/using-imageicon-to-access-a-picture-cant-access-it-how-to-fix
    private ImageIcon loadIcon(String iconName) throws IOException {
        final URL imgURL = ClassLoader.getSystemResource(ROOT + iconName);
        return new ImageIcon(imgURL);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.drawImage(backgroundImage, 0, 0, this.getMainFrame().getWidth(), this.getMainFrame().getHeight(), this);

        // Waitress
        g.drawImage(waitress.getIcon(), waitress.getPosition().getX(), waitress.getPosition().getY(), 120, 180, this);

        // Tables
        //this.tables.forEach(e ->
        //    g.drawImage(e.getIcon(), e.getPosition().getX(), e.getPosition().getY(), 120, 180, this)
        //);

        // Customers
        this.customers.forEach(c ->
            g.drawImage(c.getIcon(), c.getPosition().getX(), c.getPosition().getY(), (int)(this.getMainFrame().getWidth() * 0.13), 
            (int)(this.getMainFrame().getHeight() * 0.18), this)
        );

        // Chef
        g.drawImage(chef.getIcon(), chef.getPosition().getX(), chef.getPosition().getY(), 200, 200, this);
       
       // g.fillRect((int)(this.getMainFrame().getWidth()*0.04), (int)(this.getMainFrame().getHeight()*0.67), 100, 100);     primo posto in fila
       // addCustomer();
    }

    public LinkedList<GameEntityViewable> getViewableCustomersList() {
        return this.customers;
    }

}
