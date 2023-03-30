package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class Model {

    private static final int MAX_CUSTOMERS_THAT_CAN_LEAVE = 10;
    private static final int MAX_EMPTY_TABLES = 4;
    private static final double CHEF_SPEED_MULTIPLIER = 1.2;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5;
    private static final double PROFIT_MULTIPLIER = 2.0;
    private static final int MAX_PLAYTIME = 60*5;
    
    private int emptyTables;
    private int coins;
    private int remainingTime;
    private int customersWhoLeft;
    private LinkedList<Customer> sittedCustomersList;   // clienti (vengono rappresentati correttamente in base alla posizione + state che hanno)
    private LinkedList<Customer> line_CustomersList;   // clienti in fila  (vengono rappresentati correttamente in base alla posizione + state che hanno)
    private LinkedList<Pair<Integer,Integer>>customers_LinePosition;                           //coordinate della gente in fila
    private LinkedList<Table> tables;         // tavoli (vengono rappresentati correttamente in base alla posizione + icona che hanno)
    private LinkedList<Dish> dishes;          // lista piatti pronti dello chef

    public Model() {
        this.sittedCustomersList = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.dishes = new LinkedList<>();
        this.customers_LinePosition=new LinkedList<>();
        this.init();
    }
    
    public void serveDish() {
        //TO-DO
    }

    public void insertOrder(int tableNumber) {
        var position = new Pair<>(0, 0);
        this.dishes.add(new Dish(position, tableNumber));
        //TO-DO
    }

    public boolean gameOver() {
        return this.remainingTime == 0 ||
            this.customersWhoLeft == MAX_CUSTOMERS_THAT_CAN_LEAVE;
    }

    private void init() {
        this.coins = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
        this.emptyTables = MAX_EMPTY_TABLES;
        this.sittedCustomersList.clear();
        this.tables.clear();
        this.dishes.clear();
    }

    public void restart() {
        this.init();
    }

    public int getMaxPlaytime() {
        return MAX_PLAYTIME;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getRemainingTime() {
        return this.remainingTime;
    }

    public int getCustomersWhoLeft() {
        return this.customersWhoLeft;
    }

    public void customerLeft() {
        if(!this.gameOver()) {
            this.customersWhoLeft++;
        }
    }

    public void addCustomer(int num) {
        if(this.gameOver()) {
            // TO DO: STOP GAME
        }
        var position = new Pair<>(30, 10); 
            if(this.emptyTables!=0){
                this.sittedCustomersList.add(new Customer(position, null, line_CustomersList, customers_LinePosition)); 
                AssegnoTavolo();
            }else{
                
               // AssegnoPostoFila();
            } 
    }

    public LinkedList<Customer> getCustomersList() {
        return this.sittedCustomersList;
    }

    public void AssegnoTavolo(){                              //quando non ci sono più tavoli liberi non vengono piu assegnati tavoli nuovi
        sittedCustomersList.stream().filter(p ->p.getDestination().equals(Optional.empty()))         //prendo dalla lista di clienti tutti quelli senza un posto assegnato
        .forEach((var x)->{                
            x.setDestination(Optional.ofNullable(tables.stream()
            .filter(p ->p.isAvailable())
             .reduce((first, second) -> first)
             .orElse(null).getPosition())) ;  
            this.emptyTables--;
            });
}

public LinkedList<Table> getTablesList(){
    return this.tables;
}

}
