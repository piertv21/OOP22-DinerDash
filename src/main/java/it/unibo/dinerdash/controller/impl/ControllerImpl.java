package it.unibo.dinerdash.controller.impl;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.impl.Customer;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.ViewImpl;

public class ControllerImpl implements Controller {

    private ModelImpl model;
    private View view;
    private GameView gamePanel;
    private Random random; // used to create customers
    Timer spawnTime = new Timer();                             //timer to make spawn customers
    
    public ControllerImpl() {
        this.random = new Random();
        this.model = new ModelImpl();
    }
    
    @Override
    public void setView(View view) {
        this.view = view;
        this.gamePanel = (GameView) this.view.getGamePanel();
    }

    @Override
    public void start() {
        //TO-DO
        this.startSpawnTimer();  //starts customers spawn
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @Override
    public void restart() {        
        this.model.restart();
    }

    @Override
    public void quit() {
        this.view.closeWindow();

        // TO-DO: Remove entity images, eg. image1.flush();

        System.exit(0);
    }

    @Override
    public int getCoins() {
        return this.model.getCoins();
    }

    @Override
    public int getRemainingTime() {
        return this.model.getRemainingTime();
    }

    @Override
    public boolean gameOver() {
        return this.model.gameOver();
    }

    @Override
    public void addCustomer() {
        this.model.addCustomer(random.nextInt(4)+1);  
    }

    public void startSpawnTimer() {
        spawnTime.schedule(custumCreation_Trd, 2000, 6000);                                //avvio la creazione programmata  dei clienti
    }

    TimerTask custumCreation_Trd = new TimerTask() {
        @Override
        public void run() { 
           addCustomer();          // thread che ogni 6 secondi chiama il metodo per creare un cliente  
        }
    };

    public LinkedList<Customer> getSittedCustomList(){             
        return this.model.getCustomersList();
    }

}
