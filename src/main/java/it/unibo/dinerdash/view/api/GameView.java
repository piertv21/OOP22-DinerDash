package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public interface GameView {

    void clear();

    void render();

    // ---------------------------------------
    void addCustomerViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int multiplicity);

    void updateCustomersViewable(int index, GameEntity gameEntity, int patience);

    void removeCustomerViewable(int index);
    // ---------------------------------------

    // ---------------------------------------
    void addChefViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active);

    void updateChefViewable();
    // ---------------------------------------

    // ---------------------------------------
    void addWaitressViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active);

    void updateWaitressViewable(Pair<Integer, Integer> coordinates, int numDishes);
    // ---------------------------------------

    // ---------------------------------------
    void addDishViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numTable);

    void updateDishesViewable(int index, GameEntity gameEntity);

    void deleteDishViewable(int index);
    // ---------------------------------------

    // ---------------------------------------
    void addTableViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size);

    void updateTablesViewable(int index, GameEntity gameEntity, int peopleNumber, String state);
    // ---------------------------------------

}
