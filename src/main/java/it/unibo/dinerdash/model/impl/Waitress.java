package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityMovableImpl;
import it.unibo.dinerdash.model.api.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends GameEntityMovableImpl {

    private static int SPEED = 1;
    
    private WaitressState state;
    private ModelImpl model;


    public Waitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size) {
        super(new Pair<Integer,Integer>(550, 148), size, SPEED);
        
    }

    public void handleMovement() {
        if(state.equals(WaitressState.CALLING)) {
            /*if(getPosition().getX() < this.getDestination().get().getX()) this.moveRight(); 
            else if(getPosition().getY() > this.getDestination().get().getY()) this.moveUp();
            else if(getPosition().getY() < this.getDestination().get().getY()) this.moveDown();
            if((getPosition().getX()>=this.getDestination().get().getX())&&
            ((getPosition().getY()<=this.getDestination().get().getY()+4)&&
            (getPosition().getY()>=this.getDestination().get().getY()-4)))     //creo una hitbox del tavolo
            {                                                                           
                    state = CustomerState.THINKING;
                    this.setPosition(this.getDestination().get());
                    this.setActive(false);                                              //cliente pensa, quindi la sua immagine deve sparire       
            }     
            */
            if(getPosition().getX() <  this.getDestination().get().getX()) this.moveRight();
            else if(getPosition().getX() > this.getDestination().get().getX()+3) this.moveLeft();
            else if(getPosition().getY() > this.getDestination().get().getY()+3) this.moveUp();
            else if(getPosition().getY() < this.getDestination().get().getY()) this.moveDown();

            if((getPosition().getX()>=this.getDestination().get().getX()-100)&& ((getPosition().getX())<=this.getDestination().get().getX()+100)&& ((getPosition().getY()<=this.getDestination().get().getY()+100)&& ((getPosition().getY()>= this.getDestination().get().getY()-100)))) {
                state = WaitressState.ORDERING;
                this.setPosition(this.getDestination().get());
                this.setActive(false);                           
            }

        }else if(state.equals(WaitressState.ORDERING))                                          //il cliente pensa a cosa ordinare
        {
               //SEND ORDER
        }
       
    }

    
    public void setState(WaitressState state) {
        this.state = state;
    }

    public WaitressState getState() {
        return this.state ;
    }
    
}
