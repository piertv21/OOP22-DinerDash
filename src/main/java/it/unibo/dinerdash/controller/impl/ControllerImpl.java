package it.unibo.dinerdash.controller.impl;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.impl.Customer;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.GameTimer;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.GameView;

public class ControllerImpl implements Controller {

    private ModelImpl model;
    private View view;
    private GameView gameView;
    private GameLoopImpl gameLoop;
    private GameTimer gameTimer;
    Timer spawnTime = new Timer();                             //TODO Rimuovi e metti in model.update() timer to make spawn customers
    
    public ControllerImpl() {
        this.model = new ModelImpl();
    }
    
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void start(GameView gameView) {
        this.gameView = gameView;
        this.init();
        this.model.start();

        this.gameLoop = new GameLoopImpl(model, this.gameView);
        this.gameLoop.start();

        this.gameTimer = new GameTimer(this.model);
        gameTimer.startTimer();

        // this.startSpawnTimer();  //TODO Rimuovi starts customers spawn
    }

    @Override
    public void restart() {        
        this.model.restart();
        this.gameView.init();
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
        this.model.addCustomer();
        this.gameView.getViewableCustomersList().add(new GameEntityViewable(null, new Pair<>(0, 0), null));
        this.gameView.addCustomerViewable(model.getRandomNumber());  
    }

    @Override
    public int getRestaurantWidth() {
        return this.model.getWidth();
    }

    @Override
    public int getRestaurantHeight() {
        return this.model.getHeight();
    }

    @Override
    public void resizeEntities() {
        //TODO        QUI CHIAMO I METODI DELLA CLASSE RESIZE
       // model.firstLinePosition= model.resizeLog.updateFirstPos(model.firstLinePosition,(int)this.model.restaurantSize.getHeight(), (int)this.model.restaurantSize.getWidth());
    }

    private void init() {
        //TODO
    }

    public String convertToMinutesAndSeconds(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        String formattedTime = String.format("%d:%02d", minutes, remainingSeconds);
        return formattedTime;
    }

    private void startSpawnTimer() {
        spawnTime.schedule(custumCreation_Trd, 2000, 6000);                                //avvio la creazione programmata  dei clienti
    }

    TimerTask custumCreation_Trd = new TimerTask() {
        @Override
        public void run() { 
          // addCustomer();          //TODO Rimuovi thread che ogni 6 secondi chiama il metodo per creare un cliente  
        }
    };

    public LinkedList<Customer> getSittedCustomList(){             
        return this.model.getCustomers();
    }

    private void updateListPosition() {         //aggiorno le posizioni dei clienti nella lista della view  SARA DA CHIAMARE OGNI VOLTA PRIMA DI STAMPARE LE IMMAGINI
        int p=0;
        for(var cus: model.getCustomers()) {
                this.gameView.getViewableCustomersList().get(p).update(cus);
                if(cus.getState().equals(CustomerState.THINKING)) {
                    //int tableNmb= this.getHashMapkey(model.getTables(), cus).getTableNumber();
                    //this.gamePanel.getTablesList().get(tableNmb-1).setImg(cus.getCustomerMultiplicity());
                }
                p++;
                // BISOGNA CHIAMARE UN METODO NELLA VIEW CHE CAMBIA L'IMMAGINE DEL TAVOLO ,
                // AGGIUNGI METODO CHE AGGIORNA POSIZIONI TAVOLI ,E CONTROLLO SE IL CLIENTE è IN THINKING ,POI SE IL NUMERO DI CLIENTI SEDUTI ED LA MOLTEPLIC DEL CLIENTE
                // ALLEGATO SONO UGUALI,  SE NON LO SONO CAMBIA L'IMMAGINE DEL TAVOLO
        }
    }

}
