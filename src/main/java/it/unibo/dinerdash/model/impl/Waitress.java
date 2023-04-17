package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.TableState;
import it.unibo.dinerdash.model.api.WaitressState;
import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntityMovable;
import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends AbstractGameEntityMovable {

    private static int SPEED = 1;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5; 
    
    private WaitressState state;
    private Model model;

    private LinkedList<DishImpl> orderList;
    private boolean flag;

    private int serveTable;
    
    public Waitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        super(coordinates, size, SPEED);
        this.state= WaitressState.WAITING;
        this.orderList=new LinkedList<>();
        this.model = model;
        
    }

    public void handleMovement(DishImpl dishReady) {
        if((state.equals(WaitressState.CALLING))||(state.equals(WaitressState.TAKING_DISH))||(state.equals(WaitressState.SERVING))) {
            this.model.setNeedUpdate(true);
            if(getPosition().getX() <  this.getDestination().get().getX()) this.moveRight();
            else if(getPosition().getX() > this.getDestination().get().getX()+3) this.moveLeft();
            else if(getPosition().getY() > this.getDestination().get().getY()+3) this.moveUp();
            else if(getPosition().getY() < this.getDestination().get().getY()) this.moveDown();

            if((getPosition().getX()>=this.getDestination().get().getX()-4)&& 
            ((getPosition().getX())<=this.getDestination().get().getX()+4)&& 
            ((getPosition().getY()<=this.getDestination().get().getY()+4)&& 
            ((getPosition().getY()>= this.getDestination().get().getY()-4)))) {
                if(state.equals(WaitressState.CALLING)){
                    this.setPosition(this.getDestination().get()); 
                    state = WaitressState.WAITING;
                    model.sendOrder(model.getTablefromPositon(getPosition()).getTableNumber());

                }else if(state.equals(WaitressState.TAKING_DISH)) {  //cameriere è arrivata al bancone a prendere il piatto
                    state = WaitressState.WAITING; 
                }
                else if(state.equals(WaitressState.SERVING)) {
                    this.setPosition(this.getDestination().get());
                    serveTable=model.getTablefromPositon(getPosition()).getTableNumber();
                   if(this.checkRightTable(serveTable)) {
                    this.model.setTableState(TableState.EATING,  serveTable);

                    orderList.remove(orderList.stream()
                    .filter(o->o.getDishNumber()==serveTable)
                    .findFirst().get());
                    
                   }
                    state=WaitressState.WAITING;
                }
                else if(state.equals(WaitressState.TAKING_MONEY)) {
                  int coin = this.model.getCoins();
                  this.model.setCoins(coin+30);
                  state=WaitressState.WAITING;
                  serveTable = model.getTablefromPositon(getDestination().get()).getTableNumber();
                  this.model.setTableState(TableState.EMPTY, serveTable);
                }
                       
            }

        }
       
    }

    public void setState(WaitressState state) {
        this.state = state;
    }

    public WaitressState getState() {
        return this.state ;
    }
    
    public void goGetDish(DishImpl dishReady) {
        this.setDestination(Optional.of(dishReady.getPosition()));
        this.state=WaitressState.TAKING_DISH;
    }
    public void takeTableOrder(Pair<Integer,Integer> position) {
        this.setDestination(Optional.of(position));
        this.state=WaitressState.CALLING;
    }
    public void serveOrder(Pair<Integer,Integer> position) {
        this.setDestination(Optional.of(position));
        state = WaitressState.SERVING;
    }
    public void colletMoney(Pair<Integer,Integer> position) {
        this.setDestination(Optional.of(position));
        state = WaitressState.TAKING_MONEY;
    }

    public int getOrdersNumber() {
        return this.orderList.size();
    }

    public void addOrderForWaitress(DishImpl dishReady) {
        orderList.add(dishReady); 
    }

    public LinkedList<DishImpl> getOrderList() {
        return this.orderList; 
    }

    private boolean checkRightTable(int tableNumber) {
        flag=false;
        this.orderList.forEach( o ->{
            if(o.getDishNumber()==tableNumber)flag=true;
        });
       return flag;
    }
}