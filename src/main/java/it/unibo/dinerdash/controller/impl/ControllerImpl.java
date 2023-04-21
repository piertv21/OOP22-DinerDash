package it.unibo.dinerdash.controller.impl;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.engine.api.GameLoop;
import it.unibo.dinerdash.engine.impl.GameLoopImpl;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.Chef;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.GameEntities.Waitress;
import it.unibo.dinerdash.model.api.States.GameState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.api.GameTimer;
import it.unibo.dinerdash.utility.impl.GameTimerImpl;
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

        this.gameTimer = new GameTimerImpl(this.model);
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
        // TODO Poi verrà rimosso questo metodo
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



    

    @Override   // TODO Rimuovi (update già fatti in model di tutto)
    public void updateListPosition() { // aggiorno le posizioni dei clienti nella lista della view SARA DA CHIAMARE
                                       // OGNI VOLTA PRIMA DI STAMPARE LE IMMAGINI
        int p = 0;
        for (final var client : model.getCustomers()) {
            this.gameView.updateCustomersViewable(p,
                    client.getPosition(),
                    client.isActive(), client.getCustomerPatience());
            p++;
        }
        p = 0;
        this.gameView.updateWaitressViewable(model.getWaitress().getPosition(), model.getWaitress().getOrdersNumber());

        for (var table : model.getTable()) {
            this.gameView.updateTablesViewable(p, table.getPeopleSeatedNumber(), table.getStateInText());
            p++;
        }
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
    public void addTableToView(Pair<Integer, Integer> pos, int tableNum, Pair<Integer, Integer> size) { //TODO Rimuovi
        this.gameView.addTableViewable(pos, size,0,"" );
    }








    // NUOVI METODI DA IMPLEMENTARE ------------------------------------------------------------------

    @Override
    public void addCustomerToView(Customer customer) {
        this.gameView.addCustomerViewable(customer.getPosition(), customer.getSize(),
        customer.isActive(), customer.getCustomerCount(), customer.getCustomerPatience());
    }

    @Override
    public void updateCustomersInView(int index, Customer customer) {
        this.gameView.updateCustomersViewable(index, customer.getPosition(),
         customer.isActive(), customer.getCustomerPatience());
    }

    @Override
    public void removeCustomerInView(int index) {
        this.gameView.removeCustomerViewable(index);
    }

    @Override
    public void addChefToView(Chef chef) {
        this.gameView.addChefViewable(chef.getPosition(), chef.getSize(), chef.isActive());
    }

    @Override
    public void updateChefInView(Chef chef) {
        this.gameView.updateChefViewable(chef.isActive());
    }

    @Override
    public void addWaitressToView(Waitress waitress) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addWaitressToView'");
    }

    @Override
    public void updateWaitressInView(Waitress waitress) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateWaitressInView'");
    }

    @Override
    public void addDishToView(Dish dish) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addDishToView'");
    }

    @Override
    public void updateDishesInView(int index, Dish dish) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDishesInView'");
    }

    @Override
    public void deleteDishInView(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteDishInView'");
    }

    @Override
    public void addTableToView(Table table) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTableToView'");
    }

    @Override
    public void updateTablesInView(int index, Table table) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTablesInView'");
    }

}
