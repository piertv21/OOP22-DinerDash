package it.unibo.dinerdash.view.api;

import java.util.LinkedList;

import it.unibo.dinerdash.utility.impl.Pair;

public interface GameView {
    
    void assignNewImage(GameEntityViewableWithLabel gameEWWithLabel, int multiplicity);

    void clear();

    void render();

    void addCustomerViewable(int num, Pair<Integer,Integer> size);

    void removeCustomerViewable(int indexVal);

    GameEntityViewable getViewableWaitress();

    LinkedList<GameEntityViewable> getViewableTable();

    LinkedList<GameEntityViewable> getViewableCustomersList();
    
}
