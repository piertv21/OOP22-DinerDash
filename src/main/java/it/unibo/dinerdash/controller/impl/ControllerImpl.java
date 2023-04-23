package it.unibo.dinerdash.controller.impl;

import java.util.Optional;

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
        this.model.start();

        this.gameLoop = new GameLoopImpl(this.model, this);
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
    public void quitWithoutPlaying() {
        this.view.quit();
        System.exit(0);
    }

    @Override
    public void quit() {
        this.model.clear();
        this.gameView.clear();
        this.quitWithoutPlaying();
    }

    @Override
    public void gameIsEnded() {
        this.gameLoop.stop();
        this.gameTimer.stopTimer();        
        this.view.showGameOverView();
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
	public int getCustomersWhoLeft() {
		return this.model.getCustomersWhoLeft();
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
    public void updateView() {
        this.gameView.render();
    }

    @Override
    public String convertToMinutesAndSeconds(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        String formattedTime = String.format("%d:%02d", minutes, remainingSeconds);
        return formattedTime;
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
        this.gameView.addWaitressViewable(waitress.getPosition(), waitress.getSize(), waitress.isActive(),
                waitress.getOrdersNumber());

    }

    @Override
    public void updateWaitressInView(Waitress waitress) {
        this.gameView.updateWaitressViewable(waitress.getPosition(), waitress.getOrdersNumber());
    }

    @Override
    public void addDishToView(Dish dish) {
        this.gameView.addDishViewable(dish.getPosition(), dish.getSize(), dish.isActive(), dish.getDishNumber());
    }

    @Override
    public void updateDishesInView(int index, Dish dish) {
        this.gameView.updateDishesViewable(index, dish.isActive());
    }

    @Override
    public void deleteDishInView(int index) {
        this.gameView.deleteDishViewable(index);
    }

    @Override
    public void addTableToView(Table table) {
        this.gameView.addTableViewable(table.getPosition(), table.getSize(), table.getPeopleSeatedNumber(), "");
    }

    @Override
    public void updateTablesInView(int index, Table table) {
        this.gameView.updateTablesViewable(index, table.getPeopleSeatedNumber(), table.getStateInText());
    }

    @Override
    public void enablePowerUp(int index) {
        switch (index) {
            case 1 -> this.model.reduceDishPreparationTime();
            case 2 -> this.model.increaseWaitressSpeed();
            case 3 -> this.model.increaseMaxCustomerThatCanLeave();
            case 4 -> this.model.increaseGainMultiplier();
            default -> throw new UnsupportedOperationException("Invalid PowerUp Index");
        }
    }

    @Override
    public int[] getPowerUpsPrices() {
        return this.model.getPowerUpsPrices();
    }

    @Override
    public void updatePowerUpsButtonsInView() {
        var prices = this.model.getPowerUpsPrices();
        for(int i = 0; i < prices.length; i++) {
            if(prices[i] <= this.model.getCoins()) {
                this.gameView.updatePowerUpButton(i, Optional.empty(), true);
            }
        }
    }

    @Override
    public void addPricesToPowerUpsInView() {
        var prices = this.model.getPowerUpsPrices();
        for(int i = 0; i < prices.length; i++) {
            this.gameView.updatePowerUpButton(i, Optional.of(prices[i] + ""), false);
        }
    }
}
