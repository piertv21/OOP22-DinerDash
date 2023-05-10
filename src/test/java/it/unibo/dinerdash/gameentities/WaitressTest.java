package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.model.impl.WaitressImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * 
 */
final class WaitressTest {

    private Optional<Model> model;
    private WaitressImpl waitress;
    private static final int STARTING_WAITIMPL_SIZE = 50;
    private static final int STARTING_WAITIMPL_COORDINATES = 0;
    private static final int DISH_READY = 100;
    private static final int PAIR_POSITION = 200;

    /**
     * 
     */
    @BeforeEach
    private void setUp() {
        model = Optional.of(new ModelImpl());
        waitress = new WaitressImpl(
                new Pair<Integer, Integer>(STARTING_WAITIMPL_COORDINATES, STARTING_WAITIMPL_COORDINATES),
                new Pair<Integer, Integer>(STARTING_WAITIMPL_SIZE, STARTING_WAITIMPL_SIZE), model);
    }

    /**
     * 
     */
    @Test
    private void testInitialState() {
        assertEquals(WaitressState.WAITING, waitress.getState());
        assertTrue(waitress.getOrderList().isEmpty());
        assertEquals(2, waitress.getMovementSpeed());
    }

    /**
     * 
     */
    @Test
    private void testUpdate() {
        // TODO: Add test cases for the update method
    }

    /**
     * 
     */
    @Test
    private void testGoGetDish() {
        Pair<Integer, Integer> dishReady = new Pair<>(DISH_READY, DISH_READY);
        waitress.goGetDish(dishReady);
        assertEquals(dishReady, waitress.getDestination().get());
        assertEquals(WaitressState.TAKING_DISH, waitress.getState());
    }

    /**
     * 
     */
    @Test
    private void testTakeTableOrder() {
        Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.takeTableOrder(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.CALLING, waitress.getState());
    }

    /**
     * 
     */
    @Test
    private void testServeOrder() {
        Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.serveOrder(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.SERVING, waitress.getState());
    }

    /**
     * 
     */
    @Test
    private void testCollectMoney() {
        Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.collectMoney(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.TAKING_MONEY, waitress.getState());
    }

    /**
     * 
     */
    @Test
    private void testAddOrderForWaitress() {
        Dish dish = new DishImpl(null, null, 1);
        waitress.addOrderForWaitress(dish);
        assertFalse(waitress.getOrderList().isEmpty());
        assertEquals(1, waitress.getOrdersNumber());
        assertTrue(waitress.getOrderList().contains(dish));
    }

    /**
     * 
     */
    @Test
    private void testCheckRightTable() {
        Dish dish = new DishImpl(null, null, 1);
        waitress.addOrderForWaitress(dish);
        assertTrue(waitress.checkRightTable(1));
        assertFalse(waitress.checkRightTable(2));
    }

    /**
     * 
     */
    @Test
    private void testIncrementSpeed() {
        waitress.incrementSpeed();
        assertEquals(3, waitress.getMovementSpeed());
        waitress.incrementSpeed();
        assertEquals(4, waitress.getMovementSpeed());
    }

}
