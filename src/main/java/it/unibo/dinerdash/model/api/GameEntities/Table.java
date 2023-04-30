package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;
import it.unibo.dinerdash.model.api.States.TableState;

/**
  * 
  * 
  */
public interface Table extends GameEntity {

    /**
     * @param tableState
     */
    void setState(TableState tableState);

    /**
     * The state of the table.
     * 
     * @return The state of the table
     */
    TableState getState();

    /**
     * @param cs
     */
    void setCustom(Optional<Customer> cs);

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
     * @param sppl The number of people seated at the table
     * @return The number of people seated at the table
     */
    int setseatedPeople(int sppl);

    /**
     * Returns the number of people seated at the table.
     * 
     * @return The number of people seated at the table
     */
    int getPeopleSeatedNumber();

    /**
     * 
     */
    void startEating();

    /**
     * 
     */
    void update();

    /**
     * Returns the state of the table in text format.
     * 
     * @return The state of the table in text format
     */
    String getStateInText();

}
