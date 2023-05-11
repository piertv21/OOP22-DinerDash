package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.model.impl.TableImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * 
 */
final class WaitressTest {

    private static Optional<Model> model;
    private Waitress testWaitress1;
    private static final int STARTING_WAITIMPL_SIZE = 50;
    private static final int STARTING_WAITIMPL_COORDINATES = 0;
    private static final int DISH_READY = 100;
    private static final int PAIR_POSITION = 200;
    private final Pair<Integer, Integer> pair1 = new Pair<>(0, 0);
    private final Pair<Integer, Integer> pair2 = new Pair<>(0, 0);
    private final GameEntityFactory factory = new GameEntityFactoryImpl();

    /**
     * 
     */
    @BeforeEach
    void setUp() {
        model = Optional.of(new ModelImpl());
        testWaitress1 = this.factory.createWaitress(
                new Pair<Integer, Integer>(STARTING_WAITIMPL_COORDINATES, STARTING_WAITIMPL_COORDINATES),
                new Pair<Integer, Integer>(STARTING_WAITIMPL_SIZE, STARTING_WAITIMPL_SIZE), model);
    }

    /**
     * 
     */
    @Test
    void testInitialState() {
        assertEquals(WaitressState.WAITING, testWaitress1.getState());
        assertTrue(testWaitress1.getOrderList().isEmpty());
        assertEquals(2, testWaitress1.getMovementSpeed());
    }

    /**
     * 
     */
    @Test
    void testUpdate() {
        testWaitress1.setState(WaitressState.CALLING);
        final Table table = new TableImpl(pair1, pair2, 1);
        testWaitress1.setPosition(table.getPosition());

        while (!testWaitress1.getPosition().equals(table.getPosition())) {
            testWaitress1.update();
        }
        assertEquals(WaitressState.CALLING, testWaitress1.getState());

    }

    /**
     * 
     */
    @Test
    void testGoGetDish() {
        final Pair<Integer, Integer> dishReady = new Pair<>(DISH_READY, DISH_READY);
        testWaitress1.goGetDish(dishReady);
        assertEquals(dishReady, testWaitress1.getDestination().get());
        assertEquals(WaitressState.TAKING_DISH, testWaitress1.getState());
    }

    /**
     * 
     */
    @Test
    void testTakeTableOrder() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        testWaitress1.takeTableOrder(position);
        assertEquals(position, testWaitress1.getDestination().get());
        assertEquals(WaitressState.CALLING, testWaitress1.getState());
    }

    /**
     * 
     */
    @Test
    void testServeOrder() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        testWaitress1.serveOrder(position);
        assertEquals(position, testWaitress1.getDestination().get());
        assertEquals(WaitressState.SERVING, testWaitress1.getState());
    }

    /**
     * 
     */
    @Test
    void testCollectMoney() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        testWaitress1.collectMoney(position);
        assertEquals(position, testWaitress1.getDestination().get());
        assertEquals(WaitressState.TAKING_MONEY, testWaitress1.getState());
    }

    /**
     * 
     */
    @Test
    void testAddOrderForWaitress() {
        final Dish dish = new DishImpl(null, null, 1);
        testWaitress1.addOrderForWaitress(dish);
        assertFalse(testWaitress1.getOrderList().isEmpty());
        assertEquals(1, testWaitress1.getOrdersNumber());
        assertTrue(testWaitress1.getOrderList().contains(dish));
    }

    /**
     * 
     */
    @Test
    void testCheckRightTable() {
        final Dish dish = new DishImpl(null, null, 1);
        testWaitress1.addOrderForWaitress(dish);
        assertTrue(testWaitress1.checkRightTable(1));
        assertFalse(testWaitress1.checkRightTable(2));
    }

    /**
     * 
     */
    @Test
    void testIncrementSpeed() {
        testWaitress1.incrementSpeed();
        assertEquals(3, testWaitress1.getMovementSpeed());
        
        testWaitress1.incrementSpeed();
        assertEquals(4, testWaitress1.getMovementSpeed());
    }

}
