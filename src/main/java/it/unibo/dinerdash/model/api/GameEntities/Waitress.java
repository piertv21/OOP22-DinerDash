package it.unibo.dinerdash.model.api.GameEntities;

import java.util.LinkedList;

import it.unibo.dinerdash.model.api.States.WaitressState;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Waitress {

    void handleMovement(DishImpl dishReady);

    void setState(WaitressState state);

    WaitressState getState();

    void goGetDish(DishImpl dishReady);

    void takeTableOrder(Pair<Integer, Integer> position);

    void serveOrder(Pair<Integer, Integer> position);

    void colletMoney(Pair<Integer, Integer> position);

    int getOrdersNumber();

    void addOrderForWaitress(DishImpl dishReady);

    LinkedList<DishImpl> getOrderList();

    boolean checkRightTable(int tableNumber);

}
