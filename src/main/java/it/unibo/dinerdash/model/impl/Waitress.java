package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;

import it.unibo.dinerdash.model.api.AbstractGameEntityMovable;
import it.unibo.dinerdash.model.api.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends AbstractGameEntityMovable {

    private static int SPEED = 1;
    
    private WaitressState state;
    private ModelImpl model;

    private LinkedList<Dish> orderList;

    private int serveTable;
    public Waitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size) {
        super(new Pair<Integer,Integer>(550, 148), size, SPEED);

        this.orderList=new LinkedList<>();
        
    }

    public void handleMovement() {
        if((state.equals(WaitressState.CALLING))||(state.equals(WaitressState.TAKING_DISH))||(state.equals(WaitressState.SERVING))) {
            if(getPosition().getX() <  this.getDestination().get().getX()) this.moveRight();
            else if(getPosition().getX() > this.getDestination().get().getX()+3) this.moveLeft();
            else if(getPosition().getY() > this.getDestination().get().getY()+3) this.moveUp();
            else if(getPosition().getY() < this.getDestination().get().getY()) this.moveDown();

            if((getPosition().getX()>=this.getDestination().get().getX()-100)&& 
            ((getPosition().getX())<=this.getDestination().get().getX()+100)&& 
            ((getPosition().getY()<=this.getDestination().get().getY()+100)&& 
            ((getPosition().getY()>= this.getDestination().get().getY()-100)))) {
                if(state.equals(WaitressState.CALLING)){
                    this.setPosition(this.getDestination().get()); 
                    // TODO SEND ORDER
                    state = WaitressState.WAITING;

                }else if(state.equals(WaitressState.TAKING_DISH)) {  //cameriere è arrivata al bancone a prendere il piatto
                    state = WaitressState.SERVING;
                    serveTable=orderList.removeFirst().getDishNumber();
                   // this.setDestination(Optional.of(model.getTable().get(serveTable).getPosition()));
                }
                else if(state.equals(WaitressState.SERVING)) {
                    // TODO CHANGE TO TABBLE STATE IN EATING IF IT'S THE RIGHT TABLE
                    this.model.setTableEating(serveTable);
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
        orderList.add(dishReady); //Aggiungi solo quando arriva al tavolo
        this.state=WaitressState.TAKING_DISH;
    }

    /*
        prendiOrdineDaTavolo(int tableNumber);
        prendiOrdineDalBancone(Dish dish);
        serviOrdine(int tableNumber);
        collectSoldi(int tableNumber); //collect soldi + libera tavolo + elimina clienti etc
    */
}