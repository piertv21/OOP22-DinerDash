package it.unibo.dinerdash.model.api.gameentities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.states.CustomerState;
import it.unibo.dinerdash.utility.impl.Pair;

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
     * Getter for customer Position.
     * 
     * @return customer's InGame state 
     */
    @Override
    Pair<Integer, Integer> getPosition();

    /** 
     * Setter for customer Destination.
     * 
     * @param destination Customer's new destination
     */
    @Override
    void setDestination(Optional<Pair<Integer, Integer>> destination);

    /** 
     * Getter for customer Destination.
     * 
     * @return customer's destination or null
     */
    @Override
    Optional<Pair<Integer, Integer>> getDestination();

    /** 
     * Setter for customer Position.
     * 
     * @param position Customer's new position
     */
    @Override
    void setPosition(Pair<Integer, Integer> position);

    /** 
     * Getter for customers's patience.
     * 
     * @return customers's patience level
     */
    int getCustomerPatience();

}

