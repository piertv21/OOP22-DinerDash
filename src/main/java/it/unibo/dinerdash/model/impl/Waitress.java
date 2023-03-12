package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import java.awt.*;

import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends GameEntity implements Runnable {

    public Waitress(Pair<Integer, Integer> coordinates, ImageIcon icon) throws IOException {
        super(new Pair<Integer,Integer>(550, 148), icon);        
        /*  try {
            cameriera = ImageIO.read(new File("waiter_front.png"));                                  assegno l'immagine alla cameriera
            
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
    public Optional<Pair<Integer, Integer>> getPosition() {
        return this.getPosition();
    }
    
}
