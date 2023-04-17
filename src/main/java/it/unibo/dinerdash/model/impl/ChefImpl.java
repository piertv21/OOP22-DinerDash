package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.Chef;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Chef
 */
public class ChefImpl extends AbstractGameEntity implements Chef {
    
    private static final int MIN_PREPARATION_TIME = 5;
    private static final int MAX_PREPARATION_TIME = 12;
    private static final int CHEF_TIME_SAVING = 2;

    private Optional<Dish> currentDish;
    private Optional<Long> timeDishReady;
    private int enabledPowerUps;
    private Model model;

    public ChefImpl(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        super(coordinates, size);
        this.setActive(false);
        this.currentDish = Optional.empty();
        this.timeDishReady = Optional.empty();
        this.enabledPowerUps = 0;
        this.model = model;
    }

    @Override
    public void update() {
        if (this.currentDish.isPresent()) {
            if (System.nanoTime() >= this.timeDishReady.get()) {
                this.completeCurrentDish();
            }
        } else {
            if (this.model.thereAreDishesToPrepare()) {
                
                if (!this.isActive()) {
                    this.setActive(true);
                }
                
                currentDish = this.model.getDishToPrepare();

                this.startPreparingDish(currentDish.get());
            } else {
                
                this.setActive(false);
            }
        }
    }

    @Override
    public void startPreparingDish(Dish dish) {
        this.currentDish = Optional.of(dish);

        int preparationTimeInSeconds = (int) (Math.random() * (MAX_PREPARATION_TIME - MIN_PREPARATION_TIME + 1)) + MIN_PREPARATION_TIME;
        int bonusTime = this.enabledPowerUps * CHEF_TIME_SAVING;
        var currentTime = System.nanoTime();
        this.timeDishReady = Optional.of(currentTime + TimeUnit.SECONDS.toNanos(preparationTimeInSeconds - bonusTime));
    }

    @Override
    public void completeCurrentDish() {
        this.model.completeDishPreparation(this.currentDish.get());

        this.currentDish = Optional.empty();
        this.timeDishReady = Optional.empty();
    }

    @Override
    public void reducePreparationTime() {
        this.enabledPowerUps++;
    }
    
}
