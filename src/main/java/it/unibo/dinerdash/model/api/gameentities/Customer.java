package it.unibo.dinerdash.model.api.gameentities;

import it.unibo.dinerdash.model.api.states.CustomerState;

/**
 * Interface with the method's to control and update
 * all Customer's instances.
 */
public interface Customer extends GameEntityMovable {

    /** 
     * Getter for customer's moltiplicity.
     * 
     * @return number of customers from 1-4
     */
    int getCustomerCount();

    /** 
     * Setter for customers's enum state.
     * 
     * @param state new Customer's state
     */
    void setState(CustomerState state);

    /** 
     * Getter for customers's enum state.
     * 
     * @return customer's InGame state 
     */
    CustomerState getState();

    /** 
     * Manage the movement of customers ,or their behaviour.
     */
    void update();

    /** 
     * Getter for customers's patience.
     * 
     * @return customers's patience level
     */
    int getCustomerPatience();

}

