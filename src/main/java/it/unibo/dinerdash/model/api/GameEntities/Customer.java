package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.model.api.States.CustomerState;

public interface Customer {

    int getCustomerCount();

    void setState(final CustomerState state);

    CustomerState getState();
    
    void update(); 
}

