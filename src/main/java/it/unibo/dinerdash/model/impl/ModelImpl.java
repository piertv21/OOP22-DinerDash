package it.unibo.dinerdash.model.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.model.api.Countertop;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Chef;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.CustomerState;
import it.unibo.dinerdash.model.api.states.GameState;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the Model interface.
 */
public final class ModelImpl implements Model {

    private static final int MAX_CUSTOMERS_THAT_CAN_LEAVE = 10;
    private static final int ADDITIONAL_CUSTOMERS_POWERUP = 2;
    private static final int MAX_CUSTOMERS_THAT_CAN_STAY = 8;
    private static final int MAX_CUSTOMERS_MULTIPLICITY = 4;
    private static final int PROFIT_PER_TABLE_MIN = 80;
    private static final int PROFIT_PER_TABLE_MAX = 150;
    private static final double PROFIT_MULTIPLIER = 1.5;
    private static final int MAX_PLAYTIME = 5 * 60;

    private static final int TABLES = 4;
    private static final int TABLE_STARTING_REL_X = (int) (0.35 * Constants.RESTAURANT_WIDTH);
    private static final int TABLE_STARTING_REL_Y = (int) (0.40 * Constants.RESTAURANT_HEIGHT);
    private static final int TABLES_HORIZONTAL_PADDING = (int) (0.31 * Constants.RESTAURANT_WIDTH);
    private static final int TABLES_VERTICAL_PADDING = (int) (0.27 * Constants.RESTAURANT_HEIGHT);
    private static final int TABLE_REL_WIDTH = (int) (0.12 * Constants.RESTAURANT_WIDTH);
    private static final int TABLE_REL_HEIGHT = (int) (0.21 * Constants.RESTAURANT_HEIGHT);

    private static final int CUSTOMER_REL_WIDTH = (int) (0.08 * Constants.RESTAURANT_WIDTH);
    private static final int CUSTOMER_REL_HEIGHT = (int) (0.21 * Constants.RESTAURANT_HEIGHT);
    private static final int CUSTOMER_IN_LINE_PADDING = (int) (0.14 * Constants.RESTAURANT_HEIGHT);
    private static final int CUSTOMER_FIRST_LINE_REL_X = (int) (0.04 * Constants.RESTAURANT_WIDTH);
    private static final int CUSTOMER_FIRST_LINE_REL_Y = (int) (0.67 * Constants.RESTAURANT_HEIGHT);
    private static final int CUSTOMERS_CREATION_TIME = 7;
    private static final int CUSTOMER_START_X = 0;
    private static final int CUSTOMER_START_Y = (int) (0.46 * Constants.RESTAURANT_HEIGHT);

    private static final int WAITRESS_STARTING_X = (int) (0.53 * Constants.RESTAURANT_WIDTH);
    private static final int WAITRESS_STARTING_Y = (int) (0.20 * Constants.RESTAURANT_HEIGHT);
    private static final int WAITRESS_REL_WIDTH = (int) (0.06 * Constants.RESTAURANT_WIDTH);
    private static final int WAITRESS_REL_HEIGH = (int) (0.21 * Constants.RESTAURANT_HEIGHT);
    private static final int WAITRESS_MAX_DISHES = 2;

    private static final int CHEF_REL_X = (int) (0.65 * Constants.RESTAURANT_WIDTH);
    private static final int CHEF_REL_Y = (int) (0.074 * Constants.RESTAURANT_HEIGHT);
    private static final int CHEF_REL_WIDTH = (int) (0.12 * Constants.RESTAURANT_WIDTH);
    private static final int CHEF_REL_HEIGHT = (int) (0.17 * Constants.RESTAURANT_HEIGHT);

    private static final int MAX_POWERUP_PER_TYPE = 3;
    private static final int[] POWER_UP_PRICES = { 100, 150, 220, 310 };

    private int coins;
    private int enabledCoinsMultipliers;
    private int remainingTime;
    private int customersWhoLeft;
    private int maxCustomerThatCanLeave;
    private GameState gameState;
    private Optional<Controller> controller;
    private long lastCustomerTimeCreation;
    private final GameEntityFactory factory;

    private final List<Customer> customers;
    private final List<Table> tables;
    private final Map<Integer, Integer> powerUps;
    private final Countertop counterTop;
    private Chef chef;
    private Waitress waitress;
    private final Random random;

    /**
     * Class constructor.
     */
    public ModelImpl() {
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.powerUps = new HashMap<>();
        this.counterTop = new CountertopImpl(Optional.of(this));
        this.factory = new GameEntityFactoryImpl();
        this.controller = Optional.empty();
        this.random = new Random();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final Controller controller) {
        this.controller = Optional.of(controller);
    }

    private void init() {
        this.coins = 0;
        this.enabledCoinsMultipliers = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
        this.maxCustomerThatCanLeave = MAX_CUSTOMERS_THAT_CAN_LEAVE;
        this.lastCustomerTimeCreation = 0;
        this.clear();
        this.generateTables();

        Arrays.stream(POWER_UP_PRICES)
            .forEach(price -> powerUps.put(price, MAX_POWERUP_PER_TYPE));

        final var chefPosition = new Pair<>(CHEF_REL_X, CHEF_REL_Y);
        final var chefSize = new Pair<>(CHEF_REL_WIDTH, CHEF_REL_HEIGHT);
        this.chef = this.factory.createChef(chefPosition, chefSize, Optional.of(this));
        this.controller.ifPresent(c -> c.addChefToView(this.chef));

        final var waitressPosition = new Pair<Integer, Integer>(WAITRESS_STARTING_X, WAITRESS_STARTING_Y);
        final var waitressSize = new Pair<Integer, Integer>(WAITRESS_REL_WIDTH, WAITRESS_REL_HEIGH);
        this.waitress = this.factory.createWaitress(waitressPosition, waitressSize, Optional.of(this));
        this.controller.ifPresent(c -> c.addWaitressToView(waitress));
        this.lastCustomerTimeCreation = System.nanoTime();
    }

    private void generateTables() {
        final var tables = IntStream.range(0, TABLES)
            .boxed()
            .map(i -> {
                final int remainder = i % (TABLES / 2);
                final int x = (int) (TABLE_STARTING_REL_X + remainder * TABLES_HORIZONTAL_PADDING);
                final int y = (int) (TABLE_STARTING_REL_Y + (i / (TABLES / 2)) * TABLES_VERTICAL_PADDING);
                final Pair<Integer, Integer> coordinates = new Pair<>(x, y);
                final Pair<Integer, Integer> size = new Pair<>(TABLE_REL_WIDTH, TABLE_REL_HEIGHT);
                final var tempTable = this.factory.createTable(coordinates, size, i + 1);
                this.controller.ifPresent(c -> c.addTableToView(tempTable));
                return tempTable;
            })
            .collect(Collectors.toList());
        this.tables.addAll(tables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.customers.clear();
        this.tables.clear();
        this.powerUps.clear();
        this.counterTop.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return Constants.RESTAURANT_WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return Constants.RESTAURANT_HEIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.init();
        this.gameState = GameState.RUNNING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.gameState = GameState.PAUSED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.gameState = GameState.ENDED;
        this.controller.ifPresent(c -> c.gameIsEnded());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean gameOver() {
        return this.remainingTime <= 0
            || this.customersWhoLeft >= this.maxCustomerThatCanLeave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restart() {
        this.clear();
        this.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCustomersWhoLeft() {
        return this.customersWhoLeft;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCustomerWhoCanLeft() {
        return this.maxCustomerThatCanLeave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendOrder(final int tableNumber) {
        final var result = this.counterTop.addOrder(tableNumber);
        if (result) {
            this.setTableState(TableState.WAITING_MEAL, tableNumber);
        } else {
            throw new IllegalStateException("Dish cannot be added.");
        }
    }

    private void addCustomer() {
        if (this.gameOver()) {
            this.stop();
        }
        final var position = new Pair<>(CUSTOMER_START_X, CUSTOMER_START_Y);
        final int customersMolteplicity = random.nextInt(MAX_CUSTOMERS_MULTIPLICITY) + 1;
        final var tempClient = this.factory.createCustomer(
                position,
                new Pair<>(CUSTOMER_REL_WIDTH, CUSTOMER_REL_HEIGHT),
                this,
                customersMolteplicity);
        this.customers.add(tempClient);
        this.controller.ifPresent(c -> c.addCustomerToView(tempClient));

        if (thereAreAvaibleTables()) {
            tableAssignament(tempClient);
        } else {
            tempClient.setState(CustomerState.LINE);
            linePositionAssignament(tempClient);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void customerLeft() {
        if (!this.gameOver()) {
            this.customersWhoLeft++;
        } else {
            this.stop();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (!this.gameOver()) {
            if (System.nanoTime() >= this.lastCustomerTimeCreation
                    + TimeUnit.SECONDS.toNanos(CUSTOMERS_CREATION_TIME)
                    && this.customers.size() < MAX_CUSTOMERS_THAT_CAN_STAY) {
                this.addCustomer();
                this.lastCustomerTimeCreation = System.nanoTime();
            }

            this.chef.update();
            this.controller.ifPresent(c -> c.updateChefInView(this.chef));

            this.waitress.update();
            this.controller.ifPresent(c -> c.updateWaitressInView(this.waitress));

            this.customers.stream()
                    .filter(c -> !c.getState().equals(CustomerState.ORDERING))
                    .forEach(client -> {
                        client.update();
                        this.controller.ifPresent(c -> c.updateCustomersInView(customers.indexOf(client), client));
                    });
            this.removeAngryCustomers();
            this.checkChangePositionLine();

            this.tables.forEach(t -> {
                t.update();
                this.controller.ifPresent(c -> c.updateTablesInView(tables.indexOf(t), t));
            });

            this.controller.ifPresent(c -> c.updatePowerUpsButtonsInView());
        } else {
            this.stop();
        }
    }

    private void removeAngryCustomers() {
        if (this.customers.stream().anyMatch(p -> p.getState().equals(CustomerState.ANGRY))) {
            final Customer tempCustomerToDelete = this.customers.stream()
                    .filter(p -> p.getState()
                            .equals(CustomerState.ANGRY))
                    .findFirst()
                    .get();

            final int indexToDelete = this.customers.indexOf(tempCustomerToDelete);
            this.customers.remove(tempCustomerToDelete);
            this.controller.ifPresent(c -> c.removeCustomerInView(indexToDelete));
            this.customerLeft();
            this.customers.stream()
                    .filter(p -> p.getState()
                            .equals(CustomerState.LINE))
                    .forEach((p) -> {
                        p.setPosition(
                                new Pair<>(p.getPosition().getX(), p.getPosition().getY() + CUSTOMER_IN_LINE_PADDING));
                    });
        }
    }

    private void checkChangePositionLine() {
        if (this.customers.stream().anyMatch(p -> p.getState().equals(CustomerState.LINE))
                && this.customers.stream().noneMatch(p -> p.getPosition().equals(new Pair<Integer, Integer>(
                        (int) CUSTOMER_FIRST_LINE_REL_X, (int) CUSTOMER_FIRST_LINE_REL_Y)))) {

            this.customers.stream()
                    .filter(p -> p.getState()
                            .equals(CustomerState.LINE))
                    .forEach((p) -> {
                        p.setPosition(
                                new Pair<>(p.getPosition().getX(), p.getPosition().getY() + CUSTOMER_IN_LINE_PADDING));
                    });

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoins() {
        return this.coins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementRemainingTime() {
        this.remainingTime--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRemainingTime() {
        return this.remainingTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tableAssignament(final Customer client) {
        client.setDestination(Optional.ofNullable(
                this.tables.stream()
                        .filter(table -> table.getCustomer().isEmpty())
                        .findFirst()
                        .get()
                        .getPosition()));
        final var selectedTable = tables.stream()
                .filter(entry -> entry.getPosition().equals(client.getDestination().get()))
                .findFirst()
                .orElse(null);
        selectedTable.setCustom(Optional.of(client));
    }

    private void linePositionAssignament(final Customer client) {
        final int inLineCustomers = (int) customers.stream().filter(p -> p.getState().equals(CustomerState.LINE))
                .count();
        if (inLineCustomers != 1) {
            client.setPosition(new Pair<Integer, Integer>((int) CUSTOMER_FIRST_LINE_REL_X,
                    (int) (CUSTOMER_FIRST_LINE_REL_Y - ((inLineCustomers - 1) * CUSTOMER_IN_LINE_PADDING))));
        } else {
            client.setPosition(new Pair<Integer, Integer>(
                    (int) CUSTOMER_FIRST_LINE_REL_X, (int) CUSTOMER_FIRST_LINE_REL_Y));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean thereAreAvaibleTables() {
        return this.tables.stream().anyMatch(tab -> tab.getCustomer().isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkFreeTables(final Customer client) {
        if (this.customers.stream()
                .filter(p -> p.getState().equals(CustomerState.LINE))
                .findFirst()
                .get()
                .equals(client)) {
            return this.thereAreAvaibleTables();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameState(final GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Table getTablefromPosition(final Pair<Integer, Integer> pos) {
        return this.tables.stream().filter(t -> t.getPosition().equals(pos)).findFirst().get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTableState(final TableState state, final int numberTable) {
        this.tables.get(numberTable - 1).setState(state);
        if (state.equals(TableState.EMPTY)) {
            this.tables.get(numberTable - 1).setSeatedPeople(0);
            final int customerListIndex = this.customers.indexOf(tables.get(numberTable - 1).getCustomer().get());
            this.customers.remove(this.tables.get(numberTable - 1).getCustomer().get());
            this.tables.get(numberTable - 1).setCustom(Optional.empty());
            this.controller.ifPresent(c -> c.removeCustomerInView(customerListIndex));
        }

        if (state.equals(TableState.EATING)) {
            this.tables.get(numberTable - 1).startEating();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWaiterssInfo(final int indexDishTable, final String entityType,
            final Pair<Integer, Integer> entityPos) {
        if (this.waitress.getState().equals(WaitressState.WAITING)) {
            if ("table".equals(entityType)) {
                switch (this.tables.get(indexDishTable).getState()) {
                    case ORDERING -> this.waitress.takeTableOrder(tables.get(indexDishTable).getPosition());
                    case WANTING_TO_PAY -> this.waitress.collectMoney(tables.get(indexDishTable).getPosition());
                    case WAITING_MEAL -> this.waitress.serveOrder(tables.get(indexDishTable).getPosition());
                    default -> { }
                }
            } else {
                if (this.waitress.getOrdersNumber() != WAITRESS_MAX_DISHES) {
                    this.waitress.goGetDish(entityPos);
                }
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dish> takeDishFromPosition(final Pair<Integer, Integer> pos) {
        return this.counterTop.takeDish(pos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void earnMoneyFromTable() {
        final var coinsEarned = random
            .nextInt(PROFIT_PER_TABLE_MAX - PROFIT_PER_TABLE_MIN + 1) + PROFIT_PER_TABLE_MIN;
        final var coinsEarnedWithBonus = (int) (coinsEarned
            + (coinsEarned * PROFIT_MULTIPLIER * this.enabledCoinsMultipliers));
        this.setCoins(this.coins + coinsEarnedWithBonus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean thereAreDishesToPrepare() {
        return this.counterTop.thereAreDishesToPrepare();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dish> getDishToPrepare() {
        return this.counterTop.getNextDishToPrepare();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completeDishPreparation(final int dishNumber) {
        this.counterTop.setDishReady(dishNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNumberOfClientsAtTable(final int numberOfClient, final int numberOfTable) {
        this.tables.get(numberOfTable - 1).setSeatedPeople(numberOfClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDishToView(final Dish dish) {
        this.controller.ifPresent(c -> c.addDishToView(dish));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDishInView(final int dishIndex) {
        this.controller.ifPresent(c -> c.deleteDishInView(dishIndex));
    }

    private boolean canAfford(final int price) {
        return this.coins >= price;
    }

    private void handlePowerUpActivation(final int cost) {
        this.setCoins(this.coins - cost);
        var remainingActivations = this.powerUps.get(cost);
        remainingActivations--;
        this.powerUps.put(cost, remainingActivations);
        this.controller.ifPresent(c -> c.updatePowerUpsButtonsInView());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reduceDishPreparationTime() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[0])) {
            this.chef.reducePreparationTime();
            this.handlePowerUpActivation(POWER_UP_PRICES[0]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseWaitressSpeed() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[1])) {
            this.waitress.incrementSpeed();
            this.handlePowerUpActivation(POWER_UP_PRICES[1]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseMaxCustomerThatCanLeave() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[2])) {
            this.addMaxCustomerThatCanLeave(ADDITIONAL_CUSTOMERS_POWERUP);
            this.handlePowerUpActivation(POWER_UP_PRICES[2]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseGainMultiplier() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[3])) {
            this.increaseCoinsMultiplier();
            this.handlePowerUpActivation(POWER_UP_PRICES[3]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getPowerUpsPrices() {
        return Arrays.copyOf(POWER_UP_PRICES, POWER_UP_PRICES.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMaxCustomerThatCanLeave(final int number) {
        this.maxCustomerThatCanLeave = this.maxCustomerThatCanLeave + number;
    }

    private void increaseCoinsMultiplier() {
        this.enabledCoinsMultipliers++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDishInView(final int index, final Dish dish) {
        this.controller.ifPresent(c -> c.updateDishesInView(index, dish));
    }

    private boolean isPowerUpAvailable(final int price) {
        return this.powerUps.get(price) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canActivatePowerUp(final int price) {
        return this.canAfford(price) && this.isPowerUpAvailable(price);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Table> getTableList() {
        return Collections.unmodifiableList(this.tables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getCustomersList() {
        return Collections.unmodifiableList(this.customers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEnableCoinMultiplier() {
        return this.enabledCoinsMultipliers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Reference to waitress it is necessary to run tests")
    public Waitress getWaitress() {
        return this.waitress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chef getChef() {
        return this.chef;
    }

}
