package it.unibo.dinerdash.view.impl;

import java.awt.*;

import it.unibo.dinerdash.utility.impl.Pair;

public class CustomerView extends GameEntityViewable{

    private Pair<Integer, Integer> coordinates;
    private Image img;
    public CustomerView(Pair<Integer, Integer> coordinates,Image icon) {
        super(coordinates,icon);
        //this.coordinates=coordinates;
        //this.img=icon;
    }
    public Image getImage() {
        return this.img;
    }
    public Pair<Integer,Integer> getPosition() {
        return this.coordinates;
    }
    
}
