package it.unibo.dinerdash.view.impl;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;
import it.unibo.dinerdash.view.api.GameEntityViewableWithLabel;

/*
 * Main Game View Panel
 */
public class GameViewImpl extends GamePanel implements GameView {

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

    private Cursor defaultCursor;
    private Cursor handCursor;

    public GameViewImpl(View mainFrame) {
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

        this.init();
        this.loadResources();

        var point = new Point(0, 0);
        this.defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(this.imageCacher.getCachedImage("defaultCursor").getImage(), point, "Default Cursor");
        this.handCursor = Toolkit.getDefaultToolkit().createCustomCursor(this.imageCacher.getCachedImage("handCursor").getImage(), point, "Hand Cursor");

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                //TODO Qui vanno check mouse sopra uno dei tavoli + uno dei piatti

                //ESEMPIO
                if (inside(mouseX, mouseY, waitress)) {
                    // Il mouse è sopra la cameriera
                    setCursor(handCursor);
                } else {
                    // Il mouse non è sopra la cameriera
                    setCursor(defaultCursor);
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
                tables.stream().forEach(tav ->{
                    if(inside(mouseX, mouseY, tav))controller.callWaitress(tables.indexOf(tav),"t",null);
                });
                dishes.forEach(d->{
                    if(inside(mouseX,mouseY,d))controller.callWaitress(dishes.indexOf(d),"d", d.getPosition());
                });
            }
        });

        this.render();
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
            /*case 1 -> this.getMainFrame().getController().restart();
            case 2 -> this.getMainFrame().getController().quit();
            default -> {
                this.getMainFrame().getController().resume();
                JOptionPane.getRootFrame().dispose();
            }*/
        }
    }

    private void start() {
        this.assignStartingImages();
        this.getMainFrame().getController().start(this);
    }

    public void init() {        
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.dishes = new LinkedList<>();
        this.imageCacher = new ImageReaderWithCache(ROOT);
    }

    private List<String> searchAssets(String path) {
        return Optional.ofNullable(new File(path).listFiles())
                .map(Arrays::stream)
                .orElse(Stream.empty())
                .flatMap(file -> getFilesRecursively(file, path))
                .filter(file -> file.getName().toLowerCase().matches(".+\\.(jpg|jpeg|png|gif)$"))
                .map(File::getPath)
                .collect(Collectors.toList());
    }
    
    private Stream<File> getFilesRecursively(File file, String basePath) {
        String filePath = file.getPath();
        String relativePath = filePath.substring(basePath.length());
        return file.isDirectory() ? Optional.ofNullable(file.listFiles())
                .map(Arrays::stream)
                .orElse(Stream.empty())
                .flatMap(f -> getFilesRecursively(f, basePath))
                : Stream.of(new File(relativePath));
    }
    
    private void loadResources() {
        var path = "src" + SEP + "main" + SEP + "resources" + SEP + ROOT;
        var assetsPath = this.searchAssets(path);
        assetsPath.forEach(this.imageCacher::readImage);

        // Init Tables List
        IntStream.range(0, 4)
        .forEach(i->{
            this.tables.add(new GameEntityViewable(new Pair<Integer,Integer>(120, 180), new Pair<>(120,180), 
            this.imageCacher.getCachedImage("table0").getImage()));
        });
    }

    private void assignStartingImages() {
        this.backgroundImage = this.imageCacher.getCachedImage("background").getImage();

        //TODO è una prova, manca la posizione della waitress dal controller->model!
        var waitressPosition = new Pair<>(40, 120);
        this.waitress = new GameEntityViewable(waitressPosition, new Pair<>(120, 180), this.imageCacher.getCachedImage("waitress").getImage());
    }

    @Override
    public void assignNewImage(GameEntityViewableWithLabel gameEWWithLabel, int multiplicity) {
        //TODO Chiamare quando cambia multiplicity di una gameentityviewable
    }

    @Override
    public void clear() {
        this.customers.clear();
        this.tables.clear();
        this.dishes.clear();
    }

    @Override
    public void addCustomerViewable(int num, Pair<Integer,Integer> size) {              //aggiungo l'immagine ai clienti appena creati
        this.customers.add(new GameEntityViewable(null, size, null));
        this.customers.getLast().setIcon(this.imageCacher.getCachedImage("customer" + num).getImage());
    }

    @Override
    public void removeCustomerViewable(int indexVal) {              //rimuovo l'elemento customers[indexVal]
        this.customers.remove(indexVal);
    }

    public void addDish() {
        //TODO
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var widthRatio = this.getMainFrame().getWidthRatio();
        var heightRatio = this.getMainFrame().getHeightRatio();

        // Background
        g.drawImage(backgroundImage, 0, 0, this.getMainFrame().getWidth(), this.getMainFrame().getHeight(), this);

        // Waitress
        g.drawImage(waitress.getIcon(), (int)(waitress.getPosition().getX() * widthRatio), (int)(waitress.getPosition().getY() * heightRatio),
            (int)(waitress.getSize().getX() * widthRatio), (int)(waitress.getSize().getY() * heightRatio), this);

        // Tables
        this.tables.stream().filter(t -> t.getPosition() != null).forEach(e ->
            g.drawImage(e.getIcon(), (int)(e.getPosition().getX() * widthRatio), (int)(e.getPosition().getY() * heightRatio),
                (int)(e.getSize().getX() * widthRatio), (int)(e.getSize().getY() * heightRatio), this)
        );
        
        // Customers
        this.customers.stream().filter(cus->cus.isActive()).forEach(c ->
            g.drawImage(c.getIcon(), (int)(c.getPosition().getX() * widthRatio), (int) (c.getPosition().getY() * heightRatio),
                (int)(c.getSize().getX() * widthRatio), (int)(c.getSize().getY() * heightRatio), this)
        ); 

        // Chef
        // g.drawImage(chef.getIcon(), chef.getPosition().getX(), chef.getPosition().getY(), 200, 200, this);
       
        // g.fillRect((int)(this.getMainFrame().getWidth()*0.04), (int)(this.getMainFrame().getHeight()*0.67), 100, 100);     primo posto in fila
    }

    @Override
    public void render() {
        var controller = this.getMainFrame().getController();
        this.timeLabel.setText("Time: " + controller.convertToMinutesAndSeconds(controller.getRemainingTime()));
        this.repaint();
    }

    @Override
    public LinkedList<GameEntityViewable> getViewableCustomersList() {  //TODO Da rimuovere (usa add - update - remove)
        return this.customers;
    }

    @Override
    public LinkedList<GameEntityViewable> getViewableTable() { //TODO Da rimuovere (usa add - update - remove)
        return this.tables;
    }

    @Override
    public GameEntityViewable getViewableWaitress() {   //TODO Da rimuovere (usa add - update)
        return this.waitress;
    }

}
