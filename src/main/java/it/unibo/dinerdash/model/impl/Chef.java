package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Chef
 */
public abstract class Chef extends GameEntityImpl implements Runnable {
    
    private static final int MIN_PREPARATION_TIME = 3 * 1000;
    private static final int MAX_PREPARATION_TIME = 10 * 1000;
    private ModelImpl model;

    public Chef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, ModelImpl model) {
        super(coordinates, size);
        this.setActive(false);
        this.model = model;
    }

    public void prepareDish() {
        //TODO
    }

    @Override
    public void run() {
        setActive(true); // Attiva il thread dello Chef

        while (isActive()) {
            try {
                // Verifica se ci sono piatti nella CounterTop
                if (model.getCounterTop().thereAreAvailableDishes()) {
                    // Simula il tempo di preparazione di un piatto
                    int preparationTime = (int) (Math.random() *
                        (MAX_PREPARATION_TIME - MIN_PREPARATION_TIME + 1)) + MIN_PREPARATION_TIME;
                    Thread.sleep(preparationTime);

                    /*
                     * // Prende il primo piatto dalla CounterTop
                    var dish = model.getCounterTop().getDishes().removeFirst();

                    // Imposta il piatto come pronto
                    dish.setReady(true);

                    // Aggiunge il piatto pronto al modello
                    model.addDish(dish);
                     */
                } else {
                    setActive(false); // Disattiva il thread dello Chef se non ci sono piatti nella CounterTop
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
