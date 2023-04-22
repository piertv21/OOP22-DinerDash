package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Customer extends GameEntityMovable {

    /**
     * getter for customer's moltiplicity
     * @return number of customers from 1-4
     */
    int getCustomerCount();

    /**
     * for customers's enum state
     * @param state new Customer's state
     */
    void setState(final CustomerState state);

    /**
     * getter for customers's enum state
     * @return customer's InGame state 
     */
    CustomerState getState();
    
    /**
     * manage the movement of customers ,or their behaviour
     */
    void update();

    Pair<Integer, Integer> getPosition();

    void setDestination(final Optional<Pair<Integer, Integer>> destination);

    Optional<Pair<Integer, Integer>> getDestination();

    void setPosition(Pair<Integer, Integer> position);

    /**
     * getter for customers's patience 
     * @return customers's patience level
     */
    int getCustomerPatience();

}

