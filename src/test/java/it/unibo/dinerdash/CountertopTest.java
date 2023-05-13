package it.unibo.dinerdash;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private static final int DISHES_X_PADDING = (int) (0.06 * Constants.RESTAURANT_WIDTH);
    private static final int DISH_REL_WIDTH = (int) (0.04 * Constants.RESTAURANT_WIDTH);
    private static final int DISH_REL_HEIGHT = (int) (0.07 * Constants.RESTAURANT_HEIGHT);

    private static Countertop countertop;
    private static GameEntityFactory gameEntityFactory;

    @BeforeAll
    static void init() {
        countertop = new CountertopImpl(Optional.empty());
        gameEntityFactory = new GameEntityFactoryImpl();
    }

    @Test
    void test1() {
        assertEquals(countertop.thereAreDishesToPrepare(), false);
        assertEquals(countertop.getNextDishToPrepare(), Optional.empty());

        assertEquals(countertop.addOrder(1), true);
        assertEquals(countertop.addOrder(2), true);
        assertEquals(countertop.addOrder(3), true);
        assertEquals(countertop.addOrder(4), true);
        assertEquals(countertop.addOrder(5), false);
    }

    @Test
    void test2() {
        assertEquals(countertop.thereAreDishesToPrepare(), true);
    }

    @Test
    void testGetNextDishToPrepare() {
        final var dishPosition = new Pair<>(START_DISH_REL_X, START_DISH_REL_Y);
        final var dishSize = new Pair<>(DISH_REL_WIDTH, DISH_REL_HEIGHT);
        final var dish = this.gameEntityFactory.createDish(dishPosition, dishSize, 1);
        System.out.println(countertop.getNextDishToPrepare().get());
        //assertEquals(countertop.getNextDishToPrepare(), Optional.of(dish));
    }

    @Test
    void testSetDishReady() {

    }

    @Test
    void testTakeDish() {

    }

}
