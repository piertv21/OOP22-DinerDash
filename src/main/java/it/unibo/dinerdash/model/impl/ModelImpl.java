package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.api.GameState;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class ModelImpl implements Model {

    private static final int RESTAURANT_WIDTH = 1280;
    private static final int RESTAURANT_HEIGHT = 720;

    private static final int MAX_CUSTOMERS_THAT_CAN_LEAVE = 10;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5;
    private static final double PROFIT_MULTIPLIER = 2.0;
    private static final int MAX_PLAYTIME = 5 * 60;

    private static final int SPACE_BETWEEN_LINE_PEOPLE = 25;
    private static final int TABLES = 4;
    private static final double STARTING_TABLE_REL_X = 0.3;
    private static final double STARTING_TABLE_REL_Y = 0.3;
    private static final int TABLES_PADDING = 250;

    private static final int STARTING_X = 0;
    private static final int STARTING_Y = 500;
    
    public static final double COUNTERTOP_REL_X = 0.5;
    public static final double COUNTERTOP_REL_Y = 0.2;
    public static final double CHEF_REL_X = 0.55;
    public static final double CHEF_REL_Y = 0.02;
    
    private Pair<Integer,Integer> firstLinePosition;    //TODO Cambia in const
    private Random random; // used to create customers
    private int coins;
    private int remainingTime;
    private int customersWhoLeft;
    private GameState gameState;                                // stato di gioco

    private LinkedList<Customer> customers;                     // clienti
    private LinkedList<Table> tables;                           // tavoli con eventuali clienti
    private Countertop counterTop;                       // bancone con lista piatti
    private Chef chef;
    private Waitress waitress;
    
    private ResizeLogic resizeLog;  //TODO Rimuovi

    public ModelImpl() {
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();

        var counterTopRelPosition = new Pair<>((int) (COUNTERTOP_REL_X * RESTAURANT_WIDTH), (int) (COUNTERTOP_REL_Y * RESTAURANT_HEIGHT));
        this.counterTop = new Countertop(counterTopRelPosition, new Pair<>(0, 0)); //TODO Imposta Size

        this.random = new Random();
    }

    private void init() {
        this.coins = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
        this.gameState = GameState.RUNNING;
        this.firstLinePosition = new Pair<Integer,Integer>((int)(RESTAURANT_WIDTH*0.04),(int) (RESTAURANT_HEIGHT*0.67));  //da modificare poi i numeri

        this.clear();

        
        var startingTableX = (int)(this.firstLinePosition.getX() * STARTING_TABLE_REL_X);
        var startingTableY = (int)(this.firstLinePosition.getY() * STARTING_TABLE_REL_Y);
        this.firstLinePosition=new Pair<Integer,Integer>((int)(this.firstLinePosition.getX()*0.04), 
                                                        (int)(this.firstLinePosition.getY()*0.67));

        IntStream.range(0, TABLES).forEach(i -> {
            var j = i % (TABLES / 2);
            var x = startingTableX + (j * TABLES_PADDING);
            var y = startingTableY + (i * TABLES_PADDING);
            var position = new Pair<>(x, y);
            this.tables.add(new Table(position,new Pair<>(0, 0), i + 1));  
            this.customers.add(new Customer(position, new Pair<>(0, 0), this));
        });
        
    }

    private void clear() {
        this.customers.clear();
        this.tables.clear();
        this.counterTop.clear();
    }

    @Override
    public int getWidth() {
        return RESTAURANT_WIDTH;
    }

    @Override
    public int getHeight() {
        return RESTAURANT_HEIGHT;
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
    public void addOrder(Dish dish) {
        this.counterTop.addDish(dish);
    }

    @Override
    public void addCustomer() {
        if(this.gameOver()) {
            this.stop();
        }
        var position = new Pair<>(STARTING_X,  STARTING_Y); 
        this.customers.add(new Customer(position, new Pair<>(0, 0), this)); // TODO Imposta size
        if(thereAreAvaibleTables()) {
            AssegnoTavolo( this.customers.getLast());
        } else {
            customers.getLast().setState(CustomerState.LINE);
            assegnoPostoFila(this.customers.getLast()); 
        }
    }

    public void update(long elapsedUpdateTime) {
        if(!this.gameOver()) {
            //this.remainingTime -= elapsedUpdateTime;
            
            //this.chef.update(elapsedUpdateTime);
            /*
                waitress.update(elapsedUpdateTime); //aggiornamento posizione + altro
                for (Customer customer : customers) { //aggiornamento posizione + altro
                    customer.update(elapsedUpdateTime);
                }
            */
        } else {
            this.stop();
        }
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void decrementRemainingTime() {
        this.remainingTime--;
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

    public void AssegnoTavolo(Customer cus) {                              //quando non ci sono più tavoli liberi non vengono piu assegnati tavoli nuovi
        cus.setDestination(Optional.ofNullable(
            this.tables.stream()
            .filter(tav->tav.isFree())
            .findFirst()
            .get()
            .getPosition()
        ));                                
        tables.stream()
        .filter(entry -> entry.getPosition().equals(cus.getDestination().get()))   
        .findFirst()
        .orElse(null)
        .setCustom(cus); 
    } 

    public void assegnoPostoFila(Customer cus) {
       int inLineCustm= (int)customers.stream().filter(p->p.getState().equals(CustomerState.LINE)).count();
       if(inLineCustm!=1) {
        cus.setPosition(new Pair<Integer,Integer>(firstLinePosition.getX(),firstLinePosition.getY()-(inLineCustm*SPACE_BETWEEN_LINE_PEOPLE) ));
        }
       else cus.setPosition(this.firstLinePosition);    
       cus.start();
    }

    public LinkedList<Table> getTable(){
        return this.tables;
    }

    public boolean thereAreAvaibleTables() {
       return this.tables.stream().anyMatch(tab->tab.isFree());  
    }
    
    public int getRandomNumber(){
        return this.random.nextInt(4)+1;
    }
    
    public Countertop getCounterTop() {
        return counterTop;
    }

    public void leaveRestaurant(Customer cus){
        synchronized(this.customers){
        this.customers.remove(cus);
        this.customerLeft();
                   // forse saranno da invertire
        this.customers.stream()
        .filter(p->p.getState().equals(CustomerState.LINE)).forEach((p)->{
            p.setPosition(new Pair<>(p.getPosition().getX(), p.getPosition().getY()+25));
        });   
    }     
    }

    public boolean checkFreeTables(Customer cus){
        synchronized(this.customers){
        if(this.customers.stream()          // se questo cliente è il primo della fila
        .filter(p->p.getState().equals(CustomerState.LINE)).findFirst().get().equals(cus)){
           return this.thereAreAvaibleTables();
        }
        return false;
    } 
    }

    public GameState getGameState() {
        return this.gameState;
    }

}
