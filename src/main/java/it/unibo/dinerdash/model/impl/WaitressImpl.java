package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntityMovable;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.model.api.GameEntities.Waitress;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.model.api.States.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * TODO Usare un metodo update generale
 */
public class WaitressImpl extends AbstractGameEntityMovable implements Waitress {

    private static int SPEED = 2;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5;

    private WaitressState state;
    private Model model;

    private LinkedList<Dish> orderList;

    private int serveTable;

    public WaitressImpl(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        super(coordinates, size, SPEED);
        this.state = WaitressState.WAITING;
        this.orderList = new LinkedList<>();
        this.model = model;
    }

    @Override
    public void handleMovement() {
        if (!state.equals(WaitressState.WAITING)) {
            if (getPosition().getX() < this.getDestination().get().getX())
                this.moveRight();
            else if (getPosition().getX() > this.getDestination().get().getX() + 3)
                this.moveLeft();
            else if (getPosition().getY() > this.getDestination().get().getY() + 3)
                this.moveUp();
            else if (getPosition().getY() < this.getDestination().get().getY())
                this.moveDown();

            if ((getPosition().getX() >= this.getDestination().get().getX() - 4) &&
                    ((getPosition().getX()) <= this.getDestination().get().getX() + 4) &&
                    ((getPosition().getY() <= this.getDestination().get().getY() + 4) &&
                            ((getPosition().getY() >= this.getDestination().get().getY() - 4)))) {
                if (state.equals(WaitressState.CALLING)) {
                    this.setPosition(this.getDestination().get());
                    state = WaitressState.WAITING;
                    model.sendOrder(model.getTablefromPositon(getPosition()).getTableNumber());

                } else if (state.equals(WaitressState.TAKING_DISH)) { // cameriere è arrivata al bancone a prendere il
                                                                      // piatto
                    state = WaitressState.WAITING;
                } else if (state.equals(WaitressState.SERVING)) {
                    this.setPosition(this.getDestination().get());
                    serveTable = model.getTablefromPositon(getPosition()).getTableNumber();
                    if (this.checkRightTable(serveTable)) {
                        this.model.setTableState(TableState.EATING, serveTable);

                        orderList.remove(orderList.stream()
                                .filter(o -> o.getDishNumber() == serveTable)
                                .findFirst().get());

                    }
                    state = WaitressState.WAITING;
                } else if (state.equals(WaitressState.TAKING_MONEY)) {
                    this.model.earnMoneyFromTable();
                    state = WaitressState.WAITING;
                    serveTable = model.getTablefromPositon(getDestination().get()).getTableNumber();
                    this.model.setTableState(TableState.EMPTY, serveTable);
                }

            }

        }

    }

    @Override
    public void setState(WaitressState state) {
        this.state = state;
    }

    @Override
    public WaitressState getState() {
        return this.state;
    }

    @Override
    public void goGetDish(Dish dishReady) {
        this.setDestination(Optional.of(dishReady.getPosition()));
        this.state = WaitressState.TAKING_DISH;
    }

    @Override
    public void takeTableOrder(Pair<Integer, Integer> position) {
        this.setDestination(Optional.of(position));
        this.state = WaitressState.CALLING;
    }

    @Override
    public void serveOrder(Pair<Integer, Integer> position) {
        this.setDestination(Optional.of(position));
        state = WaitressState.SERVING;
    }

    @Override
    public void collectMoney(Pair<Integer, Integer> position) {
        this.setDestination(Optional.of(position));
        state = WaitressState.TAKING_MONEY;
    }

    @Override
    public int getOrdersNumber() {
        return this.orderList.size();
    }

    @Override
    public void addOrderForWaitress(Dish dishReady) {
        orderList.add(dishReady);
    }

    @Override
    public LinkedList<Dish> getOrderList() {
        return this.orderList;
    }

    @Override
    public boolean checkRightTable(int tableNumber) {
        return this.orderList.stream().anyMatch(e -> e.getDishNumber() == tableNumber);
    }

    @Override
    public void incrementSpeed() {
        this.setMovementSpeed((int) (this.getMovementSpeed() * WAITRESS_SPEED_MULTIPLIER));
    }

}