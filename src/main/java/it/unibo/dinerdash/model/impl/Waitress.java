package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;

import it.unibo.dinerdash.model.api.AbstractGameEntityMovable;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.TableState;
import it.unibo.dinerdash.model.api.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends AbstractGameEntityMovable {

    //TODO Const (Costante) con numero max di piatti che può portare (2) 
    private static int SPEED = 1;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5; 
    
    private WaitressState state;
    private Model model;

    private LinkedList<Dish> orderList;
    private boolean flag;

    private int serveTable;
    public Waitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        super(new Pair<Integer,Integer>(550, 148), size, SPEED);    //TODO usa le coordinate passate come parametro
        this.state= WaitressState.WAITING;
        this.orderList=new LinkedList<>();
        this.model = model;
        
    }

    public void handleMovement(Dish dishReady) {
        if((state.equals(WaitressState.CALLING))||(state.equals(WaitressState.TAKING_DISH))||(state.equals(WaitressState.SERVING))) {
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
                    int tableNum=model.getTablefromPositon(getPosition()).getTableNumber();
                   if(this.checkRightTable(tableNum)) {
                    this.model.setTableState(TableState.EATING,  tableNum);

                    orderList.remove(orderList.stream()
                    .filter(o->o.getDishNumber()==tableNum)
                    .findFirst().get());
                    
                   }
                    state=WaitressState.WAITING;
                }
                else if(state.equals(WaitressState.TAKING_MONEY)) {
                  int coin = this.model.getCoins();
                  this.model.setCoins(coin+30);
                  state=WaitressState.WAITING;
                  int tableNum = model.getTablefromPositon(getDestination().get()).getTableNumber();
                  this.model.setTableState(TableState.EMPTY, tableNum);
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
    
    public void goGetDish(Dish dishReady) {
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

    public void addOrderForWaitress(Dish dishReady) {
        orderList.add(dishReady); 
    }

    public LinkedList<Dish> getOrderList() {
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