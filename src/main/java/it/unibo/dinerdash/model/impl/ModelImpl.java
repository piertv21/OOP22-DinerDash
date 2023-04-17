package it.unibo.dinerdash.model.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
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

    private static final int RESTAURANT_WIDTH = 1280;
    private static final int RESTAURANT_HEIGHT = 720;

    private static final int MAX_CUSTOMERS_THAT_CAN_LEAVE = 10;
    private static final int MAX_CUSTOMERS_THAT_CAN_ENTER = 8;

    private static final int PROFIT_PER_TABLE_MIN = 80;
    private static final int PROFIT_PER_TABLE_MAX = 150;
    private static final double PROFIT_MULTIPLIER = 1.5;
    private static final int MAX_PLAYTIME = 5 * 60;

    private static final int TABLES = 4;
    private static final double TABLE_STARTING_REL_X = 0.35 * RESTAURANT_WIDTH;
    private static final double TABLE_STARTING_REL_Y = 0.40 * RESTAURANT_HEIGHT;
    private static final int TABLES_HORIZONTAL_PADDING = 400;
    private static final int TABLES_VERTICAL_PADDING = 200;
    private static final int TABLE_REL_WIDTH = 120;
    private static final int TABLE_REL_HEIGHT = 120;

    private static final int CUSTOMER_STARTING_X = 0;
    private static final int CUSTOMER_STARTING_Y = 500;
    private static final int CUSTOMER_REL_WIDTH = 150;
    private static final int CUSTOMER_REL_HEIGHT = 200;
    private static final int CUSTOMER_IN_LINE_PADDING = 100;
    private static final double CUSTOMER_FIRST_LINE_REL_X = 0.04 * RESTAURANT_WIDTH;
    private static final double CUSTOMER_FIRST_LINE_REL_Y = 0.67 * RESTAURANT_HEIGHT;
    private static final int CUSTOMERS_CREATION_TIME = 6;

    private static final int WAITRESS_STARTING_X = 40;
    private static final int WAITRESS_STARTING_Y = 120;
    private static final int WAITRESS_REL_WIDTH = 120;
    private static final int WAITRESS_REL_HEIGH = 180;
    private static final int WAITRESS_MAX_DISHES = 2;

    private static final double CHEF_REL_X = 0.55;
    private static final double CHEF_REL_Y = 0.02;
    private static final double CHEF_REL_WIDTH = 0.05;
    private static final double CHEF_REL_HEIGHT = 0.02;

    private static final double DISH_REL_WIDTH = 0.05;
    private static final double DISH_REL_HEIGHT = 0.02;
    private static final Pair<Integer, Integer> TABLES_SIZE = new Pair<Integer, Integer>(120, 180);

    private int coins;
    private int enabledCoinsMultipliers;
    private int remainingTime;
    private int customersWhoLeft;
    private GameState gameState;
    private Controller controller;
    private long lastCustomerTimeCreation;
    private boolean needUpdate;

    private LinkedList<Customer> customers;
    private LinkedList<Table> tables;
    private CountertopImpl counterTop;
    private ChefImpl chef;
    private Waitress waitress;

    public ModelImpl(Controller controller) {
        this.controller = controller;
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.counterTop = new CountertopImpl(this);
    }

    private void init() {
        this.coins = 0;
        this.enabledCoinsMultipliers = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
        this.needUpdate = true;
        this.clear();
        this.generateTables();

        var chefPosition = new Pair<>((int) (CHEF_REL_X * RESTAURANT_WIDTH), (int) (CHEF_REL_Y * RESTAURANT_HEIGHT));
        var chefSize = new Pair<>((int) (CHEF_REL_WIDTH * RESTAURANT_WIDTH),
                (int) (CHEF_REL_HEIGHT * RESTAURANT_HEIGHT));
        this.chef = new ChefImpl(chefPosition, chefSize, this);

        var waitressPosition = new Pair<Integer, Integer>(WAITRESS_STARTING_X, WAITRESS_STARTING_Y);
        var waitressSize = new Pair<Integer, Integer>(WAITRESS_REL_WIDTH, WAITRESS_REL_HEIGH);
        this.waitress = new WaitressImpl(waitressPosition, waitressSize, this);

        this.lastCustomerTimeCreation = System.nanoTime();
    }

    // Tavoli
    private void generateTables() {
        var tables = IntStream.range(0, TABLES / 2)
                .boxed()
                .flatMap(i -> IntStream.range(0, TABLES / 2)
                        .mapToObj(j -> {
                            int x = (int) (TABLE_STARTING_REL_X + j * TABLES_HORIZONTAL_PADDING);
                            int y = (int) (TABLE_STARTING_REL_Y + i * TABLES_VERTICAL_PADDING);
                            Pair<Integer, Integer> coordinates = new Pair<>(x, y);
                            Pair<Integer, Integer> size = new Pair<>(TABLE_REL_WIDTH, TABLE_REL_HEIGHT);
                            this.controller.addTable(coordinates, i + 1, TABLES_SIZE);
                            return new TableImpl(coordinates, size, i + 1);
                        }))
                .collect(Collectors.toList());
        this.tables.addAll(tables);
    }

    private void clear() {
        this.customers.clear();
        this.tables.clear();
        this.counterTop.clear();
    }

    @Override
    public int getWidth() {
        return RESTAURANT_WIDTH;
    }

    @Override
    public int getHeight() {
        return RESTAURANT_HEIGHT;
    }

    @Override
    public void start() {
        this.init();
        this.gameState = GameState.RUNNING;
        // TODO Check se serve altro
    }

    @Override
    public void pause() {
        this.gameState = GameState.PAUSED;
    }

    @Override
    public void stop() {
        this.gameState = GameState.ENDED;
    }

    @Override
    public boolean gameOver() {
        return this.remainingTime == 0 ||
                this.customersWhoLeft == MAX_CUSTOMERS_THAT_CAN_LEAVE;
    }

    @Override
    public void restart() {
        this.init();
        this.start();
    }

    @Override
    public void quit() {
        this.clear();
        // TODO Check se serve altro
    }

    @Override
    public void sendOrder(int tableNumber) { // mando l'ordine al bancone
        this.counterTop.addOrder(tableNumber);
    }

    @Override
    public void addCustomer() {
        if (this.gameOver()) {
            this.stop();
        }
        final var position = new Pair<>(CUSTOMER_STARTING_X, CUSTOMER_STARTING_Y);
        final int customersMolteplicity = (int) (Math.random() * (4)) + 1;
        this.customers.add(new CustomerImpl(position, new Pair<>(CUSTOMER_REL_WIDTH, CUSTOMER_REL_HEIGHT), this,
                customersMolteplicity));
        this.controller.addCustomer(customersMolteplicity, new Pair<>(CUSTOMER_REL_WIDTH, CUSTOMER_REL_HEIGHT));
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

    public void update(long elapsedUpdateTime) {
        if (!this.gameOver()) {
            if (System.nanoTime() >= this.lastCustomerTimeCreation + TimeUnit.SECONDS.toNanos(CUSTOMERS_CREATION_TIME)
                    && this.customers.size() < MAX_CUSTOMERS_THAT_CAN_ENTER) {
                this.addCustomer();
                this.lastCustomerTimeCreation = System.nanoTime();
            }
            this.chef.update();
            this.waitress.handleMovement(null); // TODO Mai usare null... a cosa serve?
            final var customIterator = this.customers.iterator();
            while (customIterator.hasNext()) {
                customIterator.next().update();
            }
            this.removeAngryCustomers();
        } else {
            this.stop();
        }
    }

    @Override
    public void removeAngryCustomers() {
        if (this.customers.stream().anyMatch(p -> p.getState().equals(CustomerState.ANGRY))) {
            final Customer tempCustomerToDelete = this.customers.stream()
                    .filter(p -> p.getState() // prendo il primo arrabbiato
                            .equals(CustomerState.ANGRY))
                    .findFirst()
                    .get();

            final int indexToDelete = this.customers.indexOf(tempCustomerToDelete); // prendo il suo indice
            this.customers.remove(tempCustomerToDelete);
            this.controller.removeCustomer(indexToDelete); // elimino il cliente dalle liste
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

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void decrementRemainingTime() {
        this.remainingTime--;
        this.controller.timeIsChanged();
    }

    public int getRemainingTime() {
        return this.remainingTime;
    }

    @Override
    public List<Customer> getCustomers() { // TODO Elimina
        return Collections.unmodifiableList(this.customers.stream().collect(Collectors.toList()));
    }

    @Override
    public void tableAssignament(final Customer cus) { // quando non ci sono più tavoli liberi non vengono piu
                                                           // assegnati tavoli nuovi
        cus.setDestination(Optional.ofNullable(
                this.tables.stream()
                        .filter(tav -> tav.getCustomer().isEmpty())
                        .findFirst()
                        .get()
                        .getPosition()));
        final var tab = tables.stream()
                .filter(entry -> entry.getPosition().equals(cus.getDestination().get()))
                .findFirst()
                .orElse(null);
        tab.setCustom(Optional.of(cus));
    }

    @Override
    public void linePositionAssignament(final Customer cus) {
        final int inLineCustm = (int) customers.stream().filter(p -> p.getState().equals(CustomerState.LINE)).count();
        if (inLineCustm != 1) {
            cus.setPosition(new Pair<Integer, Integer>((int) CUSTOMER_FIRST_LINE_REL_X,
                    (int) (CUSTOMER_FIRST_LINE_REL_Y - ((inLineCustm - 1) * CUSTOMER_IN_LINE_PADDING))));
        } else
            cus.setPosition(
                    new Pair<Integer, Integer>((int) CUSTOMER_FIRST_LINE_REL_X, (int) CUSTOMER_FIRST_LINE_REL_Y));
    }

    @Override
    public List<Table> getTable() { // TODO Elimina
        return Collections.unmodifiableList(this.tables.stream().collect(Collectors.toList()));
    }

    @Override
    public boolean thereAreAvaibleTables() {
        return this.tables.stream().anyMatch(tab -> tab.getCustomer().isEmpty());
    }

    public CountertopImpl getCounterTop() { // TODO Elimina
        return counterTop;
    }

    @Override
    public boolean checkFreeTables(final Customer cus) { // i clientei in fila controllano se si è liberato un
                                                             // tavolo
        if (this.customers.stream() // se questo cliente è il primo della fila
                .filter(p -> p.getState().equals(CustomerState.LINE)).findFirst().get().equals(cus)) {
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
    public void setWaitressTableDestination(Pair<Integer, Integer> dest) { // assegno la destinazione del tavo alla
                                                                           // cameriera
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
            this.tables.get(numberTable - 1).setSeatedPeople(0);
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
            this.controller.removeCustomer(indiceCustomerInList);
        }
    }

    @Override
    public void setTableCustomers(int customersMolteplicity, int numberTable) { // assegna il numero di persone sedute
                                                                                // al tavolo
        this.tables.get(numberTable - 1).setSeatedPeople(customersMolteplicity);
    }

    @Override
    public Waitress getWaitress() { // TODO Elimina
        return this.waitress;
    }

    public void setWaiterssInfo(int indexL, String s, Pair<Integer, Integer> pos) {
        if (s.equals("t")) {
            switch (this.tables.get(indexL).getState()) {
                case ORDERING:
                    this.waitress.takeTableOrder(tables.get(indexL).getPosition());
                    break;
                case WANTING_TO_PAY:
                    this.waitress.colletMoney(tables.get(indexL).getPosition());
                    break;
                default:
                    break;
            }
        } else {
            if (this.waitress.getOrdersNumber() != WAITRESS_MAX_DISHES) {
                this.waitress.addOrderForWaitress((this.counterTop.takeDish(pos.getX(), pos.getY())).get());
                this.waitress.goGetDish(waitress.getOrderList().getLast());
            }
        }
    }

    @Override
    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    @Override
    public boolean getNeedUpdate() {
        return this.needUpdate;
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
        return this.counterTop.thereAreAvailableDishes();
    }

    @Override
    public Optional<Dish> getDishToPrepare() {
        return this.counterTop.getDishInOrder();
    }

    @Override
    public void completeDishPreparation(Dish dish) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completeDishPreparation'");
    }

}
