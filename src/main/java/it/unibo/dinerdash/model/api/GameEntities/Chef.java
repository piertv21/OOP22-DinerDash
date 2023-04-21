package it.unibo.dinerdash.model.api.GameEntities;

public interface Chef extends GameEntity {
    
    void update();

    void startPreparingDish(Dish dish);

    void completeCurrentDish();

    void reducePreparationTime();

}
