package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends GameEntityImpl {

    private float speed;

    public Waitress(Pair<Integer, Integer> coordinates) {
        super(new Pair<Integer,Integer>(550, 148));        
        /*  try {
            cameriera = ImageIO.read(new File("waiter_front.png"));                                  assegno l'immagine alla cameriera
            
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void handleMovement() {
        //TO-DO
    }
    
}
