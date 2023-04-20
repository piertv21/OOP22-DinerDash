package it.unibo.dinerdash.controller.impl;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.engine.api.GameLoop;
import it.unibo.dinerdash.engine.impl.GameLoopImpl;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.States.GameState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.GameTimer;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.View;

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
        this.gameView.clear();
        this.model.restart();
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
        // TODO Sincronizza stato liste view con quelle model

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
    public void addCustomer( final Pair<Integer, Integer> position,
        final int customNumber,
        final Pair<Integer, Integer> size ,int maxPatience) {
        this.gameView.addCustomerViewable(position, size, true, customNumber, maxPatience);
    }

    @Override
    public void removeCustomer(final int indexValue) {
        this.gameView.removeCustomerViewable(indexValue);
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
        this.updateListPosition();
        this.gameView.render();
    }

    @Override
    public void resizeEntities() {
        // TODO QUI CHIAMO I METODI DELLA CLASSE RESIZE
        // model.firstLinePosition=
        // model.resizeLog.updateFirstPos(model.firstLinePosition,(int)this.model.restaurantSize.getHeight(),
        // (int)this.model.restaurantSize.getWidth());
    }

    private void init() {
        // TODO
    }

    @Override
    public String convertToMinutesAndSeconds(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        String formattedTime = String.format("%d:%02d", minutes, remainingSeconds);
        return formattedTime;
    }

    @Override
    public void updateListPosition() { // aggiorno le posizioni dei clienti nella lista della view SARA DA CHIAMARE
                                       // OGNI VOLTA PRIMA DI STAMPARE LE IMMAGINI
        int p = 0;
        for (final var client : model.getCustomers()) {
            this.gameView.updateCustomersViewable(p,
                client.getPosition(),
                client.isActive(), client.getCustomerPatience());
            p++;
        }
        p =0 ;
        /*this.gameView.getViewableWaitress().update(model.getWaitress());

        for (var table : model.getTable()) {
            this.gameView.getViewableTable().get(p).update(table);
            p++;
        }*/
    }

    @Override
    public void setWaitressDestination(Pair<Integer, Integer> dest) {
        this.model.setWaitressTableDestination(dest);
    }

    @Override
    public void callWaitress(int indexList, String s, Pair<Integer, Integer> position) {
        model.setWaiterssInfo(indexList, s, position);
    }

    @Override
    public void addTable(Pair<Integer, Integer> pos, int tableNum, Pair<Integer, Integer> size) {
        //this.gameView.adddTableViewable(pos, tableNum, size);
    }

}
