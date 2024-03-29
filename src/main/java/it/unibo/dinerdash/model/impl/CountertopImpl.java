package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.model.api.Countertop;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/**
* {@inheritDoc}.
*
* Implementation of the CounterTop Interface.
*/
public class CountertopImpl implements Countertop {

    private static final int START_DISH_REL_X = (int) (0.37 * Constants.RESTAURANT_WIDTH);
    private static final int START_DISH_REL_Y = (int) (0.19 * Constants.RESTAURANT_HEIGHT);
    private static final int DISHES_X_PADDING = (int) (0.06 * Constants.RESTAURANT_WIDTH);
    private static final int DISH_REL_WIDTH = (int) (0.04 * Constants.RESTAURANT_WIDTH);
    private static final int DISH_REL_HEIGHT = (int) (0.07 * Constants.RESTAURANT_HEIGHT);
    private static final int MAX_COUNTERTOP_DISHES = 4;
    private final GameEntityFactory factory;

    private final List<Dish> dishes;
    private final Optional<Model> model;

    /**
     * Class constructor.
     * 
     * @param model is the model
     */
    public CountertopImpl(final Optional<Model> model) {
        this.model = model;
        this.dishes = new LinkedList<>();
        this.factory = new GameEntityFactoryImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addOrder(final int tableNumber) {
        if (this.dishes.size() < MAX_COUNTERTOP_DISHES) {

            final var coordX = this.getFirstFreeX();
            final var coordY = START_DISH_REL_Y;
            final var dishPosition = new Pair<>(coordX, coordY);
            final var dishSize = new Pair<>(DISH_REL_WIDTH, DISH_REL_HEIGHT);
            final var dish = this.factory.createDish(dishPosition, dishSize, tableNumber);

            this.dishes.add(dish);
            this.model.ifPresent(m -> m.addDishToView(dish));
            return true;
        }
        return false;
    }

    private int getFirstFreeX() {
        final int startPoint = START_DISH_REL_X;

        final List<Integer> xCoordinates = dishes.stream()
                .map(dish -> dish.getPosition().getX())
                .sorted()
                .collect(Collectors.toList());

        return IntStream.iterate(startPoint, x -> x + DISHES_X_PADDING)
                .filter(x -> !xCoordinates.contains(x))
                .findFirst()
                .orElse(startPoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dish> takeDish(final Pair<Integer, Integer> coordinates) {
        return this.dishes.stream()
                .filter(dish -> dish.getPosition().equals(coordinates) && dish.isActive())
                .findFirst()
                .map(dish -> {
                    final var dishIndex = this.dishes.indexOf(dish);
                    this.model.ifPresent(m -> m.removeDishInView(dishIndex));
                    dishes.remove(dish);
                    return dish;
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.dishes.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean thereAreDishesToPrepare() {
        return this.dishes.stream().anyMatch(e -> !e.isActive());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dish> getNextDishToPrepare() {
        return this.dishes.stream()
            .filter(dish -> !dish.isActive())
            .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDishReady(final int dishNumber) {
        final Optional<Dish> dishOptional = dishes.stream()
                .filter(dish -> dish.getDishNumber() == dishNumber)
                .findFirst();

        if (dishOptional.isPresent()) {
            final var dish = dishOptional.get();
            final int dishIndex = dishes.indexOf(dish);
            dish.setActive(true);
            model.ifPresent(m -> m.updateDishInView(dishIndex, dish));
        }
    }

}
