package it.unibo.dinerdash.model.api.gameentities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.states.TableState;

/**
 * Interface with the method's to control and update
 * all Table's instances.
 */
public interface Table extends GameEntity {

  /**
   * Set table state.
   * 
   * @param tableState is the new Table state
   */
  void setState(TableState tableState);

  /**
   * The state of the table.
   * 
   * @return yhe state of the table
   */
  TableState getState();

  /**
   * Set reference to a customer in the table.
   * 
   * @param customer is the customer at the table
   */
  void setCustom(Optional<Customer> customer);

  /**
   * Returns an Optional object containing the customer.
   * 
   * @return An Optional object containing the customer
   */
  Optional<Customer> getCustomer();

  /**
   * Returns the table number.
   * 
   * @return The table number
   */
  int getTableNumber();

  /**
   * Sets the number of people seated at the table.
   * 
   * @param peopleAreSeated The number of people seated at the table
   */
  void setSeatedPeople(int peopleAreSeated);

  /**
   * Returns the number of people seated at the table.
   * 
   * @return The number of people seated at the table
   */
  int getPeopleSeatedNumber();

  /**
   * Start timer for costumer dinner.
   */
  void startEating();

  /**
   * Stop timer for costumer dinner and change state.
   */
  void update();

  /**
   * Returns the state of the table in text format.
   * 
   * @return The state of the table in text format
   */
  String getStateInText();

  /**
   * Return the time for finish eating.
   * 
   * @return the time for finish eating
   */
  Optional<Long> getTimeFinishEating();

}
