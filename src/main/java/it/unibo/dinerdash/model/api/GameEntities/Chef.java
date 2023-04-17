package it.unibo.dinerdash.model.api.GameEntities;

public interface Chef {
    
    void update();

    void startPreparingDish(Dish dish);

    void completeCurrentDish();

    void reducePreparationTime();

}
