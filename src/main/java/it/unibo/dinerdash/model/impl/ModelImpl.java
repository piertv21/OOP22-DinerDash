package it.unibo.dinerdash.model.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.model.api.Countertop;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.Chef;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.model.api.GameEntities.GameEntityFactory;
import it.unibo.dinerdash.model.api.GameEntities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.GameEntities.Waitress;
import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.model.api.States.GameState;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.model.api.States.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class ModelImpl implements Model {

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
    private static final int CHEF_REL_Y = (int) (0.087 * Constants.RESTAURANT_HEIGHT);
    private static final int CHEF_REL_WIDTH = (int) (0.12 * Constants.RESTAURANT_WIDTH);;
    private static final int CHEF_REL_HEIGHT = (int) (0.17 * Constants.RESTAURANT_HEIGHT);

    private static final int MAX_POWERUP_PER_TYPE = 3;
    private static final int[] POWER_UP_PRICES = { 100, 150, 220, 310 };

    private int coins;
    private int enabledCoinsMultipliers;
    private int remainingTime;
    private int customersWhoLeft;
    private int maxCustomerThatCanLeave;
    private GameState gameState;
    private Controller controller;
    private long lastCustomerTimeCreation;
    private GameEntityFactory factory;

    private LinkedList<Customer> customers;
    private LinkedList<Table> tables;
    private HashMap<Integer, Integer> powerUps;
    private Countertop counterTop;
    private Chef chef;
    private Waitress waitress;

    public ModelImpl(Controller controller) {
        this.controller = controller;
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.powerUps = new HashMap<>();
        this.counterTop = new CountertopImpl(this);
        this.factory = new GameEntityFactoryImpl();
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

        var chefPosition = new Pair<>(CHEF_REL_X, CHEF_REL_Y);
        var chefSize = new Pair<>(CHEF_REL_WIDTH, CHEF_REL_HEIGHT);
        this.chef = this.factory.createChef(chefPosition, chefSize, this);
        this.controller.addChefToView(this.chef);

        var waitressPosition = new Pair<Integer, Integer>(WAITRESS_STARTING_X, WAITRESS_STARTING_Y);
        var waitressSize = new Pair<Integer, Integer>(WAITRESS_REL_WIDTH, WAITRESS_REL_HEIGH);
        this.waitress = this.factory.createWaitress(waitressPosition, waitressSize, this);
        this.controller.addWaitressToView(waitress);
        this.lastCustomerTimeCreation = System.nanoTime();
    }

    // Tavoli
    private void generateTables() {
        var tables = IntStream.range(0, TABLES)
                .boxed()
                .map(i -> {
                    int x = (int) (TABLE_STARTING_REL_X + (i % (TABLES / 2)) * TABLES_HORIZONTAL_PADDING);
                    int y = (int) (TABLE_STARTING_REL_Y + (i / (TABLES / 2)) * TABLES_VERTICAL_PADDING);
                    Pair<Integer, Integer> coordinates = new Pair<>(x, y);
                    Pair<Integer, Integer> size = new Pair<>(TABLE_REL_WIDTH, TABLE_REL_HEIGHT);
                    var tempTable = this.factory.createTable(coordinates, size, i + 1);
                    this.controller.addTableToView(tempTable);
                    return tempTable;
                })
                .collect(Collectors.toList());
        this.tables.addAll(tables);
    }

    @Override
    public void clear() {
        this.customers.clear();
        this.tables.clear();
        this.powerUps.clear();
        this.counterTop.clear();
    }

    @Override
    public int getWidth() {
        return Constants.RESTAURANT_WIDTH;
    }

    @Override
    public int getHeight() {
        return Constants.RESTAURANT_HEIGHT;
    }

    @Override
    public void start() {
        this.init();
        this.gameState = GameState.RUNNING;
    }

    @Override
    public void pause() {
        this.gameState = GameState.PAUSED;
    }

    @Override
    public void stop() {
        this.gameState = GameState.ENDED;
        this.controller.gameIsEnded();
    }

    @Override
    public boolean gameOver() {
        return this.remainingTime <= 0 ||
                this.customersWhoLeft >= this.maxCustomerThatCanLeave;
    }

    @Override
    public void restart() {
        this.clear();
        this.start();
    }

    @Override
    public int getCustomersWhoLeft() {
        return this.customersWhoLeft;
    }

    @Override
    public int getCustomerWhoCanLeft() {
        return this.maxCustomerThatCanLeave;
    }

    @Override
    public void sendOrder(int tableNumber) {
        this.counterTop.addOrder(tableNumber);
        this.setTableState(TableState.WAITING_MEAL, tableNumber);
    }

    @Override
    public void addCustomer() {
        if (this.gameOver()) {
            this.stop();
        }
        final var position = new Pair<>(CUSTOMER_START_X, CUSTOMER_START_Y);
        final int customersMolteplicity = (int) (Math.random() * (MAX_CUSTOMERS_MULTIPLICITY)) + 1;
        var tempClient = this.factory.createCustomer(position, new Pair<>(CUSTOMER_REL_WIDTH, CUSTOMER_REL_HEIGHT),
                this, customersMolteplicity);
        this.customers.add(tempClient);

        this.controller.addCustomerToView(tempClient);
        if (thereAreAvaibleTables()) {
            tableAssignament(this.customers.getLast());
        } else {
            customers.getLast().setState(CustomerState.LINE);
            linePositionAssignament(this.customers.getLast());
        }
    }

    @Override
    public void customerLeft() {
        if (!this.gameOver()) {
            this.customersWhoLeft++;
        } else {
            this.stop();
        }
    }

    public void update() {
        if (!this.gameOver()) {
            // Aggiunta clienti
            if (System.nanoTime() >= this.lastCustomerTimeCreation
                    + TimeUnit.SECONDS.toNanos(CUSTOMERS_CREATION_TIME)
                    && this.customers.size() < MAX_CUSTOMERS_THAT_CAN_STAY) {
                this.addCustomer();
                this.lastCustomerTimeCreation = System.nanoTime();
            }

            // Update chef
            this.chef.update();
            this.controller.updateChefInView(this.chef);

            // Update Waitress
            this.waitress.update();
            this.controller.updateWaitressInView(this.waitress);

            // Update Customers
            this.customers.stream()
                    .filter(c -> !c.getState().equals(CustomerState.ORDERING))
                    .forEach(client -> {
                        client.update();
                        controller.updateCustomersInView(customers.indexOf(client), client);
                    });
            this.removeAngryCustomers();
            this.checkChangePositionLine();

            // Update tables
            this.tables.forEach(t -> {
                t.update();
                controller.updateTablesInView(tables.indexOf(t), t);
            });

            // Update PowerUp buttons state
            this.controller.updatePowerUpsButtonsInView();
        } else {
            this.stop();
        }
    }

    @Override
    public void removeAngryCustomers() {
        if (this.customers.stream().anyMatch(p -> p.getState().equals(CustomerState.ANGRY))) {
            final Customer tempCustomerToDelete = this.customers.stream()
                    .filter(p -> p.getState() // Get frist angry customer
                            .equals(CustomerState.ANGRY))
                    .findFirst()
                    .get();

            final int indexToDelete = this.customers.indexOf(tempCustomerToDelete); // get his index
            this.customers.remove(tempCustomerToDelete);
            this.controller.removeCustomerInView(indexToDelete); // delete client from lists
            this.customerLeft();
            this.customers.stream() // fix line positions
                    .filter(p -> p.getState()
                            .equals(CustomerState.LINE))
                    .forEach((p) -> {
                        p.setPosition(
                                new Pair<>(p.getPosition().getX(), p.getPosition().getY() + CUSTOMER_IN_LINE_PADDING));
                    });
        }
    }

    public void checkChangePositionLine() {
        if ((this.customers.stream().anyMatch(p -> p.getState().equals(CustomerState.LINE)))
                && (this.customers.stream().noneMatch(p -> p.getPosition().equals(new Pair<Integer, Integer>(
                        (int) CUSTOMER_FIRST_LINE_REL_X, (int) CUSTOMER_FIRST_LINE_REL_Y))))) {

            this.customers.stream() // fix line positions
                    .filter(p -> p.getState()
                            .equals(CustomerState.LINE))
                    .forEach((p) -> {
                        p.setPosition(
                                new Pair<>(p.getPosition().getX(), p.getPosition().getY() + CUSTOMER_IN_LINE_PADDING));
                    });

        }
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void decrementRemainingTime() {
        this.remainingTime--;
    }

    public int getRemainingTime() {
        return this.remainingTime;
    }

    @Override
    public void tableAssignament(final Customer client) { // assign a table to client
        client.setDestination(Optional.ofNullable(
                this.tables.stream()
                        .filter(tav -> tav.getCustomer().isEmpty())
                        .findFirst()
                        .get()
                        .getPosition()));
        final var tab = tables.stream()
                .filter(entry -> entry.getPosition().equals(client.getDestination().get()))
                .findFirst()
                .orElse(null);
        tab.setCustom(Optional.of(client));
    }

    @Override
    public void linePositionAssignament(final Customer client) {
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

    @Override
    public boolean thereAreAvaibleTables() {
        return this.tables.stream().anyMatch(tab -> tab.getCustomer().isEmpty());
    }

    @Override
    public boolean checkFreeTables(final Customer client) { // check for a free table
        if (this.customers.stream() // check if client is the first in line
                .filter(p -> p.getState().equals(CustomerState.LINE))
                .findFirst()
                .get()
                .equals(client)) {
            return this.thereAreAvaibleTables();
        }
        return false;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void setWaitressTableDestination(Pair<Integer, Integer> dest) {
        if (!(this.waitress.getState().equals(WaitressState.CALLING))) {
            this.waitress.setDestination(Optional.of(dest));
            this.waitress.setState(WaitressState.CALLING);
        }
    }

    @Override
    public Table getTablefromPositon(Pair<Integer, Integer> pos) { // ottengo il tavolo data la posizione
        return this.tables.stream().filter(t -> t.getPosition().equals(pos)).findFirst().get();
    }

    @Override
    public void setTableState(TableState state, int numberTable) { // pongo il tavolo in modalito ordering a gli assegno
                                                                   // il numero di clienti
        this.tables.get(numberTable - 1).setState(state);
        if (state.equals(TableState.EMPTY)) {
            this.tables.get(numberTable - 1).setseatedPeople(0);
            int indiceCustomerInList = this.customers.indexOf(tables.get(numberTable - 1).getCustomer().get()); // da
                                                                                                                // usare
                                                                                                                // per
                                                                                                                // cancellare
                                                                                                                // elem
                                                                                                                // in
                                                                                                                // lista
                                                                                                                // view
            this.customers.remove(this.tables.get(numberTable - 1).getCustomer().get());
            this.tables.get(numberTable - 1).setCustom(Optional.empty());
            this.controller.removeCustomerInView(indiceCustomerInList);
        }

        if (state.equals(TableState.EATING)) {
            this.tables.get(numberTable - 1).startEating();
        }
    }

    public void setWaiterssInfo(int indexL, String s, Pair<Integer, Integer> pos) {
        if (this.waitress.getState().equals(WaitressState.WAITING)) {
            if (s.equals("table")) {
                switch (this.tables.get(indexL).getState()) {
                    case ORDERING:
                        this.waitress.takeTableOrder(tables.get(indexL).getPosition());
                        break;
                    case WANTING_TO_PAY:
                        this.waitress.collectMoney(tables.get(indexL).getPosition());
                        break;
                    case WAITING_MEAL:
                        this.waitress.serveOrder(tables.get(indexL).getPosition());
                        break;
                    default:
                        break;
                }
            } else {
                if (this.waitress.getOrdersNumber() != WAITRESS_MAX_DISHES) {
                    this.waitress.goGetDish(pos);
                }
            }
        }

    }

    @Override
    public Optional<Dish> takeDishFromPosition(Pair<Integer, Integer> pos) {
        return this.counterTop.takeDish(pos);

    }

    @Override
    public void earnMoneyFromTable() { // Chiamato da waitress
        var coinsEarned = (int) (Math.random() * (PROFIT_PER_TABLE_MAX - PROFIT_PER_TABLE_MIN + 1))
                + PROFIT_PER_TABLE_MIN;
        var coinsEarnedWithBonus = (int) (coinsEarned
                + (coinsEarned * PROFIT_MULTIPLIER * this.enabledCoinsMultipliers));
        this.setCoins(this.coins + coinsEarnedWithBonus);
    }

    @Override
    public boolean thereAreDishesToPrepare() {
        return this.counterTop.thereAreDishesToPrepare();
    }

    @Override
    public Optional<Dish> getDishToPrepare() {
        return this.counterTop.getNextDishToPrepare();
    }

    @Override
    public void completeDishPreparation(Dish dish) {
        this.counterTop.setDishReady(dish);
    }

    @Override
    public void setNumberOfClientsAtTable(int numberOfClient, int numberOfTable) { // set sitted clients at table
        this.tables.get(numberOfTable - 1).setseatedPeople(numberOfClient);
    }

    @Override
    public void addDishToView(Dish dish) {
        this.controller.addDishToView(dish);
    }

    @Override
    public void removeDishInView(int dishIndex) {
        this.controller.deleteDishInView(dishIndex);
    }

    private boolean canAfford(int price) {
        return this.coins >= price;
    }

    private void handlePowerUpActivation(int cost) {
        this.setCoins(this.coins - cost);
        var remainingActivations = this.powerUps.get(cost);
        remainingActivations--;
        this.powerUps.put(cost, remainingActivations);
        this.controller.updatePowerUpsButtonsInView();
    }

    @Override
    public void reduceDishPreparationTime() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[0])) {
            this.chef.reducePreparationTime();
            this.handlePowerUpActivation(POWER_UP_PRICES[0]);
        }
    }

    @Override
    public void increaseWaitressSpeed() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[1])) {
            this.waitress.incrementSpeed();
            this.handlePowerUpActivation(POWER_UP_PRICES[1]);
        }
    }

    @Override
    public void increaseMaxCustomerThatCanLeave() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[2])) {
            this.addMaxCustomerThatCanLeave(ADDITIONAL_CUSTOMERS_POWERUP);
            this.handlePowerUpActivation(POWER_UP_PRICES[2]);
        }
    }

    @Override
    public void increaseGainMultiplier() {
        if (this.canActivatePowerUp(POWER_UP_PRICES[3])) {
            this.increaseCoinsMultiplier();
            this.handlePowerUpActivation(POWER_UP_PRICES[3]);
        }
    }

    @Override
    public int[] getPowerUpsPrices() {
        return POWER_UP_PRICES;
    }

    @Override
    public void addMaxCustomerThatCanLeave(int number) {
        this.maxCustomerThatCanLeave = this.maxCustomerThatCanLeave + number;
    }

    @Override
    public void increaseCoinsMultiplier() {
        this.enabledCoinsMultipliers++;
    }

    @Override
    public void updateDishInView(int index, Dish dish) {
        this.controller.updateDishesInView(index, dish);
    }

    private boolean isPowerUpAvailable(int price) {
        return this.powerUps.get(price) > 0;
    }

    @Override
    public boolean canActivatePowerUp(int price) {
        return this.canAfford(price) && this.isPowerUpAvailable(price);
    }

}
