package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class ModelImpl implements Model {

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
    private LinkedList<Customer> customers;   // clienti
    private LinkedList<Table> tables;         // tavoli
    private LinkedList<Dish> dishes;          // piatti

    public ModelImpl() {
        this.tables = new LinkedList<>();
        this.dishes = new LinkedList<>();
        this.customers=new LinkedList<>();
        this.init();
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
    
    @Override
    public void serveDish() {
        //TO-DO
    }

    @Override
    public void addOrder(int tableNumber) {
        var position = new Pair<>(0, 0);
        this.dishes.add(new Dish(position, tableNumber));
        //TO-DO
    }

    @Override
    public boolean gameOver() {
        return this.remainingTime == 0 ||
            this.customersWhoLeft == MAX_CUSTOMERS_THAT_CAN_LEAVE;
    }

    @Override
    public void restart() {
        this.init();
    }

    @Override
    public void addCustomer(int num) {
        if(this.gameOver()) {
            // TO DO: STOP GAME
        }
        var position = new Pair<>(30, 10); 
            if(this.emptyTables!=0){
                this.customers.add(null); //TODO Aggiungi cliente vero
                AssegnoTavolo();
            }else{
                
               // AssegnoPostoFila();
            } 
    }

    private void init() {
        this.coins = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
        this.emptyTables = MAX_EMPTY_TABLES;
        this.customers.clear();
        this.tables.clear();
        this.dishes.clear();
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

    public LinkedList<Customer> getCustomers() {
        return this.customers;
    }

    public void AssegnoTavolo() {                              //quando non ci sono più tavoli liberi non vengono piu assegnati tavoli nuovi
        customers.stream().filter(p ->p.getDestination().equals(Optional.empty()))         //prendo dalla lista di clienti tutti quelli senza un posto assegnato
        .forEach((var x)->{
            x.setDestination(Optional.ofNullable(tables.stream()
            .filter(p ->p.isAvailable())
            .reduce((first, second) -> first)
            .orElse(null).getPosition()));  
            this.emptyTables--;
            x.setTableNumber();
        });
    }

    public LinkedList<Table> getTablesList() {
        return this.tables;
    }

}
