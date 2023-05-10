package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.model.impl.TableImpl;
import it.unibo.dinerdash.model.impl.WaitressImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * 
 */
final class WaitressTest {

    private static Optional<Model> model;
    private WaitressImpl waitress;
    private static final int STARTING_WAITIMPL_SIZE = 50;
    private static final int STARTING_WAITIMPL_COORDINATES = 0;
    private static final int DISH_READY = 100;
    private static final int PAIR_POSITION = 200;
    private final Pair<Integer, Integer> pair1 = new Pair<>(0, 0);
    private final Pair<Integer, Integer> pair2 = new Pair<>(0, 0);

    /**
     * 
     */
    @BeforeEach
    void setUp() {
        model = Optional.of(new ModelImpl());
        waitress = new WaitressImpl(
                new Pair<Integer, Integer>(STARTING_WAITIMPL_COORDINATES, STARTING_WAITIMPL_COORDINATES),
                new Pair<Integer, Integer>(STARTING_WAITIMPL_SIZE, STARTING_WAITIMPL_SIZE), model);
    }

    /**
     * 
     */
    @Test
    void testInitialState() {
        assertEquals(WaitressState.WAITING, waitress.getState());
        assertTrue(waitress.getOrderList().isEmpty());
        assertEquals(2, waitress.getMovementSpeed());
    }

    /**
     * 
     */
    @Test
    void testUpdate() {
        waitress.setState(WaitressState.CALLING);
        final Table table = new TableImpl(pair1, pair2, 1);
        waitress.setPosition(table.getPosition());

        while (!waitress.getPosition().equals(table.getPosition())) {
            waitress.update();
        }
        assertEquals(WaitressState.CALLING, waitress.getState());

    }

    /**
     * 
     */
    @Test
    void testGoGetDish() {
        final Pair<Integer, Integer> dishReady = new Pair<>(DISH_READY, DISH_READY);
        waitress.goGetDish(dishReady);
        assertEquals(dishReady, waitress.getDestination().get());
        assertEquals(WaitressState.TAKING_DISH, waitress.getState());
    }

    /**
     * 
     */
    @Test
    void testTakeTableOrder() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.takeTableOrder(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.CALLING, waitress.getState());
    }

    /**
     * 
     */
    @Test
    void testServeOrder() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.serveOrder(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.SERVING, waitress.getState());
    }

    /**
     * 
     */
    @Test
    void testCollectMoney() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.collectMoney(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.TAKING_MONEY, waitress.getState());
    }

    /**
     * 
     */
    @Test
    void testAddOrderForWaitress() {
        final Dish dish = new DishImpl(null, null, 1);
        waitress.addOrderForWaitress(dish);
        assertFalse(waitress.getOrderList().isEmpty());
        assertEquals(1, waitress.getOrdersNumber());
        assertTrue(waitress.getOrderList().contains(dish));
    }

    /**
     * 
     */
    @Test
    void testCheckRightTable() {
        final Dish dish = new DishImpl(null, null, 1);
        waitress.addOrderForWaitress(dish);
        assertTrue(waitress.checkRightTable(1));
        assertFalse(waitress.checkRightTable(2));
    }

    /**
     * 
     */
    @Test
    void testIncrementSpeed() {
        waitress.incrementSpeed();
        assertEquals(3, waitress.getMovementSpeed());
        waitress.incrementSpeed();
        assertEquals(4, waitress.getMovementSpeed());
    }

}
