package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.gameentities.Chef;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class ChefTest {

    private static final Pair<Integer, Integer> CHEF_POSITION = new Pair<>(700, 50);
    private static final Pair<Integer, Integer> CHEF_SIZE = new Pair<>(100, 20);

    private static Chef chef;
    private static GameEntityFactory gameEntityFactory;

    @BeforeAll
    static void init() {
        gameEntityFactory = new GameEntityFactoryImpl();
    }

    @Test
    void testInitialState() {
        chef = gameEntityFactory.createChef(CHEF_POSITION, CHEF_SIZE, Optional.empty());

        assertEquals(chef.getCurrentDish(), Optional.empty());
        assertEquals(chef.getTimeDishReady(), Optional.empty());
        assertEquals(chef.getEnabledPowerUps(), 0);
    }

    @Test
    void testDishPreparation() {
        chef = gameEntityFactory.createChef(CHEF_POSITION, CHEF_SIZE, Optional.empty());

        final var dish = gameEntityFactory.createDish(
            new Pair<>(10, 10),
            new Pair<>(10, 10),
            1
        );

        chef.startPreparingDish(dish);
 
        assertEquals(chef.getCurrentDish(), Optional.of(dish));
        assertNotEquals(chef.getTimeDishReady(), Optional.empty());
        assertEquals(chef.getEnabledPowerUps(), 0);
    }

    @Test
    void testDishPreparationEnding() {
        chef = gameEntityFactory.createChef(CHEF_POSITION, CHEF_SIZE, Optional.empty());

        final var dish = gameEntityFactory.createDish(
            new Pair<>(10, 10),
            new Pair<>(10, 10),
            1
        );
        chef.startPreparingDish(dish);

        chef.completeCurrentDish();
        assertEquals(chef.getCurrentDish(), Optional.empty());
        assertEquals(chef.getTimeDishReady(), Optional.empty());
    }

    @Test
    void testPowerUpEnabling() {
        chef = gameEntityFactory.createChef(CHEF_POSITION, CHEF_SIZE, Optional.empty());
        assertEquals(chef.getEnabledPowerUps(), 0);

        chef.reducePreparationTime();
        assertEquals(chef.getEnabledPowerUps(), 1);

        chef.reducePreparationTime();
        assertEquals(chef.getEnabledPowerUps(), 2);
    }

}
