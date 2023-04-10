package it.unibo.dinerdash.view.impl;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.unibo.dinerdash.controller.impl.ControllerImpl;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

public class TableViewable extends GameEntityViewable {

    JButton tableStateIndicator;
    boolean showState;  // Mostra bottone
    ControllerImpl contr;

    public TableViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Image icon,ControllerImpl controller) {
        super(coordinates, size, icon);
        this.tableStateIndicator = new JButton();
        this.showState = false;
        this.contr = controller;

        tableStateIndicator.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              //chiamo un metodo nel controller che va nel model e assegna la destinazione alla cameriera
              contr.setWaitresDestination(getPosition());
            } 
          } );
        tableStateIndicator.setBounds(getPosition().getX()+10,getPosition().getY()-40, 50, 50);
    }

    public void setState() {
        //TODO Caricamento immagine + set state
    }
    
}