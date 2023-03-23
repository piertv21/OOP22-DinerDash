package it.unibo.dinerdash.view.impl;

import java.awt.Image;

import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;

public class CustomerViewable extends GameEntityViewable {

    public CustomerViewable(Pair<Integer, Integer> coordinates, Image icon) {
        super(coordinates, icon);
    }
}
