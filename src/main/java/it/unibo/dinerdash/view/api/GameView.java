package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.utility.impl.Pair;

public interface GameView {

    void clear();

    void render();

    // ---------------------------------------
    void addCustomerViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int multiplicity, int patience);

    void updateCustomersViewable(int index, Pair<Integer, Integer> coordinates, boolean active, int patience);

    void removeCustomerViewable(int index);
    // ---------------------------------------

    // ---------------------------------------
    void addChefViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active);

    void updateChefViewable(boolean active);
    // ---------------------------------------

    // ---------------------------------------
    void addWaitressViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numDishes);

    void updateWaitressViewable(Pair<Integer, Integer> coordinates, int numDishes);
    // ---------------------------------------

    // ---------------------------------------
    void addDishViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numTable);

    void updateDishesViewable(int index, boolean active);

    void deleteDishViewable(int index);
    // ---------------------------------------

    // ---------------------------------------
    void addTableViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int peopleNumer, Image state);

    void updateTablesViewable(int index, int peopleNumber, Image state);
    // ---------------------------------------

}
