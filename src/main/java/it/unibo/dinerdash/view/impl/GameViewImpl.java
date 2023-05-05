package it.unibo.dinerdash.view.impl;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.ImageDecorator;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;
import it.unibo.dinerdash.view.api.GameEntityViewableImpl;
import it.unibo.dinerdash.view.api.NumberDecoratorImpl;
import it.unibo.dinerdash.view.api.OutlinedLabel;
import it.unibo.dinerdash.view.api.ImageDecoratorImpl;
import it.unibo.dinerdash.view.api.NumberDecorator;

/*
 * Main Game View Panel
 */
public class GameViewImpl extends GamePanel implements GameView {

    private static final long serialVersionUID = -2203744497404204918L;

    private static final int CUSTOMER_PATIENCE_REL_POSITION = (int) (Constants.RESTAURANT_WIDTH * -0.01);
    private static final int TABLE_STATE_PATTER = (int) (Constants.RESTAURANT_WIDTH * 0.03);
    private static final int MAX_PATIECE = 7;
    private static final Pair<Integer, Integer> CUSTOMER_PATIENCE_IMG_SIZE = new Pair<>(100, 30);
    private static final Pair<Integer, Integer> TABLE_STATE_IMG_SIZE = new Pair<>(80, 56);
    
    private final JLabel timeLabel;
    private final JLabel customerWhoLeftLabel;
    private final JLabel coinLabel;    
    private List<JButton> powerupButtons;
    private final JPanel rightPanel;
    private final Image backgroundImage;
    private ImageReaderWithCache imageCacher;

    private List<ImageDecorator> customers;
    private List<ImageDecorator> tables;
    private List<NumberDecorator> dishes;
    private NumberDecorator waitress;
    private GameEntityViewable chef;

    private final Cursor defaultCursor;
    private final Cursor handCursor;

    public  GameViewImpl(final View mainFrame) {
        super(mainFrame);

        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.WHITE);

        this.init();
        this.backgroundImage = this.imageCacher.getCachedImage("background").getImage();

        final var topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(0, 60));

        final var controller = mainFrame.getController();
        timeLabel = new OutlinedLabel(
            "Time: " + controller.getRemainingTime(), Color.BLACK);
        timeLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, 25));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(25, 25, 0, 0));
        topPanel.add(timeLabel);

        customerWhoLeftLabel = new OutlinedLabel(
            "Customers who left: " + controller.getCustomersWhoLeft(), Color.BLACK);
        customerWhoLeftLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, 25));
        customerWhoLeftLabel.setForeground(Color.WHITE);
        customerWhoLeftLabel.setBorder(BorderFactory.createEmptyBorder(25, 10, 0, 0));
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(customerWhoLeftLabel);
        topPanel.add(Box.createHorizontalGlue());

        coinLabel = new OutlinedLabel(
            "Coins: " + controller.getCoins(), Color.BLACK);
        coinLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, 25));
        coinLabel.setForeground(Color.WHITE);
        coinLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 25));
        topPanel.add(coinLabel);
       
        add(topPanel, BorderLayout.NORTH);

        final var bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(0, 30));

        final var pauseButton = new JButton("Pause");
        pauseButton.addActionListener((e) -> {
            controller.pause();
            this.showPauseDialog();
        });
        bottomPanel.add(pauseButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        var c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets.top = 6;
        c.insets.right = 8;

        final var prices = controller.getPowerUpsPrices();
        IntStream.range(0, prices.length)
            .forEach(i -> {
                final JButton button = new JButton(String.valueOf(prices[i]),
                    this.imageCacher.getCachedImage("powerUp" + (i + 1)));
                button.setHorizontalTextPosition(JButton.CENTER);
                button.setVerticalTextPosition(JButton.BOTTOM);
                button.addActionListener(e -> controller.enablePowerUp(i));
                button.setEnabled(false);
                rightPanel.add(button, c);
                this.powerupButtons.add(button);
                c.gridy++;
            });

        add(rightPanel, BorderLayout.EAST);

        final var point = new Point(0, 0);
        this.defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                this.imageCacher.getCachedImage("defaultCursor").getImage(), point, "Default Cursor");
        this.handCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                this.imageCacher.getCachedImage("handCursor").getImage(), point, "Hand Cursor");

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                final int mouseX = e.getX();
                final int mouseY = e.getY();

                setCursor(
                    tables.stream().anyMatch(table -> inside(mouseX, mouseY, table)) ||
                    dishes.stream().anyMatch(dish -> inside(mouseX, mouseY, dish) && dish.isActive())
                    ? handCursor : defaultCursor
                );
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int mouseX = e.getX();
                final int mouseY = e.getY();

                tables.stream()
                        .filter(table -> inside(mouseX, mouseY, table))
                        .findFirst()
                        .ifPresent(table -> controller.callWaitress(tables.indexOf(table), "table", null));

                dishes.stream()
                        .filter(dish -> inside(mouseX, mouseY, dish) && dish.isActive())
                        .findFirst()
                        .ifPresent(dish -> controller.callWaitress(dishes.indexOf(dish), "dish", dish.getPosition()));
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int height = getHeight();

                timeLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, (int) (height * 0.04)));
                customerWhoLeftLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, (int) (height * 0.04)));
                coinLabel.setFont(new Font(Constants.GAME_FONT, Font.BOLD, (int) (height * 0.04)));
            }
        });

        mainFrame.setGameStarted(true);
        this.start();
    }

    private boolean inside(final int mouseX, final int mouseY, final GameEntityViewable viewableEntity) {
        final var widthRatio = this.getMainFrame().getWidthRatio();
        final var heightRatio = this.getMainFrame().getHeightRatio();

        final int viewableEntityWindowX = (int) (viewableEntity.getPosition().getX() * widthRatio);
        final int viewableEntityWindowY = (int) (viewableEntity.getPosition().getY() * heightRatio);

        final int entityWindowWidth = (int) (viewableEntity.getSize().getX() * widthRatio);
        final int entityWindowHeight = (int) (viewableEntity.getSize().getY() * heightRatio);

        return mouseX >= viewableEntityWindowX && mouseX <= viewableEntityWindowX + entityWindowWidth 
            && mouseY >= viewableEntityWindowY && mouseY <= viewableEntityWindowY + entityWindowHeight;
    }

    private void showPauseDialog() {
        final JLabel messageLabel = new JLabel("GAME PAUSED");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.add(messageLabel);

        final String[] options = {"Resume", "Restart", "Exit"};
        final int result = JOptionPane.showOptionDialog(this, dialogPanel, "Pause", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        final var controller = this.getMainFrame().getController();

        switch (result) {
            case 1 -> controller.restart();
            case 2 -> controller.quit();
            default -> {
                controller.resume();
                JOptionPane.getRootFrame().dispose();
            }
        }
    }

    private void start() {
        this.getMainFrame().getController().start(this);
    }

    private void init() {
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.dishes = new LinkedList<>();
        this.powerupButtons = new ArrayList<>();
        this.imageCacher = this.getMainFrame().getImageCacher();
    }

    @Override
    public void clear() {
        this.clearForRestarting();
        this.powerupButtons.clear();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final var heightRatio = this.getMainFrame().getHeightRatio();
        final var widthRatio = this.getMainFrame().getWidthRatio();
        g.drawImage(backgroundImage, 0, 0, this.getMainFrame().getWidth(), this.getMainFrame().getHeight(), this);
        g.drawImage(waitress.getIcon(),
                (int) (waitress.getPosition().getX() * widthRatio),
                (int) (waitress.getPosition().getY() * heightRatio),
                (int) (waitress.getSize().getX() * widthRatio),
                (int) (waitress.getSize().getY() * heightRatio),
                this);

        this.tables.stream().filter(t -> t.getPosition() != null)
                .forEach(e -> {
                    g.drawImage(e.getIcon(),
                        (int) (e.getPosition().getX() * widthRatio),
                        (int) (e.getPosition().getY() * heightRatio),
                        (int) (e.getSize().getX() * widthRatio),
                        (int) (e.getSize().getY() * heightRatio),
                    this);

                    if (e.getState().isPresent()) {
                        g.drawImage(e.getState().get(),
                                (int) ((e.getPosition().getX() + (TABLE_STATE_PATTER * widthRatio)) * widthRatio),
                                (int) (e.getPosition().getY() * heightRatio),
                                (int) (TABLE_STATE_IMG_SIZE.getX() * widthRatio),
                                (int) (TABLE_STATE_IMG_SIZE.getY() * heightRatio),
                                this);
                    }
                });
                
        this.customers.stream().filter(cus -> cus.isActive()
                && ((NumberDecorator) cus.getDecorated()).getNumber() == MAX_PATIECE)
                .forEach(c -> {
                    g.drawImage(c.getIcon(), (int) (c.getPosition().getX() * widthRatio),
                            (int) (c.getPosition().getY() * heightRatio),
                            (int) (c.getSize().getX() * widthRatio),
                            (int) (c.getSize().getY() * heightRatio),
                            this);
                });
                
        final var streamLineCustomer = this.customers.stream()
                .filter(cus -> cus.isActive()
                        && ((NumberDecorator) cus.getDecorated()).getNumber() != MAX_PATIECE);
        final var list = streamLineCustomer.collect(Collectors.toList());

        Collections.reverse(list);
        list.forEach(c -> {
            g.drawImage(c.getIcon(),
                    (int) (c.getPosition().getX() * widthRatio),
                    (int) (c.getPosition().getY() * heightRatio),
                    (int) (c.getSize().getX() * widthRatio),
                    (int) (c.getSize().getY() * heightRatio),
                    this);

            g.drawImage(c.getState().get(),
                    (int) ((c.getPosition().getX() - CUSTOMER_PATIENCE_REL_POSITION) * widthRatio),
                    (int) ((c.getPosition().getY() + CUSTOMER_PATIENCE_REL_POSITION) * heightRatio),
                    (int) (CUSTOMER_PATIENCE_IMG_SIZE.getX() * widthRatio),
                    (int) (CUSTOMER_PATIENCE_IMG_SIZE.getY() * heightRatio),
                    this);
        });
        
        if (this.chef.isActive()) {
            g.drawImage(chef.getIcon(),
                    (int) (chef.getPosition().getX() * widthRatio),
                    (int) (chef.getPosition().getY() * heightRatio),
                    (int) (chef.getSize().getX() * widthRatio),
                    (int) (chef.getSize().getY() * heightRatio),
            this);
        }

        this.dishes.stream().filter(dish -> dish.isActive()).forEach(dish ->
            g.drawImage(dish.getIcon(),
                    (int) (dish.getPosition().getX() * widthRatio),
                    (int) (dish.getPosition().getY() * heightRatio),
                    (int) (dish.getSize().getX() * widthRatio),
                    (int) (dish.getSize().getY() * heightRatio),
            this)
        );
    }

    @Override
    public void render() {
        final var controller = this.getMainFrame().getController();
        this.timeLabel.setText("Time: " + controller.getRemainingTime());
        this.customerWhoLeftLabel.setText("Customers who left: " + controller.getCustomersWhoLeft());
        this.coinLabel.setText("Coins: " + controller.getCoins());
        this.repaint();
    }

    private void resetPowerUpsButtons() {
        this.powerupButtons.forEach(e -> e.setEnabled(false));
    }

    private void clearForRestarting() {
        this.customers.clear();
        this.tables.clear();
        this.dishes.clear();
    }

    @Override
    public void restart() {
        this.clearForRestarting();
        this.resetPowerUpsButtons();
    }

    @Override
    public void addCustomerViewable(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final boolean active,
        final int multiplicity,
        final int patience
    ) {
        final var client = new ImageDecoratorImpl(
                new NumberDecoratorImpl(
                        new GameEntityViewableImpl(coordinates, size, active,
                                this.imageCacher.getCachedImage("customer" + multiplicity).getImage())));
        ((NumberDecorator) client.getDecorated()).setNumber(patience);
        this.customers.add(client);
    }

    @Override
    public void updateCustomersViewable(
        final int index,
        final Pair<Integer, Integer> coordinates,
        final boolean active,
        final int patience
    ) {
        this.customers.get(index).update(coordinates, active);
        final var client = (NumberDecorator) this.customers.get(index).getDecorated();

        if (client.getNumber() != patience && patience != -1) {
            final var img = this.imageCacher.getCachedImage("heart" + patience).getImage();
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
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final boolean active
    ) {
        this.chef = new GameEntityViewableImpl(
                coordinates, size, active, this.imageCacher.getCachedImage("chef").getImage());
    }

    @Override
    public void updateChefViewable(final boolean active) {
        this.chef.update(this.chef.getPosition(), active);
    }

    @Override
    public void addWaitressViewable(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final boolean active,
        final int numDishes
    ) {
        this.waitress = new NumberDecoratorImpl(
                new GameEntityViewableImpl(coordinates, size, active,
                        this.imageCacher.getCachedImage("waitress" + numDishes).getImage()));
        this.waitress.setNumber(numDishes);
    }

    @Override
    public void updateWaitressViewable(final Pair<Integer, Integer> coordinates, final int numDishes) {
        this.waitress.update(coordinates, this.waitress.isActive());

        if (this.waitress.getNumber() != numDishes) {
            this.waitress.setNumber(numDishes);
            final var img = this.imageCacher.getCachedImage("waitress" + numDishes).getImage();
            this.waitress.setIcon(img);
        }
    }

    @Override
    public void addDishViewable(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final boolean active,
        final int numTable
    ) {
        final var img = this.imageCacher.getCachedImage("dish" + numTable).getImage();
        final var dish = new NumberDecoratorImpl(
                new GameEntityViewableImpl(coordinates, size, active, img));
        dish.setNumber(numTable);
        this.dishes.add(dish);
    }

    @Override
    public void updateDishesViewable(final int index, final boolean active) {
        final var dish = this.dishes.get(index);
        this.dishes.get(index).update(dish.getPosition(), active);
    }

    @Override
    public void deleteDishViewable(final int index) {
        this.dishes.remove(index);
    }

    @Override
    public void addTableViewable(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final boolean active,
        final int peopleNumer,
        final String state
    ) {
        final var img = this.imageCacher.getCachedImage("table" + peopleNumer).getImage();
        final var table = new ImageDecoratorImpl(new NumberDecoratorImpl(
                new GameEntityViewableImpl(coordinates, size, active, img)));
        ((NumberDecorator) table.getDecorated()).setNumber(peopleNumer);
        table.setState(Optional.empty());
        this.tables.add(table);
    }

    @Override
    public void updateTablesViewable(final int index, final int peopleNumber, final String state) {
        final var tempTable = (NumberDecorator) tables.get(index).getDecorated();
        if (tempTable.getNumber() != peopleNumber) {
            tempTable.setNumber(peopleNumber);
            final var img = this.imageCacher.getCachedImage("table" + peopleNumber).getImage();
            tempTable.setIcon(img);
        }
        if (!state.isEmpty()) {
            if ("eating".equals(state)) {
                tempTable.setIcon(this.imageCacher.getCachedImage("tableWithDish" + peopleNumber).getImage());
            } else {
                final var imgState = this.imageCacher.getCachedImage(state).getImage();
                tables.get(index).setState(Optional.of(imgState));
            }
        } else if (tables.get(index).getState().isPresent()) {
            tables.get(index).setState(Optional.empty());
        }

    }

    @Override
    public void updatePowerUpButton(final int index, final boolean active) {
        this.powerupButtons.stream()
                .skip(index)
                .findFirst()
                .ifPresent(button -> button.setEnabled(button.isEnabled() != active ? active : button.isEnabled()));
    }

}
