package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Table is not a thread
 */
public class Table extends GameEntityImpl {
    
    private int tableNumber;
    private Optional<Customer> custom;  // Contiene numero persone altrimenti 0
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
        // TODO
        return 0;
    }

    public void setState(TableState TableState){                                     
        this.state = TableState;
    }

    public TableState getState(){                                     
        return this.state;
    }

    public boolean isFree() {
        return this.getPeopleSeatedNumber() == 0;        
    }

    public void setCustom(Customer cs) {
        this.custom = Optional.of(cs);
    }
    
}
