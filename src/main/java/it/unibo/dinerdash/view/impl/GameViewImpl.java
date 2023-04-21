package it.unibo.dinerdash.view.impl;

import java.awt.Image;
import java.awt.List;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
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
import it.unibo.dinerdash.view.api.GameEntityViewableImpl;
import it.unibo.dinerdash.view.api.NumberDecoratorImpl;
import it.unibo.dinerdash.view.api.ImageDecoratorImpl;

/*
 * Main Game View Panel
 */
public class GameViewImpl extends GamePanel implements GameView {

    private static int HEAD_PATTEN = -20;
    private static int MAX_PATIECE = 7;
    private static Pair<Integer, Integer> CLIENT_PATIENCE_IMG_SIZE = new Pair<Integer, Integer>(100, 30);

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

    private LinkedList<ImageDecoratorImpl> customers;
    private LinkedList<ImageDecoratorImpl> tables;
    private LinkedList<NumberDecoratorImpl> dishes;
    private NumberDecoratorImpl waitress;
    private GameEntityViewable chef;

    private Cursor defaultCursor;
    private Cursor handCursor;

    public GameViewImpl(View mainFrame) {
        super(mainFrame);

        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.WHITE);

        this.init();
        this.backgroundImage = this.imageCacher.getCachedImage("background").getImage();

        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(0, 30));

        var controller = this.getMainFrame().getController();
        timeLabel = new JLabel("Time: " + controller.convertToMinutesAndSeconds(controller.getRemainingTime()));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // TODO Add image coin
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
        // TODO .addActionListener(); //aumenta velocità cameriera
        rightPanel.add(powerupButton1, c);

        c.gridy = 1;
        powerupButton2 = new JButton("2");
        // TODO .addActionListener(); aumenta velocità di preparazione dei piatti
        rightPanel.add(powerupButton2, c);

        c.gridy = 2;
        powerupButton3 = new JButton("3");
        // TODO .addActionListener(); aumenta il guadagno
        rightPanel.add(powerupButton3, c);

        c.gridy = 3;
        powerupButton4 = new JButton("4");
        // TODO .addActionListener(); aumenta la velocità di consumazione dei clienti
        rightPanel.add(powerupButton4, c);

        add(rightPanel, BorderLayout.EAST);

        var point = new Point(0, 0);
        this.defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                this.imageCacher.getCachedImage("defaultCursor").getImage(), point, "Default Cursor");
        this.handCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(this.imageCacher.getCachedImage("handCursor").getImage(), point, "Hand Cursor");

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                setCursor(
                        tables.stream().anyMatch(table -> inside(mouseX, mouseY, table)) ||
                                dishes.stream().anyMatch(dish -> inside(mouseX, mouseY, dish)) ? handCursor
                                        : defaultCursor);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                tables.stream()
                        .filter(table -> inside(mouseX, mouseY, table))
                        .findFirst()
                        .ifPresent(table -> controller.callWaitress(tables.indexOf(table), "t", null));

                dishes.stream()
                        .filter(dish -> inside(mouseX, mouseY, dish))
                        .findFirst()
                        .ifPresent(dish -> controller.callWaitress(dishes.indexOf(dish), "d", dish.getPosition()));
            }
        });

        this.render();
        this.start();
    }

    private boolean inside(int mouseX, int mouseY, GameEntityViewable viewableEntity) {
        var widthRatio = this.getMainFrame().getWidthRatio();
        var heightRatio = this.getMainFrame().getHeightRatio();

        int viewableEntityWindowX = (int) (viewableEntity.getPosition().getX() * widthRatio);
        int viewableEntityWindowY = (int) (viewableEntity.getPosition().getY() * heightRatio);

        int entityWindowWidth = (int) (viewableEntity.getSize().getX() * widthRatio);
        int entityWindowHeight = (int) (viewableEntity.getSize().getY() * heightRatio);

        return (mouseX >= viewableEntityWindowX && mouseX <= viewableEntityWindowX + entityWindowWidth &&
                mouseY >= viewableEntityWindowY && mouseY <= viewableEntityWindowY + entityWindowHeight);
    }

    private void showPauseDialog() {
        JLabel messageLabel = new JLabel("GAME PAUSED");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.add(messageLabel);

        String[] options = { "Resume", "Restart", "Exit" };
        int result = JOptionPane.showOptionDialog(this, dialogPanel, "Pause", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

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
        this.getMainFrame().getController().start(this);
    }

    public void init() {
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.dishes = new LinkedList<>();
        this.imageCacher = this.getMainFrame().getImageCacher();
    }

    @Override
    public void clear() {
        this.customers.clear();
        this.tables.clear();
        this.dishes.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final var heightRatio = this.getMainFrame().getHeightRatio();
        final var widthRatio = this.getMainFrame().getWidthRatio();

        // Background
        g.drawImage(backgroundImage, 0, 0, this.getMainFrame().getWidth(), this.getMainFrame().getHeight(), this);
        Image exp = this.imageCacher.getCachedImage("heart6").getImage();

        // Waitress

        g.drawImage(waitress.getIcon(), (int) (waitress.getPosition().getX() *
                widthRatio),
                (int) (waitress.getPosition().getY() * heightRatio),
                (int) (waitress.getSize().getX() * widthRatio), (int) (waitress.getSize().getY() * heightRatio), this);

        // Tables

        this.tables.stream().filter(t -> t.getPosition() != null)
                .forEach(e -> {
                    g.drawImage(e.getIcon(), (int) (e.getPosition().getX() * widthRatio),
                            (int) (e.getPosition().getY() * heightRatio),
                            (int) (e.getSize().getX() * widthRatio), (int) (e.getSize().getY() *
                                    heightRatio),
                            this);

                    if (e.getState().isPresent()) {
                        g.drawImage(e.getState().get(), (int) (e.getPosition().getX() * widthRatio),
                                (int) (e.getPosition().getY() * heightRatio), 50, 30, this);
                    }
                });

        // Customers
        this.customers.stream().filter(cus -> cus.isActive()
                && ((NumberDecoratorImpl) cus.getDecorated()).getNumber() == MAX_PATIECE)
                .forEach(c -> { // stampo i clienti che vanno ai tavoli
                    g.drawImage(c.getIcon(), (int) (c.getPosition().getX() * widthRatio),
                            (int) (c.getPosition().getY() * heightRatio),
                            (int) (c.getSize().getX() * widthRatio),
                            (int) (c.getSize().getY() * heightRatio),
                            this);
                });

        var streamLineCustomer = this.customers.stream()
                .filter(cus -> cus.isActive()
                        && ((NumberDecoratorImpl) cus.getDecorated()).getNumber() != MAX_PATIECE);

        var list = streamLineCustomer.collect(Collectors.toList());
        Collections.reverse(list);
        list.forEach(c -> {
            g.drawImage(c.getIcon(), (int) (c.getPosition().getX() * widthRatio),
                    (int) (c.getPosition().getY() * heightRatio),
                    (int) (c.getSize().getX() * widthRatio), (int) (c.getSize().getY() *
                            heightRatio),
                    this);

            g.drawImage(exp,
                    (int) ((c.getPosition().getX() - HEAD_PATTEN) * widthRatio),
                    (int) ((c.getPosition().getY() + HEAD_PATTEN) * heightRatio),
                    (int) (CLIENT_PATIENCE_IMG_SIZE.getX() * widthRatio),
                    (int) (CLIENT_PATIENCE_IMG_SIZE.getY() * heightRatio), this);
        });

        // Chef
        /*
         * g.drawImage(chef.getIcon(), (int) (chef.getPosition().getX() * widthRatio),
         * (int) (chef.getPosition().getY() * heightRatio),
         * (int) (chef.getSize().getX() * widthRatio), (int) (chef.getSize().getY() *
         * heightRatio), this);
         */
    }

    @Override
    public void render() {
        var controller = this.getMainFrame().getController();
        this.timeLabel.setText("Time: " + controller.convertToMinutesAndSeconds(controller.getRemainingTime()));
        this.repaint();
    }

    // NUOVI METODI DA IMPLEMENTARE
    // ----------------------------------------------------------------

    @Override
    public void addCustomerViewable(
            Pair<Integer, Integer> coordinates,
            Pair<Integer, Integer> size,
            boolean active,
            int multiplicity,
            int patience) {
        var client = new ImageDecoratorImpl(
                new NumberDecoratorImpl(
                        new GameEntityViewableImpl(coordinates, size, active,
                                this.imageCacher.getCachedImage("customer" + multiplicity).getImage())));
        ((NumberDecoratorImpl) client.getDecorated()).setNumber(patience);
        this.customers.add(client);
    }

    @Override
    public void updateCustomersViewable(int index, Pair<Integer, Integer> coordinates, boolean active, int patience) {
        this.customers.get(index).update(coordinates, active);
        var client = ((NumberDecoratorImpl) this.customers.get(index).getDecorated());

        if (client.getNumber() != patience) {
            var img = this.imageCacher.getCachedImage("heart" + patience).getImage();
            this.customers.get(index).setState(Optional.of(img));
            client.setNumber(patience);
        }
    }

    @Override
    public void removeCustomerViewable(final int index) {
        this.customers.remove(index);
    }

    @Override
    public void addChefViewable(
            Pair<Integer, Integer> coordinates,
            Pair<Integer, Integer> size,
            boolean active) {
        this.chef = new GameEntityViewableImpl(
                coordinates, size, active, this.imageCacher.getCachedImage("chef").getImage());
    }

    @Override
    public void updateChefViewable(boolean active) {
        this.chef.update(this.chef.getPosition(), active);
    }

    @Override
    public void addWaitressViewable(
            Pair<Integer, Integer> coordinates,
            Pair<Integer, Integer> size,
            boolean active,
            int numDishes) {
        this.waitress = new NumberDecoratorImpl(
                new GameEntityViewableImpl(coordinates, size, active,
                        this.imageCacher.getCachedImage("waitress" + numDishes).getImage()));
        this.waitress.setNumber(numDishes);
    }

    @Override
    public void updateWaitressViewable(Pair<Integer, Integer> coordinates, int numDishes) {
        this.waitress.update(coordinates, this.waitress.isActive());

        if (this.waitress.getNumber() != numDishes) {
            this.waitress.setNumber(numDishes);
            var img = this.imageCacher.getCachedImage("waitress" + numDishes).getImage();
            this.waitress.setIcon(img);
        }
    }

    @Override
    public void addDishViewable(
            Pair<Integer, Integer> coordinates,
            Pair<Integer, Integer> size,
            boolean active,
            int numTable) {
        var img = this.imageCacher.getCachedImage("dish" + numTable).getImage();
        var dish = new NumberDecoratorImpl(
                new GameEntityViewableImpl(coordinates, size, active, img));
        dish.setNumber(numTable);
        this.dishes.add(dish);
    }

    @Override
    public void updateDishesViewable(int index, boolean active) {
        var dish = this.dishes.get(index);
        this.dishes.get(index).update(dish.getPosition(), active);
    }

    @Override
    public void deleteDishViewable(int index) {
        this.dishes.remove(index);
    }

    @Override
    public void addTableViewable(
            Pair<Integer, Integer> coordinates,
            Pair<Integer, Integer> size,
            int peopleNumer,
            String state) {
        var img = this.imageCacher.getCachedImage("table" + peopleNumer).getImage();
        var table = new ImageDecoratorImpl(new NumberDecoratorImpl(
                new GameEntityViewableImpl(coordinates, size, true, img)));
        ((NumberDecoratorImpl) table.getDecorated()).setNumber(peopleNumer);
        table.setState(Optional.empty());
        this.tables.add(table);
    }

    @Override
    public void updateTablesViewable(int index, int peopleNumber, String state) {
        var tempTable = (NumberDecoratorImpl) tables.get(index).getDecorated();
        if (tempTable.getNumber() != peopleNumber) {
            tempTable.setNumber(peopleNumber);
            Image img = this.imageCacher.getCachedImage("table" + peopleNumber).getImage();
            tempTable.setIcon(img);
        }
        if (!state.isBlank()) {
            Image imgState = this.imageCacher.getCachedImage(state).getImage();
            tables.get(index).setState(Optional.of(imgState));
        } else if (tables.get(index).getState().isPresent()) {
            tables.get(index).setState(Optional.empty());
        }
    }

}
