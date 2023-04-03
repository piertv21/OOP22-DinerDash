package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Table is not a thread
 */
public class Table extends GameEntityImpl {
    
    private int tableNumber;
    private int peopleSeatedNumber; // Equals to customers number
    private Optional<Customer> custom;
    private TableState state;

    private enum TableState {
        THINKING,
        ORDERING,
        WAITING_MEAL,
        EATING,
        WANTING_TO_PAY,
        EMPTY
    }

    public Table(Pair<Integer, Integer> coordinates, int tableNumber) {
        super(coordinates);
        this.tableNumber = tableNumber;
        // TODO Auto-generated method stub
    }

    public int getPeopleSeatedNumber() {
        return this.peopleSeatedNumber;
    }

    public void setPeopleSeatedNumber(int peopleSeatedNumber) {
        this.peopleSeatedNumber = peopleSeatedNumber;
    }

    public void setState(TableState TableState){                                     
        this.state = TableState;
    }

    public TableState getState(){                                     
        return this.state;
    }

    public boolean isFree() {
        if(peopleSeatedNumber!=0)return false;
        else return true;
        
    }
    public void setCustom(Customer cs) {
        this.custom = Optional.of(cs);
    }
    
}
