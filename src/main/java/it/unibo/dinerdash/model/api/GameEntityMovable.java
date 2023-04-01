package it.unibo.dinerdash.model.api;

public interface GameEntityMovable extends GameEntity {
    
    void setMovementSpeed(int speed);

    void moveUp();

    void moveDown();

    void moveRight();

    void moveLeft();

}
