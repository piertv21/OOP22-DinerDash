package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.impl.ModelImpl;

final class ModelTest {

    private static final int TABLE_NUMBER = 2;
    private Model model;

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
        Table checkTable =  this.model.getTableList().get(0);
        Table checkTable2 =  this.model.getTableList().get(1);
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

    }

    @Test
    void testSetWaitressTableDestination() {

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

    }

    @Test
    void testThereAreAvaibleTables() {

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
