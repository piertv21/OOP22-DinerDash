package it.unibo.dinerdash.GameEntities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.controller.impl.ControllerImpl;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.Chef;
import it.unibo.dinerdash.model.impl.ChefImpl;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public class ChefTest {

    private static final int CHEF_X = 700;
    private static final int CHEF_Y = 50;
    private static final int CHEF_WIDTH = 100;
    private static final int CHEF_HEIGHT = 20;

    private Chef chef;
    private Model model;
    private Controller controller;

    @BeforeAll
    void init() {
        this.controller = new ControllerImpl();
        this.model = new ModelImpl(this.controller);

        final var position = new Pair<>(CHEF_X, CHEF_Y);
        final var size = new Pair<>(CHEF_WIDTH, CHEF_HEIGHT);
        this.chef = new ChefImpl(position, size, this.model);
    }

    @Test
    void testReducePreparationTime() {

    }

    @Test
    void testUpdate() {

    }

}
