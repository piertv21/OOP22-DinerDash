package it.unibo.dinerdash.controller.impl;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.impl.Customer;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.GameView;

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
    }

    @Override
    public void start() {
        //TO-DO
        this.gamePanel = this.view.getGamePanel();
        this.startSpawnTimer();  //starts customers spawn
    }

    @Override
    public void restart() {        
        this.model.restart();
        this.gamePanel.init();
    }

    @Override
    public void quit() {
        this.view.quit();
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

    @Override
    public void getFrameSize(Dimension dimension) {
        System.out.println(dimension);
    }

    @Override
    public void resizeEntities() {
        //TODO
    }

    private void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    public void startSpawnTimer() {
        spawnTime.schedule(custumCreation_Trd, 2000, 6000);                                //avvio la creazione programmata  dei clienti
    }

    TimerTask custumCreation_Trd = new TimerTask() {
        @Override
        public void run() { 
          // addCustomer();          // thread che ogni 6 secondi chiama il metodo per creare un cliente  
        }
    };

    public LinkedList<Customer> getSittedCustomList(){             
        return this.model.getCustomers();
    }

}
