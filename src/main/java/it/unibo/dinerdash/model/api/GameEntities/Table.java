package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.model.impl.CustomerImpl;

public interface Table {

    void setState(TableState TableState);

    TableState getState();

    void setCustom(Optional<CustomerImpl> cs);

    Optional<CustomerImpl> getCustomer();

    int getTableNumber();

    int setSeatedPeople(int sppl);

    int getPeopleSeatedNumber();

    void startEating();

    void update();

    void getPosition();
}
