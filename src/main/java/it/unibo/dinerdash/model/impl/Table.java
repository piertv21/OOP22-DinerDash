package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread
 */
public class Table extends GameEntityImpl {
    
    private int peopleSeatedNumber; // Equals to customers number
    private TableState state;

    private enum TableState {
        THINKING,
        ORDERING,
        WAITING_MEAL,
        EATING,
        WANTING_TO_PAY,
        EMPTY
    }

    public Table(Pair<Integer, Integer> coordinates) {
        super(coordinates);
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
    
}
