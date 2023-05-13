package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class DishTest {

    private static final int DISH_X = 100;
    private static final int DISH_Y = 150;
    private static final int DISH_SIZE = 50;

    private static Dish dish;
    private static GameEntityFactory gameEntityFactory;

    @BeforeAll
    static void init() {
        gameEntityFactory = new GameEntityFactoryImpl();

        final var position = new Pair<>(DISH_X, DISH_Y);
        final var size = new Pair<>(DISH_SIZE, DISH_SIZE);

        dish = gameEntityFactory.createDish(position, size, 1);
    }

    @Test
    void testGetDishNumber() {
        assertEquals(dish.getDishNumber(), 1);
    }

}
