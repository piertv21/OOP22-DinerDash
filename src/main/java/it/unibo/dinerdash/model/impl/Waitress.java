package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends GameEntity implements Runnable {

    public Waitress(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        super(coordinates, icon);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
