package it.unibo.dinerdash.view.impl;

import java.awt.Image;

import javax.swing.JButton;

import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

public class TableViewable extends GameEntityViewable {

    JButton tableState;

    public TableViewable(Pair<Integer, Integer> coordinates, Image icon) {
        super(coordinates, icon);
        //TODO Auto-generated constructor stub
    }

    public JButton getButton(){                                       
        return this.tableState;
    } 
    
}