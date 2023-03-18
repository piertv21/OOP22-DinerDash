package it.unibo.dinerdash.view.impl;

import java.awt.*;

import it.unibo.dinerdash.utility.impl.Pair;

public class CustomerView {

    private Pair<Integer, Integer> coordinates;
    private Image img;
    public CustomerView(Pair<Integer, Integer> coordinates,Image icon) {
        this.coordinates=coordinates;
        this.img=icon;
    }
    public Image getImage() {
        return this.img;
    }
    public Pair<Integer,Integer> getPosition() {
        return this.coordinates;
    }
    
}
