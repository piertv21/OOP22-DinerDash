package it.unibo.dinerdash.model.api.GameEntities;

import java.util.LinkedList;

import it.unibo.dinerdash.model.api.States.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Waitress extends GameEntityMovable {

    void update();

    void setState(WaitressState state);

    WaitressState getState();

    void takeTableOrder(Pair<Integer, Integer> position);

    void serveOrder(Pair<Integer, Integer> position);

    void collectMoney(Pair<Integer, Integer> position);

    int getOrdersNumber();

    void addOrderForWaitress(Dish dishReady);

    LinkedList<Dish> getOrderList();

    boolean checkRightTable(int tableNumber);

    void setPosition(Pair<Integer, Integer> position);

    void incrementSpeed();

    void goGetDish(Pair<Integer, Integer> dishReady);

}
