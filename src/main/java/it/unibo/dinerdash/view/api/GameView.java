package it.unibo.dinerdash.view.api;

import java.util.LinkedList;

import it.unibo.dinerdash.utility.impl.Pair;

public interface GameView {

    void clear();

    void render();

    // ---------------------------------------
    void addCustomerViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int multiplicity);

    void updateCustomerViewable(int index);

    void removeCustomerViewable(int index);
    // ---------------------------------------

    // ---------------------------------------
    void addChefViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active);
    // ---------------------------------------

    // ---------------------------------------
    void addWaitressViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active);

    void updateWaitressViewable(Pair<Integer, Integer> coordinates, int numDishes);
    // ---------------------------------------

    // ---------------------------------------
    void addDishViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numTable);

    void deleteDishViewable(int index);
    // ---------------------------------------

    // ---------------------------------------
    void addTableViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size);

    void updateTableViewable(int index, int peopleNumber, String state);
    // ---------------------------------------

    // NB: da usare solo nel Controller per richiamare i metodi update/remove queste sotto
    GameEntityViewableWithNumber getWaitressViewable();

    GameEntityViewable getChefViewable();

    LinkedList<GameEntityViewableWithNumberAndLabel> getCustomersViewable();

    LinkedList<GameEntityViewableWithNumberAndLabel> getTablesViewable();

    LinkedList<GameEntityViewableWithNumber> getDishesViewable();

}
