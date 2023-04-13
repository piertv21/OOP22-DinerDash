package it.unibo.dinerdash.controller.impl;

import java.util.LinkedList;
import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.engine.api.GameLoop;
import it.unibo.dinerdash.engine.impl.GameLoopImpl;
import it.unibo.dinerdash.model.api.GameState;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.impl.Customer;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.GameTimer;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.GameView;

public class ControllerImpl implements Controller {

    private Model model;
    private View view;
    private GameView gameView;
    private GameLoop gameLoop;
    private GameTimer gameTimer;
    
    public ControllerImpl() {
        this.model = new ModelImpl(this);
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

        this.gameLoop = new GameLoopImpl(model, this);
        this.gameLoop.start();

        this.gameTimer = new GameTimer(this.model);
        gameTimer.startTimer();
    }

    @Override
    public void restart() {        
        this.model.restart();
        this.gameView.init();
        this.gameTimer.restartTimer();
        this.updateView();
    }

    @Override
    public void pause() {
        this.model.setGameState(GameState.PAUSED);
        this.gameTimer.pauseTimer();
    }

    @Override
    public void resume() {
        this.model.setGameState(GameState.RUNNING);
        this.gameTimer.resumeTimer();
    }

    @Override
    public void quit() {
        this.view.quit();
        System.exit(0);
    }

    @Override
    public void syncChanges() {
        //TODO Sincronizza stato liste view con quelle model

        // Chiamare solo se ci son cambiamenti
        this.updateView();
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
        // this.gameView.addCustomerViewable(model.getRandomNumber());  TODO Prendi numero da molteplicità model
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
    public void timeIsChanged() {
        this.updateView();
    }

    private void updateView() {
        this.gameView.render();
    }

    @Override
    public void resizeEntities() {
        //TODO        QUI CHIAMO I METODI DELLA CLASSE RESIZE
       // model.firstLinePosition= model.resizeLog.updateFirstPos(model.firstLinePosition,(int)this.model.restaurantSize.getHeight(), (int)this.model.restaurantSize.getWidth());
    }

    private void init() {
        //TODO
    }

    @Override
    public String convertToMinutesAndSeconds(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        String formattedTime = String.format("%d:%02d", minutes, remainingSeconds);
        return formattedTime;
    }

    public LinkedList<Customer> getSittedCustomList(){             
        return this.model.getCustomers();
    }

    private void updateListPosition() {         //aggiorno le posizioni dei clienti nella lista della view  SARA DA CHIAMARE OGNI VOLTA PRIMA DI STAMPARE LE IMMAGINI
        int p=0;
        for(var cus: model.getCustomers()) {
                this.gameView.getViewableCustomersList().get(p).update(cus);
                p++;
                // BISOGNA CHIAMARE UN METODO NELLA VIEW CHE CAMBIA L'IMMAGINE DEL TAVOLO ,
                // AGGIUNGI METODO CHE AGGIORNA POSIZIONI TAVOLI ,E CONTROLLO SE IL CLIENTE è IN THINKING ,POI SE IL NUMERO DI CLIENTI SEDUTI ED LA MOLTEPLIC DEL CLIENTE
                // ALLEGATO SONO UGUALI,  SE NON LO SONO CAMBIA L'IMMAGINE DEL TAVOLO
        }
        
    }

    @Override
    public void setWaitresDestination(Pair<Integer,Integer> dest) {
        this.model.setWaitressTableDestination(dest);
    }

}
