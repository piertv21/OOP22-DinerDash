package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.AbstractGameEntity;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Chef
 */
public class Chef extends AbstractGameEntity {
    
    private static final int MIN_PREPARATION_TIME = 5;
    private static final int MAX_PREPARATION_TIME = 12;
    private static final int CHEF_TIME_SAVING = 2;

    private Optional<Dish> currentDish;
    private Optional<Long> timeDishReady;
    private int enabledPowerUps;
    private Model model;

    public Chef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        super(coordinates, size);
        this.setActive(false);
        this.currentDish = Optional.empty();
        this.timeDishReady = Optional.empty();
        this.enabledPowerUps = 0;
        this.model = model;
    }

    public void update() {
        // Verifica se c'è un piatto su cui lavorare
        if (this.currentDish.isPresent()) {
            // Verifica se il piatto corrente è pronto
            if (System.nanoTime() >= this.timeDishReady.get()) {
                this.completeCurrentDish();
            }
        } else {
            // Verifica se ci sono piatti da preparare sul CounterTop
            var counterTop = this.model.getCounterTop();

            if (counterTop.thereAreAvailableDishes()) {
                // Verifica se lo chef è attualmente inattivo
                if (!this.isActive()) {
                    this.setActive(true);
                }

                // Seleziona un nuovo piatto da preparare
                currentDish = counterTop.getDishInOrder();

                this.startPreparingDish(currentDish.get());
            } else {
                // Se non ci sono piatti da preparare, imposta lo stato di lavoro inattivo
                this.setActive(false);
            }
        }
    }

    public void startPreparingDish(Dish dish) {
        // Salva nuovo piatto
        this.currentDish = Optional.of(dish);

        // Calcola tempo piatto pronto
        var currentTime = System.nanoTime();
        int preparationTimeInSeconds = (int) (Math.random() * (MAX_PREPARATION_TIME - MIN_PREPARATION_TIME + 1)) + MIN_PREPARATION_TIME;
        int bonusTime = this.enabledPowerUps * CHEF_TIME_SAVING;
        this.timeDishReady = Optional.of(currentTime + TimeUnit.SECONDS.toNanos(preparationTimeInSeconds - bonusTime));
    }

    public void completeCurrentDish() {
        var counterTop = this.model.getCounterTop();
        counterTop.setDishReady(this.currentDish.get());

        this.currentDish = Optional.empty();
        this.timeDishReady = Optional.empty();
    }

    public void reducePreparationTime() {
        this.enabledPowerUps++;
    }
    
}
