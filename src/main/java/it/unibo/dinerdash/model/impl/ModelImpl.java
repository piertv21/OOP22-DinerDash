package it.unibo.dinerdash.model.impl;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.api.GameState;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class ModelImpl implements Model {

    private static final int MAX_CUSTOMERS_THAT_CAN_LEAVE = 10;
    private static final double CHEF_SPEED_MULTIPLIER = 1.2;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5;
    private static final double PROFIT_MULTIPLIER = 2.0;
    private static final int MAX_PLAYTIME = 60*5;
    private static final int SPACE_BETWEEN_LINE_PEOPLE = 25;
    private static final int TABLES = 4;
    private static final double STARTING_TABLE_RELATIVE_X = 0.3;
    private static final double STARTING_TABLE_RELATIVE_Y = 0.3;
    private static final int TABLES_PADDING = 250;
    private static final int STARTING_X = 0;
    private static final int STARTING_Y = 500;
    
    public Pair<Integer,Integer> firstLinePosition;
    private Random random; // used to create customers
    private int coins;
    private int remainingTime;
    private int customersWhoLeft;
    private LinkedList<Customer> customers;                     // clienti
    private LinkedList<Table> tables;                           // tavoli con eventuali clienti
    private LinkedList<Dish> dishes;                            // piatti
    private Dimension restaurantSize;                           // size aggiornata finestra
    private GameState gameState;                                // stato di gioco
    private ResizeLogic resizeLog;

    public ModelImpl() {
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.dishes = new LinkedList<>();
        this.random = new Random();
    }

    @Override
    public void setRestaurantSize(Dimension dimension) {
        this.restaurantSize = dimension;
    }

    private void init() {
        this.coins = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
        this.gameState = GameState.RUNNING;       
        this.resizeLog=new ResizeLogic(this.restaurantSize.getHeight(),this.restaurantSize.getWidth());   // creo il resizeLogic passando le dimensioni schermo
        this.clear();

        var startingTableX = (int)(this.restaurantSize.getWidth() * STARTING_TABLE_RELATIVE_X);
        var startingTableY = (int)(this.restaurantSize.getHeight() * STARTING_TABLE_RELATIVE_Y);
        this.firstLinePosition=new Pair<Integer,Integer>((int)(this.restaurantSize.getWidth()*0.04), 
                                                        (int)(this.restaurantSize.getHeight()*0.67));

        IntStream.range(0, TABLES).forEach(i -> {
            var j = i % (TABLES / 2);
            var x = startingTableX + (j * TABLES_PADDING);
            var y = startingTableY + (i * TABLES_PADDING);
            var position = new Pair<>(x, y);
           // this.tables.put(new Table(position, i + 1), Optional.empty());  TODO  DA MODIFICARE!!!
        });
    }

    private void clear() {
        this.customers.clear();
        this.tables.clear();
        this.dishes.clear();
    }

    @Override
    public void start() {
        this.init();

        //TODO
    }

    @Override
    public void pause() {
        this.gameState = GameState.PAUSED;
    }

    @Override
    public void stop() {
        this.gameState = GameState.ENDED;
    }

    @Override
    public boolean gameOver() {
        return this.remainingTime == 0 ||
            this.customersWhoLeft == MAX_CUSTOMERS_THAT_CAN_LEAVE;
    }

    @Override
    public void restart() {
        this.init();
        this.start();
    }

    @Override
    public void quit() {
        this.clear();
    }
    
    @Override
    public void serveDish() {
        //TODO
    }

    @Override
    public void addOrder(int tableNumber) {
        var position = new Pair<>(0, 0);
        this.dishes.add(new Dish(position, tableNumber));
        //TODO
    }

    @Override
    public void addCustomer() {
        if(this.gameOver()) {
            this.stop();
        }
        var position = new Pair<>(STARTING_X,  STARTING_Y); 
        this.customers.add(new Customer(position, this)); 
        if(thereAreAvaibleTables()) {
            AssegnoTavolo();
        } else {
            customers.getLast().setState(CustomerState.LINE);
            assegnoPostoFila(); 
        }
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

    public void customerLeft() {
        if(!this.gameOver()) {
            this.customersWhoLeft++;
        }
    }

    public LinkedList<Customer> getCustomers() {
        return this.customers;
    }

    public void AssegnoTavolo() {                              //quando non ci sono più tavoli liberi non vengono piu assegnati tavoli nuovi
        customers.getLast().setDestination(Optional.ofNullable(
            this.tables.stream()
            .filter(tav->tav.isFree())
            .findFirst()
            .get()
            .getPosition()
        ));                                
        tables.stream()
        .filter(entry -> entry.getPosition().equals(customers.getLast().getDestination().get()))   
        .findFirst()
        .orElse(null)
        .setCustom(customers.getLast()); 
    } 

    public void assegnoPostoFila() {
       int inLineCustm= (int)customers.stream().filter(p->p.getState().equals(CustomerState.LINE)).count();
       if(inLineCustm!=1) {
        customers.getLast().setPosition(new Pair<Integer,Integer>(firstLinePosition.getX(),firstLinePosition.getY()-(inLineCustm*SPACE_BETWEEN_LINE_PEOPLE) ));
        }
       else customers.getLast().setPosition(this.firstLinePosition);    
    }

            /* 
    public HashMap<Table, Optional<Customer>> getTables() {                TODO   DA MODIFICARE
        return this.tables;
    }*/

    public boolean thereAreAvaibleTables() {
        //return this.tables.values().stream().anyMatch(customers->customers.isEmpty());    TODO togliere
       return this.tables.stream().anyMatch(tbl->tbl.isFree());  
    }
      /*                                       TODO  TOGLIERE
    private void initializeTablesMap() {
        this.tables.put(new Table(new Pair<Integer,Integer>(100, 200), 12), Optional.empty());  //provo a mettere un tavolo
    }*/
    public int getRandomNumber(){
        return this.random.nextInt(4)+1;
    }


}
