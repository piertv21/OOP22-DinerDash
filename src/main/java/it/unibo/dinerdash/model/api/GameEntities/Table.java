package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Table extends GameEntity {

    void setState(TableState TableState);

    TableState getState();

    void setCustom(Optional<Customer> cs);

    Optional<Customer> getCustomer();

    int getTableNumber();

    int setSeatedPeople(int sppl);

    int getPeopleSeatedNumber();

    void startEating();

    void update();

    Pair<Integer, Integer> getPosition();
    
}
