package it.unibo.dinerdash.model.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntity.CustomerStati;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class ModelImpl implements Model {

    private static final int MAX_CUSTOMERS_THAT_CAN_LEAVE = 10;
    private static final double CHEF_SPEED_MULTIPLIER = 1.2;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5;
    private static final double PROFIT_MULTIPLIER = 2.0;
    private static final int MAX_PLAYTIME = 60*5;
    
    private int coins;
    private int remainingTime;
    private int customersWhoLeft;
    private LinkedList<Customer> customers;   // clienti
    private LinkedList<Customer> customersInLine;   // clienti in fila
    //TODO GIA' in customersInLine private LinkedList<Pair<Integer,Integer>>customers_LinePosition = new LinkedList<>();                           //coordinate della gente in fila
    private HashMap<Table, Optional<Customer>> tables;         // tavoli con eventuali clienti
    private LinkedList<Dish> dishes;          // piatti

    public ModelImpl() {
        this.tables = new HashMap<>();
        this.dishes = new LinkedList<>();
        this.customers = new LinkedList<>();
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
    public void quit() {
        this.init();
    }

    @Override
    public void addCustomer(int num) {
        this.initializeTablesMap(); //prova   poi si dovra togliere da qui
        if(this.gameOver()) {
            // TO DO: STOP GAME
        }
        var position = new Pair<>(30, 10); 
        if(thereAreAvaibleTables()){
            this.customers.add(new Customer(position, this)); 
            AssegnoTavolo();
        }else{
            
            // AssegnoPostoFila();
        }
    }

    private void init() {
        this.coins = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
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
            x.setDestination(Optional.ofNullable(
                tables.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(Optional.empty()))   
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null).getPosition()
            ));
        });
      //  System.out.println(customers.getFirst().getDestination().get());      testa se funziona
    } 

    public void assegnoPostoFila() {
       long posto= customers.stream().filter(p->p.getState().equals(CustomerStati.LINE)).count();
    }


    public HashMap<Table, Optional<Customer>> getTables() {
        return this.tables;
    }

    public LinkedList<Customer> getCustomersLineList() {
        return this.customersInLine;
    }

    public boolean thereAreAvaibleTables() {
        return this.tables.values().stream().anyMatch(customers->customers.isEmpty());
    }

    private void initializeTablesMap() {
        this.tables.put(new Table(new Pair<Integer,Integer>(100, 200), 12), Optional.empty());  //provo a mettere un tavolo
    }
}
