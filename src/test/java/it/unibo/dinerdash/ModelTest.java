package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.impl.ModelImpl;

final class ModelTest {

    final int TABLENUMBER = 2; 
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
        assertEquals(10, model.getCustomerWhoCanLeft());
        model.addMaxCustomerThatCanLeave(2);
        model.addMaxCustomerThatCanLeave(7);
        assertEquals(19, model.getCustomerWhoCanLeft());

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
        assertEquals(720, model.getHeight());
    }

    @Test
    void testGetPowerUpsPrices() {

    }

    @Test
    void testGetRemainingTime() {

    }

    @Test
    void testGetTablefromPositon() {

    }

    @Test
    void testGetWidth() {
        assertEquals(1280, model.getWidth());
    }

    @Test
    void testIncreaseCoinsMultiplier() {

    }

    @Test
    void testIncreaseGainMultiplier() {

    }

    @Test
    void testIncreaseMaxCustomerThatCanLeave() {
        assertEquals(10, model.getCustomerWhoCanLeft());
        this.model.increaseMaxCustomerThatCanLeave();
        assertEquals(10, model.getCustomerWhoCanLeft());
        this.model.setCoins(600);
        this.model.increaseMaxCustomerThatCanLeave();
        assertEquals(12, model.getCustomerWhoCanLeft());

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
        assertEquals(0, this.model.getTableList().get(TABLENUMBER).getPeopleSeatedNumber());
        this.model.setNumberOfClientsAtTable(3, TABLENUMBER + 1 );
        assertEquals(3, this.model.getTableList().get(TABLENUMBER).getPeopleSeatedNumber());
    }

    @Test
    void testSetTableState() {
        assertEquals(TableState.EMPTY, this.model.getTableList().get(TABLENUMBER).getState());
        this.model.setTableState(TableState.ORDERING, TABLENUMBER + 1);
        assertEquals(TableState.ORDERING, this.model.getTableList().get(TABLENUMBER).getState());
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
