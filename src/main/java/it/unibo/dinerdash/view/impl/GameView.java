package it.unibo.dinerdash.view.impl;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

/*
 * Main Game View Panel
 */
public class GameView extends GamePanel {

    private static final String SEP = System.getProperty("file.separator");
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "dinerdash" + SEP;

    private JLabel timeLabel;
    private JLabel coinLabel;
    private JButton pauseButton;
    private JButton powerupButton1;
    private JButton powerupButton2;
    private JButton powerupButton3;
    private JButton powerupButton4;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private Image backgroundImage;
    private ImageReaderWithCache imageCacher;
    
    private LinkedList<GameEntityViewable> customers;
    private LinkedList<GameEntityViewable> tables;
    private LinkedList<GameEntityViewable> dishes;
    private GameEntityViewable waitress;
    private GameEntityViewable chef;

    public GameView(ViewImpl mainFrame) {
        super(mainFrame);

        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.WHITE);
        
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(0, 30));

        var controller = this.getMainFrame().getController();
        timeLabel = new JLabel("Time: " + controller.convertToMinutesAndSeconds(controller.getRemainingTime()));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        coinLabel = new JLabel("Coins: " + this.getMainFrame().getController().getCoins());
        coinLabel.setFont(new Font("Arial", Font.BOLD, 20));
        coinLabel.setForeground(Color.WHITE);
        coinLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(coinLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(0, 30));

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener((e) -> {
            this.getMainFrame().getController().pause();
            this.showPauseDialog();
        });
        bottomPanel.add(pauseButton, BorderLayout.EAST);

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

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                //TODO Qui vanno check mouse sopra uno dei tavoli + uno dei piatti

                //ESEMPIO
                if (inside(mouseX, mouseY, waitress)) {
                    // Il mouse è sopra la cameriera
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    // Il mouse non è sopra la cameriera
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                //TODO Qui vanno gli eventi mouse sopra uno dei tavoli + uno dei piatti

                //ESEMPIO
                if (inside(mouseX, mouseY, waitress)) {
                    // Il mouse è stato cliccato sulla cameriera
                    System.out.println("Coordinate del mouse: (" + mouseX + ", " + mouseY + ")");
                }
            }
        });

        this.start();
    }

    private boolean inside(int mouseX, int mouseY, GameEntityViewable viewableEntity) {
        var widthRatio = this.getMainFrame().getWidthRatio();
        var heightRatio = this.getMainFrame().getHeightRatio();

        // Coordinate x e y dell'entità
        int viewableEntityWindowX = (int) (viewableEntity.getPosition().getX() * widthRatio);
        int viewableEntityWindowY = (int) (viewableEntity.getPosition().getY() * heightRatio);
        
        // Dimensioni dell'entità
        int entityWindowWidth = (int) (viewableEntity.getSize().getX() * widthRatio);
        int entityWindowHeight = (int) (viewableEntity.getSize().getY() * heightRatio);
        
        // Verifica se le coordinate del mouse sono all'interno dell'entità
        return (mouseX >= viewableEntityWindowX && mouseX <= viewableEntityWindowX + entityWindowWidth &&
                mouseY >= viewableEntityWindowY && mouseY <= viewableEntityWindowY + entityWindowHeight);
    }    

    private void showPauseDialog() {
        JLabel messageLabel = new JLabel("GAME PAUSED");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);    
    
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.add(messageLabel);
    
        String[] options = {"Resume", "Restart", "Exit"};
        int result = JOptionPane.showOptionDialog(this, dialogPanel, "Pause", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    
       switch (result) {
            case 1 -> this.getMainFrame().getController().restart();
            case 2 -> this.getMainFrame().getController().quit();
            default -> {
                this.getMainFrame().getController().resume();
                JOptionPane.getRootFrame().dispose();
            }
        }
    }

    private void start() {
        this.init();
        this.loadResources();
        this.assignStartingImages();
        this.getMainFrame().getController().start(this);
    }

    public void init() {        
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.dishes = new LinkedList<>();
        this.imageCacher = new ImageReaderWithCache(ROOT);
    }
    
    private void loadResources() {
        // Load background
        this.imageCacher.readImage("background.jpg");

        // Cache chef
        this.imageCacher.readImage("chef.png");

        // Cache waitress
        this.imageCacher.readImage("waitress.png");

        // Cache customers
        IntStream.range(1, 5)
            .forEach(i -> this.imageCacher.readImage("customers" + SEP + "customer" + i + ".png"));

        // Cache tables
        IntStream.range(0, 5)
            .forEach(i -> this.imageCacher.readImage("tables" + SEP + "table" + i + ".png"));
    }

    private void assignStartingImages() {
        this.backgroundImage = this.imageCacher.getCachedImage("background").getImage();

        //TODO è una prova, manca la posizione della waitress dal controller->model!
        var waitressPosition = new Pair<>(40, 120);
        this.waitress = new GameEntityViewable(waitressPosition, new Pair<>(120, 180), this.imageCacher.getCachedImage("waitress").getImage());
    }

    private void clear() {
        this.customers.clear();
        this.tables.clear();
        this.dishes.clear();
    }
    
    //TODO da rivedere dopo model
    public void addCustomerViewable(int num) {              //aggiungo l'immagine ai clienti appena creati
        this.customers.getLast().setIcon(this.imageCacher.getCachedImage("customer" + num + ".png").getImage());
    }

    public void addDish() {
        //TODO
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //TODO Imposta size delle entità qui sotto

        // Background
        g.drawImage(backgroundImage, 0, 0, this.getMainFrame().getWidth(), this.getMainFrame().getHeight(), this);

        // Waitress
        g.drawImage(waitress.getIcon(), waitress.getPosition().getX(), waitress.getPosition().getY(), waitress.getSize().getX(), waitress.getSize().getY(), this);

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
        // g.drawImage(chef.getIcon(), chef.getPosition().getX(), chef.getPosition().getY(), 200, 200, this);
       
        // g.fillRect((int)(this.getMainFrame().getWidth()*0.04), (int)(this.getMainFrame().getHeight()*0.67), 100, 100);     primo posto in fila
    }

    public void render() {
        var controller = this.getMainFrame().getController();
        this.timeLabel.setText("Time: " + controller.convertToMinutesAndSeconds(controller.getRemainingTime()));
        this.repaint();
    }

    public LinkedList<GameEntityViewable> getViewableCustomersList() {
        return this.customers;
    }

    public LinkedList<GameEntityViewable> getViewableTable() {                
        return this.tables;
    }
}
