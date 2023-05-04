package it.unibo.dinerdash.GameEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.GameEntities.Chef;
import it.unibo.dinerdash.model.api.GameEntities.GameEntityFactory;
import it.unibo.dinerdash.model.api.GameEntities.GameEntityFactoryImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public class ChefTest {

    private static final int CHEF_X = 700;
    private static final int CHEF_Y = 50;
    private static final int CHEF_WIDTH = 100;
    private static final int CHEF_HEIGHT = 20;

    private static Chef chef;
    private static GameEntityFactory gameEntityFactory;

    @BeforeAll
    static void init() {
        gameEntityFactory = new GameEntityFactoryImpl();

        final var position = new Pair<>(CHEF_X, CHEF_Y);
        final var size = new Pair<>(CHEF_WIDTH, CHEF_HEIGHT);

        chef = gameEntityFactory.createChef(position, size, Optional.empty());
    }

    @Test
    void test1() {
        assertEquals(chef.getCurrentDish(), Optional.empty());
        assertEquals(chef.getTimeDishReady(), Optional.empty());
        assertEquals(chef.getEnabledPowerUps(), 0);
    }

    @Test
    void test2() {
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
    void test3() {
        chef.completeCurrentDish();
        
        assertEquals(chef.getCurrentDish(), Optional.empty());
        assertEquals(chef.getTimeDishReady(), Optional.empty());
    }

    @Test
    void test4() {
        chef.reducePreparationTime();        
        assertEquals(chef.getEnabledPowerUps(), 1);

        chef.reducePreparationTime();        
        assertEquals(chef.getEnabledPowerUps(), 2);
    }

}
