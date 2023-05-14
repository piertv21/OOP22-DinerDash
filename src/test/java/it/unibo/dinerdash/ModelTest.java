package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class ModelTest {

    private static final int TABLE_NUMBER = 2;
    private Model model;
    private final GameEntityFactory factory = new GameEntityFactoryImpl();

    @BeforeEach
    void init() {
        this.model = new ModelImpl();
        this.model.start();
    }

    @Test
    void testAddDishToView() {

    }

    @Test
    void testAddMaxCustomerThatCanLeave() {

    }

    @Test
    void testCanActivatePowerUp() {

    }

    @Test
    void testCheckChangePositionLine() {

    }

    @Test
    void testCheckFreeTables() {

    }

    @Test
    void testClear() {

    }

    @Test
    void testCompleteDishPreparation() {

    }

    @Test
    void testCustomerLeft() {
        assertEquals(0, model.getCustomersWhoLeft());
        model.customerLeft();
        assertEquals(1, model.getCustomersWhoLeft());
    }

    @Test
    void testDecrementRemainingTime() {

    }

    @Test
    void testEarnMoneyFromTable() {

    }

    @Test
    void testGameOver() {

    }

    @Test
    void testGetCoins() {

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
    void testGetDishToPrepare() {

    }

    @Test
    void testGetGameState() {

    }

    @Test
    void testGetHeight() {
        final int exeptHeight = 720;
        assertEquals(exeptHeight, model.getHeight());
    }

    @Test
    void testGetPowerUpsPrices() {

    }

    @Test
    void testGetRemainingTime() {

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
        final int exeptWidth = 1280;
        assertEquals(exeptWidth, model.getWidth());
    }

    @Test
    void testIncreaseCoinsMultiplier() {

    }

    @Test
    void testIncreaseGainMultiplier() {

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

    }

    @Test
    void testPause() {

    }

    @Test
    void testReduceDishPreparationTime() {

    }

    @Test
    void testRemoveDishInView() {

    }

    @Test
    void testRestart() {

    }

    @Test
    void testSendOrder() {
        this.model.setTableState(TableState.THINKING, 1);
        this.model.sendOrder(1);
        assertEquals(TableState.WAITING_MEAL, this.model.getTableList().get(0).getState());

    }

    @Test
    void testSetCoins() {

    }

    @Test
    void testSetGameState() {

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
        final Table table = this.factory.createTable(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                1);

        final Waitress waitress = this.factory.createWaitress(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                Optional.of(model));
        model.setWaiterssInfo(1, "aaa", new Pair<Integer, Integer>(100, 100));
        assertEquals(WaitressState.WAITING, waitress.getState());
        assertEquals(TableState.EMPTY, table.getState());

    }

    @Test
    void testSetWaitressTableDestination() {
        final Waitress waitress = this.factory.createWaitress(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                Optional.of(model));
        // Add an order for table 1
        model.setWaitressTableDestination(new Pair<Integer, Integer>(100, 100));
        // Verify that the table state is set to WAITING_MEAL
        assertEquals(WaitressState.WAITING, waitress.getState());
    }

    @Test
    void testStart() {

    }

    @Test
    void testStop() {

    }

    @Test
    void testTableAssignament() {

    }

    @Test
    void testTakeDishFromPosition() {
        final Table table = this.factory.createTable(
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
    void testThereAreDishesToPrepare() {

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testUpdateDishInView() {

    }
}
