package it.unibo.dinerdash.view.api;

public interface GameEntityViewableDecorator extends GameEntityViewable {

    GameEntityViewable getDecorated();
    
}
