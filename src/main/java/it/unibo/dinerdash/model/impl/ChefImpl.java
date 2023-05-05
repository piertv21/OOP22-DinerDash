package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.gameentities.Chef;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the Chef interface.
 */
public class ChefImpl extends AbstractGameEntity implements Chef {

    private static final int MIN_PREPARATION_TIME = 5;
    private static final int MAX_PREPARATION_TIME = 12;
    private static final int CHEF_TIME_SAVING = 2;

    private Optional<Dish> currentDish;
    private Optional<Long> timeDishReady;
    private int enabledPowerUps;
    private final Optional<Model> model;

    /**
     * Class constructor.
     * 
     * @param coordinates are the coordinates of the chef in the restaurant
     * @param size is the chef size in the restaurant
     * @param model is the reference to the Model
     */
    public ChefImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final Optional<Model> model) {
        super(coordinates, size);
        this.setActive(false);
        this.currentDish = Optional.empty();
        this.timeDishReady = Optional.empty();
        this.enabledPowerUps = 0;
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (this.currentDish.isPresent()) {
            if (System.nanoTime() >= this.timeDishReady.get()) {
                this.completeCurrentDish();
            }
        } else {
            if (this.model.get().thereAreDishesToPrepare()) {
                if (!this.isActive()) {
                    this.setActive(true);
                }

                currentDish = this.model.get().getDishToPrepare();
                this.startPreparingDish(currentDish.get());
            } else {
                this.setActive(false);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reducePreparationTime() {
        this.enabledPowerUps++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startPreparingDish(final Dish dish) {
        this.currentDish = Optional.of(dish);

        final int preparationTimeInSeconds =
            (int) (Math.random() * (MAX_PREPARATION_TIME - MIN_PREPARATION_TIME + 1)) + MIN_PREPARATION_TIME;
        final int bonusTime = this.enabledPowerUps * CHEF_TIME_SAVING;
        final var currentTime = System.nanoTime();
        this.timeDishReady = Optional.of(currentTime + TimeUnit.SECONDS.toNanos(preparationTimeInSeconds - bonusTime));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completeCurrentDish() {
        this.model.ifPresent(m -> m.completeDishPreparation(this.currentDish.get()));

        this.currentDish = Optional.empty();
        this.timeDishReady = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dish> getCurrentDish() {
        return this.currentDish;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Long> getTimeDishReady() {
        return this.timeDishReady;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEnabledPowerUps() {
        return this.enabledPowerUps;
    }

}
