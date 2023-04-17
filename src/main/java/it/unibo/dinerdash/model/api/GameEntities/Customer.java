package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Customer extends GameEntityMovable {

    int getCustomerCount();

    void setState(final CustomerState state);

    CustomerState getState();
    
    void update(); 

    void setPosition(Pair<Integer, Integer> position);
}

