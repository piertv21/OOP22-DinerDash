package it.unibo.dinerdash.model.api.GameEntities;

import java.util.LinkedList;

import it.unibo.dinerdash.model.api.States.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Waitress extends GameEntityMovable {

    void handleMovement();

    void setState(WaitressState state);

    WaitressState getState();

    void goGetDish(Dish dishReady);

    void takeTableOrder(Pair<Integer, Integer> position);

    void serveOrder(Pair<Integer, Integer> position);

    void colletMoney(Pair<Integer, Integer> position);

    int getOrdersNumber();

    void addOrderForWaitress(Dish dishReady);

    LinkedList<Dish> getOrderList();

    boolean checkRightTable(int tableNumber);

    void setPosition(Pair<Integer, Integer> position);

    void incrementSped();

}
