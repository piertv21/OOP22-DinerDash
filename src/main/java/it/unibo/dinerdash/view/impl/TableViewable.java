package it.unibo.dinerdash.view.impl;

import java.awt.Image;

import javax.swing.JButton;

import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

public class TableViewable extends GameEntityViewable {

    JButton tableStateIndicator;
    boolean showState;  // Mostra bottone

    public TableViewable(Pair<Integer, Integer> coordinates, Image icon) {
        super(coordinates, icon);
        this.tableStateIndicator = new JButton();
        this.showState = false;
    }

    public void setState() {
        //TODO Caricamento immagine + set state
    }
    
}