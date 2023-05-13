package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class WaitressTest {

    private Waitress waitress;
    private static final int STARTING_WAITRESS_SIZE = 50;
    private static final int STARTING_WAITRESS_COORDINATES = 0;
    private static final int DISH_SIZE = 100;
    private static final int PAIR_POSITION = 200;
    private final GameEntityFactory factory = new GameEntityFactoryImpl();

    @BeforeEach
    void init() {
        waitress = this.factory.createWaitress(
                new Pair<Integer, Integer>(STARTING_WAITRESS_COORDINATES, STARTING_WAITRESS_COORDINATES),
                new Pair<Integer, Integer>(STARTING_WAITRESS_SIZE, STARTING_WAITRESS_SIZE),
                Optional.of(new ModelImpl()));
    }

    @Test
    void testInitialState() {
        assertEquals(WaitressState.WAITING, waitress.getState());
        assertTrue(waitress.getOrderList().isEmpty());
        assertEquals(2, waitress.getMovementSpeed());
    }

    @Test
    void testUpdate() {
        waitress.setState(WaitressState.CALLING);
        Table table = this.factory.createTable(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                2);
        waitress.setPosition(table.getPosition());

        while (!waitress.getPosition().equals(table.getPosition())) {
            waitress.update();
        }
        assertEquals(WaitressState.CALLING, waitress.getState());

    }

    @Test
    void testGoGetDish() {
        final Pair<Integer, Integer> dishReady = new Pair<>(DISH_SIZE, DISH_SIZE);
        waitress.goGetDish(dishReady);
        assertEquals(dishReady, waitress.getDestination().get());
        assertEquals(WaitressState.TAKING_DISH, waitress.getState());
    }

    @Test
    void testTakeTableOrder() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.takeTableOrder(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.CALLING, waitress.getState());
    }

    @Test
    void testServeOrder() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.serveOrder(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.SERVING, waitress.getState());
    }

    @Test
    void testCollectMoney() {
        final Pair<Integer, Integer> position = new Pair<>(PAIR_POSITION, PAIR_POSITION);
        waitress.collectMoney(position);
        assertEquals(position, waitress.getDestination().get());
        assertEquals(WaitressState.TAKING_MONEY, waitress.getState());
    }

    @Test
    void testAddOrderForWaitress() {
        Dish dish = this.factory.createDish(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                1);
        waitress.addOrderForWaitress(dish);
        assertFalse(waitress.getOrderList().isEmpty());
        assertEquals(1, waitress.getOrdersNumber());
        assertTrue(waitress.getOrderList().contains(dish));
    }

    @Test
    void testCheckRightTable() {
        Dish dish = this.factory.createDish(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                1);
        waitress.addOrderForWaitress(dish);
        assertTrue(waitress.checkRightTable(1));
        assertFalse(waitress.checkRightTable(2));
    }

    @Test
    void testIncrementSpeed() {
        waitress.incrementSpeed();
        assertEquals(3, waitress.getMovementSpeed());
        waitress.incrementSpeed();
        assertEquals(4, waitress.getMovementSpeed());
    }

}
