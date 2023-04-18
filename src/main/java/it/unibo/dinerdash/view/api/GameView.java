package it.unibo.dinerdash.view.api;

import java.util.LinkedList;

import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public interface GameView {

    void clear();

    void render();

    void addCustomerViewable(int num, Pair<Integer, Integer> size);

    void removeCustomerViewable(int indexVal);

    GameEntityViewable getViewableWaitress();

    LinkedList<GameEntityViewable> getViewableTable();

    void UpdateViewableCustomer(int index, GameEntity elem);

    void UpdateViewableTable(int index, GameEntity elem);

    void adddTableViewable(Pair<Integer, Integer> pos, int tableNum, Pair<Integer, Integer> size);

}
