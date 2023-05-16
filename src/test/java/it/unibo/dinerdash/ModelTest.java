package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.CustomerState;
import it.unibo.dinerdash.model.api.states.GameState;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class ModelTest {

    private static final int TABLE_NUMBER = 2;
    private static final int CUSTOMER_INSIDE = 5;
    private Model model;
    private static GameEntityFactory factory;

    @BeforeAll
    static void init() {
        factory = new GameEntityFactoryImpl();
    }

    @BeforeEach
    void setUp() {
        this.model = new ModelImpl();
        this.model.start();
    }

    @Test
    void testAddMaxCustomerThatCanLeave() {
        final var customerThatCanLeave = 10;
        assertEquals(customerThatCanLeave, model.getCustomerWhoCanLeft());
        model.addMaxCustomerThatCanLeave(2);
        assertEquals(customerThatCanLeave + 2, model.getCustomerWhoCanLeft());
    }

    @Test
    void testCanActivatePowerUp() {
        final var coins = 150;
        assertFalse(model.canActivatePowerUp(100));
        model.setCoins(coins);
        assertTrue(model.canActivatePowerUp(100));
    }

    @Test
    void testCheckFreeTables() {
        while (this.model.thereAreAvaibleTables()) {
            this.model.update();
        }
        while (this.model.getCustomersList().size() < CUSTOMER_INSIDE) {
            this.model.update();
        }
       final Customer firstClientLine = this.model.getCustomersList().stream()
       .filter(p -> p.getState().equals(CustomerState.LINE))
       .findFirst()
       .get();
       assertFalse(this.model.checkFreeTables(firstClientLine));
    }

    @Test
    void testCustomerLeft() {
        assertEquals(0, model.getCustomersWhoLeft());
        model.customerLeft();
        assertEquals(1, model.getCustomersWhoLeft());
    }

    @Test
    void testDecrementRemainingTime() {
        final var initialTime = 300;
        assertEquals(initialTime, model.getRemainingTime());
        model.decrementRemainingTime();
        assertEquals(initialTime - 1, model.getRemainingTime());
        model.decrementRemainingTime();
        model.decrementRemainingTime();
        assertEquals(initialTime - 3, model.getRemainingTime());
    }

    @Test
    void testEarnMoneyFromTable() {
        assertEquals(0, model.getCoins());
        model.earnMoneyFromTable();
        assertNotEquals(0, model.getCoins());
    }

    @Test
    void testGameOver() {
        final var initialTime = 300;
        IntStream.range(0, 10)
            .forEach(i -> model.customerLeft());
        assertTrue(model.gameOver());

        setUp();

        IntStream.range(0, initialTime)
            .forEach(i -> model.decrementRemainingTime());
        assertTrue(model.gameOver());
    }

    @Test
    void testGetCoins() {
        assertEquals(0, model.getCoins());
    }

    @Test
    void testGetCustomerWhoCanLeft() {
        final int expectLeftCust = 19;
        final int addCustomerLeave = 7;
        assertEquals(10, model.getCustomerWhoCanLeft());
        model.addMaxCustomerThatCanLeave(2);
        model.addMaxCustomerThatCanLeave(addCustomerLeave);
        assertEquals(expectLeftCust, model.getCustomerWhoCanLeft());
    }

    @Test
    void testGetCustomersWhoLeft() {
        assertEquals(0, model.getCustomersWhoLeft());
        model.customerLeft();
        model.customerLeft();
        model.customerLeft();
        assertEquals(3, model.getCustomersWhoLeft());
    }

    @Test
    void testGetGameState() {
        assertEquals(GameState.RUNNING, model.getGameState());
        model.setGameState(GameState.ENDED);
        assertEquals(GameState.ENDED, model.getGameState());
    }

    @Test
    void testGetHeight() {
        final int expectedHeight = 720;
        assertEquals(expectedHeight, model.getHeight());
    }

    @Test
    void testGetPowerUpsPrices() {
        assertNotNull(model.getPowerUpsPrices());
    }

    @Test
    void testGetRemainingTime() {
        final var initialTime = 300;
        assertEquals(initialTime, model.getRemainingTime());
    }

    @Test
    void testGetTablefromPosition() {
        final Table checkTable = this.model.getTableList().get(0);
        final Table checkTable2 = this.model.getTableList().get(1);
        assertEquals(checkTable, this.model.getTablefromPosition(checkTable.getPosition()));
        assertEquals(checkTable2, this.model.getTablefromPosition(checkTable2.getPosition()));
        assertNotEquals(checkTable, this.model.getTablefromPosition(checkTable2.getPosition()));
    }

    @Test
    void testGetWidth() {
        final int expectedWidth = 1280;
        assertEquals(expectedWidth, model.getWidth());
    }

    @Test
    void testIncreaseGainMultiplier() {
        final var coins = 310;
        assertEquals(0, model.getEnableCoinMultiplier());
        model.setCoins(coins);
        model.increaseGainMultiplier();
        assertEquals(1, model.getEnableCoinMultiplier());
    }

    @Test
    void testIncreaseMaxCustomerThatCanLeave() {
        final int coin = 600;
        final int expectMaxCust = 12;
        assertEquals(10, model.getCustomerWhoCanLeft());
        this.model.increaseMaxCustomerThatCanLeave();
        assertEquals(10, model.getCustomerWhoCanLeft());
        this.model.setCoins(coin);
        this.model.increaseMaxCustomerThatCanLeave();
        assertEquals(expectMaxCust, model.getCustomerWhoCanLeft());
    }

    @Test
    void testIncreaseWaitressSpeed() {
        final var coins = 150;
        final var waitress = model.getWaitress();
        assertEquals(2, waitress.getMovementSpeed());
        model.setCoins(coins);
        model.increaseWaitressSpeed();
        assertNotEquals(2, waitress.getMovementSpeed());
    }

    @Test
    void testPause() {
        model.pause();
        assertEquals(GameState.PAUSED, model.getGameState());
    }

    @Test
    void testReduceDishPreparationTime() {
        final var chef = model.getChef();
        assertEquals(0, chef.getEnabledPowerUps());
        model.setCoins(100);
        model.reduceDishPreparationTime();
        assertEquals(1, chef.getEnabledPowerUps());
    }

    @Test
    void testSendOrder() {
        this.model.setTableState(TableState.THINKING, 1);
        this.model.sendOrder(1);
        assertEquals(TableState.WAITING_MEAL, this.model.getTableList().get(0).getState());
    }

    @Test
    void testSetCoins() {
        final var coins = 200;
        assertEquals(0, model.getCoins());
        model.setCoins(coins);
        assertEquals(coins, model.getCoins());
    }

    @Test
    void testSetGameState() {
        model.setGameState(GameState.ENDED);
        assertEquals(GameState.ENDED, model.getGameState());

        model.setGameState(GameState.PAUSED);
        assertEquals(GameState.PAUSED, model.getGameState());
    }

    @Test
    void testSetNumberOfClientsAtTable() {
        assertEquals(0, this.model.getTableList().get(TABLE_NUMBER).getPeopleSeatedNumber());
        this.model.setNumberOfClientsAtTable(3, TABLE_NUMBER + 1);
        assertEquals(3, this.model.getTableList().get(TABLE_NUMBER).getPeopleSeatedNumber());
    }

    @Test
    void testSetTableState() {
        assertEquals(TableState.EMPTY, this.model.getTableList().get(TABLE_NUMBER).getState());
        this.model.setTableState(TableState.ORDERING, TABLE_NUMBER + 1);
        assertEquals(TableState.ORDERING, this.model.getTableList().get(TABLE_NUMBER).getState());
    }

    @Test
    void testSetWaiterssInfo() {
        //TODO CORREGGI
        final Table table = factory.createTable(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                1);

        final Waitress waitress = factory.createWaitress(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                Optional.of(model));
        model.setWaiterssInfo(1, "table", new Pair<Integer, Integer>(100, 100));
        assertEquals(WaitressState.WAITING, waitress.getState());
        assertEquals(TableState.EMPTY, table.getState());
    }

    @Test
    void testSetWaitressTableDestination() {
        //TODO NO! La Waitress interna ha stato CALLING
        final Waitress waitress = factory.createWaitress(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                Optional.of(model));
        model.setWaitressTableDestination(new Pair<Integer, Integer>(100, 100));
        assertEquals(WaitressState.WAITING, waitress.getState());
    }

    @Test
    void testStart() {
        assertEquals(GameState.RUNNING, model.getGameState());
    }

    @Test
    void testStop() {
        model.stop();
        assertEquals(GameState.ENDED, model.getGameState());
    }

    @Test
    void testTableAssignament() {
        while (this.model.getCustomersList().size() < 1) {
            this.model.update();
        }
        final var customer = this.model.getCustomersList().get(0);
        final var tablePosition = customer.getDestination().get();
        final var assignedTable = this.model.getTableList().stream()
        .filter(table -> table.getPosition().equals(tablePosition))
        .findFirst()
        .get();
        assertEquals(assignedTable.getPosition(), customer.getDestination().get());
    }

    @Test
    void testTakeDishFromPosition() {
        final Table table = factory.createTable(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                1);
        assertEquals(Optional.empty(), this.model.takeDishFromPosition(table.getPosition()));
    }

    @Test
    void testThereAreAvaibleTables() {
        assertTrue(model.thereAreAvaibleTables());
    }

    @Test
    void testUpdate() {
        final var tablePosition = new Pair<>(50, 50);
        final var waitressPosition = model.getWaitress().getPosition();
        model.setWaitressTableDestination(tablePosition);
        model.update();
        assertNotEquals(waitressPosition, model.getWaitress().getPosition());
    }

}
