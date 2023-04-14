package it.unibo.dinerdash.view.impl;

import java.awt.Image;
import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

public class TableViewable extends GameEntityViewable {

    boolean showState;  // Mostra bottone
    Controller contr;

    public TableViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Image icon, Controller controller) {
        super(coordinates, size, icon);
        this.showState = false;
        this.contr = controller;

        
    }

    public void setState() {
        //TODO Caricamento immagine + set state
    }
    
}