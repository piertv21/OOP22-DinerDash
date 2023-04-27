package it.unibo.dinerdash.controller.impl;

import java.util.stream.IntStream;

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

/**
 * {@inheritDoc}
 *
 * Implementation of the Controller interface.
 */
public class ControllerImpl implements Controller {

    private Model model;
    private View view;
    private GameView gameView;
    private GameLoop gameLoop;
    private GameTimer gameTimer;

    /**
     * Class constructor, instantiates the Model
     * by passing this instance.
     */
    public ControllerImpl() {
        this.model = new ModelImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final View view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(GameView gameView) {
        this.gameView = gameView;
        this.model.start();

        this.gameLoop = new GameLoopImpl(this);
        this.gameLoop.start();

        this.gameTimer = new GameTimerImpl(this.model);
        gameTimer.startTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restart() {
        this.gameView.restart();
        this.model.restart();
        this.gameTimer.restartTimer();
        this.updateView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.model.setGameState(GameState.PAUSED);
        this.gameTimer.pauseTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.model.setGameState(GameState.RUNNING);
        this.gameTimer.resumeTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void quitWithoutPlaying() {
        this.view.quit();
        System.exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void quit() {
        this.model.clear();
        this.gameView.clear();
        this.quitWithoutPlaying();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGame() {
        this.model.update();
        this.updateView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gameIsEnded() {
        this.gameLoop.stop();
        this.gameTimer.stopTimer();        
        this.view.showGameOverView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoins() {
        return this.model.getCoins();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemainingTime() {
        var time = this.model.getRemainingTime();
        int minutes = time / 60;
        int remainingSeconds = time % 60;
        String formattedTime = String.format("%d:%02d", minutes, remainingSeconds);
        return formattedTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCustomersWhoLeft() {
        return this.model.getCustomersWhoLeft() + "/" + this.model.getCustomerWhoCanLeft();
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRestaurantWidth() {
        return this.model.getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRestaurantHeight() {
        return this.model.getHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateView() {
        this.gameView.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void callWaitress(final int indexList, final String s, final Pair<Integer, Integer> position) {
        model.setWaiterssInfo(indexList, s, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCustomerToView(final Customer customer) {
        this.gameView.addCustomerViewable(customer.getPosition(), customer.getSize(),
                customer.isActive(), customer.getCustomerCount(), customer.getCustomerPatience());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCustomersInView(final int index, final Customer customer) {
        this.gameView.updateCustomersViewable(index, customer.getPosition(),
                customer.isActive(), customer.getCustomerPatience());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCustomerInView(final int index) {
        this.gameView.removeCustomerViewable(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addChefToView(final Chef chef) {
        this.gameView.addChefViewable(chef.getPosition(), chef.getSize(), chef.isActive());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChefInView(final Chef chef) {
        this.gameView.updateChefViewable(chef.isActive());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWaitressToView(final Waitress waitress) {
        this.gameView.addWaitressViewable(waitress.getPosition(), waitress.getSize(), waitress.isActive(),
                waitress.getOrdersNumber());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateWaitressInView(final Waitress waitress) {
        this.gameView.updateWaitressViewable(waitress.getPosition(), waitress.getOrdersNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDishToView(final Dish dish) {
        this.gameView.addDishViewable(dish.getPosition(), dish.getSize(), dish.isActive(), dish.getDishNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDishesInView(final int index, final Dish dish) {
        this.gameView.updateDishesViewable(index, dish.isActive());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDishInView(final int index) {
        this.gameView.deleteDishViewable(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTableToView(final Table table) {
        this.gameView.addTableViewable(table.getPosition(), table.getSize(), table.isActive(), table.getPeopleSeatedNumber(), "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTablesInView(final int index, final Table table) {
        this.gameView.updateTablesViewable(index, table.getPeopleSeatedNumber(), table.getStateInText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enablePowerUp(final int index) {
        switch (index) {
            case 0 -> this.model.reduceDishPreparationTime();
            case 1 -> this.model.increaseWaitressSpeed();
            case 2 -> this.model.increaseMaxCustomerThatCanLeave();
            case 3 -> this.model.increaseGainMultiplier();
            default -> throw new UnsupportedOperationException("Invalid PowerUp Index");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getPowerUpsPrices() {
        return this.model.getPowerUpsPrices();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePowerUpsButtonsInView() {
        var prices = this.model.getPowerUpsPrices();
        IntStream.range(0, prices.length)
            .forEach(i -> this.gameView.updatePowerUpButton(i, 
           this.model.canActivatePowerUp(prices[i]) ? true : false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return this.model.getGameState();
    }

}
