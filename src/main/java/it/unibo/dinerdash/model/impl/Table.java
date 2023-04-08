package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.model.api.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread
 */
public class Table extends GameEntityImpl {
    
    private int tableNumber;
    private Optional<Customer> customer;
    private TableState state;
    private int SeatedPeople;



    public Table(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int tableNumber) {
        super(coordinates, size);
        this.tableNumber = tableNumber;
        this.SeatedPeople =0;
       
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
        this.customer = Optional.of(cs);
    }

    public Customer getCustomer(){
        return this.customer.get();
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
