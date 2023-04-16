package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import it.unibo.dinerdash.model.api.AbstractGameEntity;
import it.unibo.dinerdash.model.api.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread
 */
public class Table extends AbstractGameEntity {
    
    private int tableNumber;
    private Optional<Customer> customer;
    private TableState state;
    private int SeatedPeople;

    public Table(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int i) {
        super(coordinates, size);
        this.tableNumber = i;
        this.SeatedPeople =0;
        this.customer=Optional.empty();
        this.state=TableState.EMPTY;
       
    }

    public void setState(TableState TableState){                                     
        this.state = TableState;
    }

    public TableState getState(){                                     
        return this.state;
    }

    public void setCustom(Optional<Customer> cs) {
        this.customer = cs;
    }

    public Optional<Customer> getCustomer(){
        return this.customer;
    }

    public int getTableNumber(){
        return this.tableNumber;
    }

    public int setSeatedPeople(int sppl){
         return this.SeatedPeople = sppl;
    }

    public int getPeopleSeatedNumber() {
       return this.SeatedPeople;
    }

    
}
