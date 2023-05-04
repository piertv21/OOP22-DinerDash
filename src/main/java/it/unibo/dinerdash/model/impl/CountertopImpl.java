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

public class CountertopImpl implements Countertop {

    private static final int START_DISH_REL_X = (int) (0.37 * Constants.RESTAURANT_WIDTH);
    private static final int START_DISH_REL_Y = (int) (0.20 * Constants.RESTAURANT_HEIGHT);
    private static final int DISHES_X_PADDING = (int) (0.06 * Constants.RESTAURANT_WIDTH);
    private static final int DISH_REL_WIDTH = (int) (0.04 * Constants.RESTAURANT_WIDTH);
    private static final int DISH_REL_HEIGHT = (int) (0.07 * Constants.RESTAURANT_HEIGHT);
    private static final int MAX_COUNTERTOP_DISHES = 4;
    private GameEntityFactory factory;

    private LinkedList<Dish> dishes;
    private Model model;

    public CountertopImpl(Model model) {
        this.model = model;
        this.dishes = new LinkedList<>();
        this.factory = new GameEntityFactoryImpl();
    }

    @Override
    public void addOrder(int tableNumber) {
        if(this.dishes.size() < MAX_COUNTERTOP_DISHES) {

            var coordX = this.getFirstFreeX();
            var coordY = START_DISH_REL_Y;
            var dishPosition = new Pair<>(coordX, coordY);
            var dishSize = new Pair<>(DISH_REL_WIDTH, DISH_REL_HEIGHT);
            var dish = this.factory.createDish(dishPosition, dishSize, tableNumber);

            this.dishes.add(dish);
            this.model.addDishToView(dish);
        }
    }

    private int getFirstFreeX() {
        // Punto iniziale
        int startPoint = START_DISH_REL_X;
    
        // Creo una lista ordinata delle coordinate X dei piatti presenti nella lista
        List<Integer> xCoordinates = dishes.stream()
                .map(dish -> dish.getPosition().getX())
                .sorted()
                .collect(Collectors.toList());
    
        // Cerco il primo multiplo mancante partendo dal punto iniziale
        return IntStream.iterate(startPoint, x -> x + DISHES_X_PADDING)
                .filter(x -> !xCoordinates.contains(x))
                .findFirst()
                .orElse(startPoint);
    }

    @Override
    public Optional<Dish> takeDish(Pair<Integer, Integer> coordinates) {
        Optional<Dish> dishToRemove = this.dishes.stream()
                .filter(dish -> dish.getPosition().equals(coordinates))
                .findFirst()
                .map(dish -> {                    
                    var dishIndex = this.dishes.indexOf(dish);
                    this.model.removeDishInView(dishIndex);
                    dishes.remove(dish);
                    return dish;
                });
        return dishToRemove;
    }

    @Override
    public void clear() {
        this.dishes.clear();
    }

    //dice se ci son piatti ancora con active = false
    @Override
    public boolean thereAreDishesToPrepare() {
        return this.dishes.stream().anyMatch(e -> !e.isActive());
    }

    // prossimo dish da preparare (active = false)
    @Override
    public Optional<Dish> getNextDishToPrepare() {
        return this.dishes.stream()
            .filter(dish -> !dish.isActive())
            .findFirst();
    }

    // Dato un dish lo imposta a ready, chiamata dallo Chef
    public void setDishReady(Dish dish) {
        var oldDishIndex = this.dishes.indexOf(dish);
        var dishInList = this.dishes.get(oldDishIndex);
        dishInList.setActive(true);
        this.model.updateDishInView(oldDishIndex, dishInList);
    }

}