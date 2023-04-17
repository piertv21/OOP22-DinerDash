package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.model.impl.DishImpl;

public interface Chef {
    
    void update();

    void startPreparingDish(DishImpl dish);

    void completeCurrentDish();

    void reducePreparationTime();

}
