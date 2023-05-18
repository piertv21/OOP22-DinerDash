package it.unibo.dinerdash.model.api.gameentities;

import java.util.List;

import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * Interface with the method's to control and update
 * all Waitress instances.
 */
public interface Waitress extends GameEntityMovable {

  /**
   * Move waitress and modify his state.
   */
  void update();

  /**
   * Set state for waitress.
   * 
   * @param state is the new Waitress state
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
   * @param position is the table position
   */
  void takeTableOrder(Pair<Integer, Integer> position);

  /**
   * Set waitress destination and send serve order.
   * 
   * @param position is the table position
   */
  void serveOrder(Pair<Integer, Integer> position);

  /**
   * Set waitress destination and take money.
   * 
   * @param position is the table position
   */
  void collectMoney(Pair<Integer, Integer> position);

  /**
   * Returns the number of orders.
   * 
   * @return The number of orders
   */
  int getOrdersNumber();

  /**
   * Returns the list of orders.
   * 
   * @return The list of orders
   */
  List<Dish> getOrderList();

  /**
   * Adjust waitress speed movement.
   */
  void incrementSpeed();

  /**
   * It takes a dish from the countertop.
   * 
   * @param dish is the dish position
   */
  void goGetDish(Pair<Integer, Integer> dish);

}
