package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Chef
 */
public class Chef extends GameEntityImpl {
    
    private static final int MIN_PREPARATION_TIME = 5;
    private static final int MAX_PREPARATION_TIME = 12;
    private static final int CHEF_TIME_SAVING = 2;

    private Optional<Dish> currentDish;
    private int timeDishReady;
    private ModelImpl model;
    private int enabledPowerUps;

    public Chef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, ModelImpl model) {
        super(coordinates, size);
        this.setActive(false);
        this.currentDish = Optional.empty();
        this.timeDishReady = 0;
        this.enabledPowerUps = 0;
        this.model = model;
    }

    public void update() {
        // Verifica se c'è un piatto su cui lavorare
        if (this.currentDish.isPresent()) {
            // Verifica se il piatto corrente è pronto
            if (this.model.getRemainingTime() <= this.timeDishReady) {
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
                currentDish = Optional.of(counterTop.getDishInOrder());

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
        var remainingTime = this.model.getRemainingTime();
        int preparationTime = (int) (Math.random() * (MAX_PREPARATION_TIME - MIN_PREPARATION_TIME + 1)) + MIN_PREPARATION_TIME;
        int bonusTime = this.enabledPowerUps * CHEF_TIME_SAVING;
        this.timeDishReady = (remainingTime - preparationTime) + bonusTime;
    }

    public void completeCurrentDish() {
        var counterTop = this.model.getCounterTop();
        counterTop.setDishReady(this.currentDish.get());

        this.currentDish = Optional.empty();
        this.timeDishReady = 0;
    }

    public void reducePreparationTime() {
        this.enabledPowerUps++;
    }
    
}
