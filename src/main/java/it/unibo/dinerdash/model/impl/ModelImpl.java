package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.api.GameState;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.TableState;
import it.unibo.dinerdash.model.api.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class ModelImpl implements Model {

    private static final int RESTAURANT_WIDTH = 1280;
    private static final int RESTAURANT_HEIGHT = 720;

    private static double FIRST_LINE_POS_REL_X = 0.04 * RESTAURANT_WIDTH;
    private static double FIRST_LINE_POS_REL_Y = 0.67 * RESTAURANT_HEIGHT;

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
    private static final int HEIGHT_SIZE_CUST = 200;
    private static final int WIDTH_SIZE_CUST = 150;
    
    private static final double CHEF_REL_X = 0.55;
    private static final double CHEF_REL_Y = 0.02;
    private static final double CHEF_REL_WIDTH = 0.05;
    private static final double CHEF_REL_HEIGHT = 0.02;

    private static final double DISH_REL_WIDTH = 0.05;
    private static final double DISH_REL_HEIGHT = 0.02;

    private long lastCustomerTimeCreation;
    private static final long CUSTOMERS_CREATION_TIME = 6000000000L;

    private int coins;
    private int remainingTime;
    private int customersWhoLeft;
    private GameState gameState;                                // stato di gioco

    private LinkedList<Customer> customers;                     // clienti
    private LinkedList<Table> tables;                           // tavoli con eventuali clienti
    private Countertop counterTop;                       // bancone con lista piatti
    private Chef chef;
    private Waitress waitress;    

    public ModelImpl() {
        this.customers = new LinkedList<>();
        this.tables = new LinkedList<>();        
        this.counterTop = new Countertop(this);
    }

    private void init() {
        this.coins = 0;
        this.remainingTime = MAX_PLAYTIME;
        this.customersWhoLeft = 0;
        this.clear();

        // Tavoli
      // Tavoli
      var x = RESTAURANT_WIDTH - (RESTAURANT_WIDTH/6);
      var y = RESTAURANT_HEIGHT - (RESTAURANT_HEIGHT/2);
      var position = new Pair<>(x, y);
      this.tables.add(new Table(position,new Pair<>(0, 0), 1)); 

      x = RESTAURANT_WIDTH - (RESTAURANT_WIDTH/6);
      y = RESTAURANT_HEIGHT - (RESTAURANT_HEIGHT/4);
      position = new Pair<>(x, y);
      this.tables.add(new Table(position,new Pair<>(0, 0), 2)); 

      x = RESTAURANT_WIDTH - 3*(RESTAURANT_WIDTH/6);
      y = RESTAURANT_HEIGHT - (RESTAURANT_HEIGHT/2);
      position = new Pair<>(x, y);
      this.tables.add(new Table(position,new Pair<>(0, 0), 3));

      x = RESTAURANT_WIDTH - 3*(RESTAURANT_WIDTH/6);
      y = RESTAURANT_HEIGHT - (RESTAURANT_HEIGHT/4);
      position = new Pair<>(x, y);
      this.tables.add(new Table(position,new Pair<>(0, 0), 4));

        // Chef
        var chefPosition = new Pair<>((int)(CHEF_REL_X * RESTAURANT_WIDTH), (int)(CHEF_REL_Y * RESTAURANT_HEIGHT));
        var chefSize = new Pair<>((int)(CHEF_REL_WIDTH * RESTAURANT_WIDTH), (int)(CHEF_REL_HEIGHT * RESTAURANT_HEIGHT));
        this.chef = new Chef(chefPosition, chefSize, this);
        
        this.lastCustomerTimeCreation = System.nanoTime(); 
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
        this.gameState = GameState.RUNNING;
        //TODO Check se serve altro
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
        this.start();
    }

    @Override
    public void quit() {
        this.clear();
        //TODO Check se serve altro
    }

    @Override
    public void sendOrder(int tableNumber) {                       //mando l'ordine al bancone
        this.counterTop.addOrder(tableNumber);
    }

    @Override
    public void serveDish() {
        //TODO
    }

    @Override
    public void addCustomer() {
        if(this.gameOver()) {
            this.stop();
        }
        var position = new Pair<>(STARTING_X,  STARTING_Y); 
        int customersMolteplicity=(int) (Math.random()* (4)) + 1;
        this.customers.add(new Customer(position, new Pair<>(WIDTH_SIZE_CUST, HEIGHT_SIZE_CUST), this,customersMolteplicity)); 
        if(thereAreAvaibleTables()) {
            AssegnoTavolo( this.customers.getLast());
        } else {
            customers.getLast().setState(CustomerState.LINE);
            assegnoPostoFila(this.customers.getLast()); 
        }
    }

    public void update(long elapsedUpdateTime) {
        if(!this.gameOver()) {  
            if(System.nanoTime()>=this.lastCustomerTimeCreation+CUSTOMERS_CREATION_TIME){
                System.out.println("creo customer");
                //this.tables.forEach(t->System.out.println(t.getPosition()));    DA TOGLIERE
                this.addCustomer();
                this.lastCustomerTimeCreation =System.nanoTime(); 
            }        
            this.chef.update();
            var customIterator= this.customers.iterator();
            while(customIterator.hasNext()){
                customIterator.next().update(elapsedUpdateTime);
        } 
            
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
        var tab = tables.stream()
        .filter(entry -> entry.getPosition().equals(cus.getDestination().get()))   
        .findFirst()
        .orElse(null);
        tab.setCustom(cus);
        tab.setOccupy();
    } 

    public void assegnoPostoFila(Customer cus) {
       int inLineCustm= (int)customers.stream().filter(p->p.getState().equals(CustomerState.LINE)).count();
       if(inLineCustm!=1) {
        cus.setPosition(new Pair<Integer,Integer>((int)FIRST_LINE_POS_REL_X,(int)((FIRST_LINE_POS_REL_Y)-(inLineCustm*SPACE_BETWEEN_LINE_PEOPLE)) ));
        }
       else cus.setPosition(new Pair<Integer,Integer>((int)FIRST_LINE_POS_REL_X,(int)FIRST_LINE_POS_REL_Y));    
    }

    public LinkedList<Table> getTable(){
        return this.tables;
    }

    public boolean thereAreAvaibleTables() {
       return this.tables.stream().anyMatch(tab->tab.isFree());  
    }
    
    public Countertop getCounterTop() {
        return counterTop;
    }

    public void leaveRestaurant(Customer cus){
        synchronized(this.customers){  
        this.customers.remove(cus);
        this.customerLeft();
        this.customers.stream()
        .filter(p->p.getState().equals(CustomerState.LINE)).forEach((p)->{
            p.setPosition(new Pair<>(p.getPosition().getX(), p.getPosition().getY()+25));
        }); 
    }     
    }

    public boolean checkFreeTables(Customer cus){   //i clientei in fila controllano se si è liberato un tavolo
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

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setWaitressTableDestination(Pair<Integer,Integer> dest){    //assegno la destinazione del tavo alla cameriera
        if(!(this.waitress.getState().equals(WaitressState.CALLING))) {
            this.waitress.setDestination(Optional.of(dest));
            this.waitress.setState(WaitressState.CALLING);
        }
    }
    
    public Table getTablefromPositon(Pair<Integer,Integer> pos){           // ottengo il tavolo data la posizione
       return this.tables.stream().filter(t->t.getPosition().equals(pos)).findFirst().get();
    }

    public void setTableState(TableState state,int numberTable) {    // pongo il tavolo in modalito ordering a gli assegno il numero di clienti
        this.tables.get(numberTable-1).setState(state);
    }
    public void setTableCustomers(int customersMolteplicity,int numberTable) {
        this.tables.get(numberTable-1).setSeatedPeople(customersMolteplicity);
    }
    


}
