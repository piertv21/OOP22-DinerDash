package it.unibo.dinerdash.model.api.gameentities;

import java.util.LinkedList;

import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
  * 
  * 
  */
public interface Waitress extends GameEntityMovable {

  /**
   * Move waitress and modify his state.
   */
  void update();

  /**
   * Set state for waitress.
   * 
   * @param state
   */
  void setState(WaitressState state);

  /**
   * Returns the state of the waitress.
   * 
   * @return The state of the waitress
   */
  WaitressState getState();

  /**
   * Set waitress destination and send order.
   * 
   * @param position
   */
  void takeTableOrder(Pair<Integer, Integer> position);

  /**
   * Set waitress destination and send serve order.
   * 
   * @param position
   */
  void serveOrder(Pair<Integer, Integer> position);

  /**
   * Set waitress destination and take money.
   * 
   * @param position
   */
  void collectMoney(Pair<Integer, Integer> position);

  /**
   * Returns the number of orders.
   * 
   * @return The number of orders
   */
  int getOrdersNumber();

  /**
   * @param dishReady
   */
  void addOrderForWaitress(Dish dishReady);

  /**
   * Returns the list of orders.
   * 
   * @return The list of orders
   */
  LinkedList<Dish> getOrderList();

  /**
   * Checks if the table number is correct.
   * 
   * @param tableNumber The table number to check
   * @return True if the table number is correct, false otherwise
   */
  boolean checkRightTable(int tableNumber);

  /**
   * Adjust waitress speed movement.
   */
  void incrementSpeed();

  /**
   * Teaking dish form order.
   * 
   * @param dishReady
   */
  void goGetDish(Pair<Integer, Integer> dishReady);

}
