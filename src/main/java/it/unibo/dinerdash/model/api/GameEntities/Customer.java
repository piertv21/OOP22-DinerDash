package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Customer {

    int getCustomerCount();

    void setState(final CustomerState state);

    CustomerState getState();
    
    void update();

    Pair<Integer, Integer> getPosition();

    void setDestination(final Optional<Pair<Integer, Integer>> destination);

    Optional<Pair<Integer, Integer>> getDestination();

    void setPosition(Pair<Integer, Integer> position);

}

