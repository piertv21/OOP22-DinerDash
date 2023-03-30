package it.unibo.dinerdash.view.impl;

import java.awt.Image;

import javax.swing.JButton;

import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

public class TableViewable extends GameEntityViewable {

    JButton tableStateIndicator;

    public TableViewable(Pair<Integer, Integer> coordinates, Image icon) {
        super(coordinates, icon);
        this.tableStateIndicator.setVisible(false);
        //TODO Add JButton init
    }

    private void toggleState() {
        var state = this.tableStateIndicator.isVisible();
        this.tableStateIndicator.setVisible(!state);
    }

    public void setState() {
        //TODO Caricamento immagine
        this.toggleState();
    }
    
}