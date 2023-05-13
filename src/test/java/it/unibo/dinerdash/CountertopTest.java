package it.unibo.dinerdash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.model.api.Countertop;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.impl.CountertopImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class CountertopTest {

    private static final int START_DISH_REL_X = (int) (0.37 * Constants.RESTAURANT_WIDTH);
    private static final int START_DISH_REL_Y = (int) (0.19 * Constants.RESTAURANT_HEIGHT);
    private static final int DISH_REL_WIDTH = (int) (0.04 * Constants.RESTAURANT_WIDTH);
    private static final int DISH_REL_HEIGHT = (int) (0.07 * Constants.RESTAURANT_HEIGHT);
    private static final int FIVE = 5;

    private static Countertop countertop;
    private static GameEntityFactory gameEntityFactory;

    @BeforeAll
    static void init() {
        countertop = new CountertopImpl(Optional.empty());
        gameEntityFactory = new GameEntityFactoryImpl();
    }

    @Test
    void test1() {
        assertFalse(countertop.thereAreDishesToPrepare());
        assertEquals(countertop.getNextDishToPrepare(), Optional.empty());

        assertTrue(countertop.addOrder(1));
        assertTrue(countertop.addOrder(2));
        assertTrue(countertop.addOrder(3));
        assertTrue(countertop.addOrder(4));
        assertFalse(countertop.addOrder(FIVE));
    }

    @Test
    void test2() {
        assertTrue(countertop.thereAreDishesToPrepare());
    }

    @Test
    void test3() {
        final var dish = gameEntityFactory.createDish(
            new Pair<>(START_DISH_REL_X, START_DISH_REL_Y),
            new Pair<>(DISH_REL_WIDTH, DISH_REL_HEIGHT),
            1
        );
        final var dishFromList = countertop.getNextDishToPrepare().get();

        assertEquals(dishFromList.getDishNumber(), dish.getDishNumber());
        assertEquals(dishFromList.getPosition(), dish.getPosition());
        assertEquals(dishFromList.getSize(), dish.getSize());
    }

    @Test
    void test4() {
        countertop.setDishReady(1);

        assertEquals(countertop.getNextDishToPrepare().get().getDishNumber(), 2);
    }

    @Test
    void test5() {
        var coord = new Pair<>(1, 1);
        assertEquals(countertop.takeDish(coord), Optional.empty());

        coord = new Pair<>(START_DISH_REL_X, START_DISH_REL_Y);
        assertNotEquals(countertop.takeDish(coord), Optional.empty());
    }

}
