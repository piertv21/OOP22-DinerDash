package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;


import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends GameEntity implements Runnable {
    private Image cameriera;
    private int x=550, y=148;
    //private Pair<Integer,Integer> destinazione; 

    public Waitress(Pair<Integer, Integer> coordinates, ImageIcon icon) throws IOException {
        super(coordinates, icon);
        
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
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    
}
