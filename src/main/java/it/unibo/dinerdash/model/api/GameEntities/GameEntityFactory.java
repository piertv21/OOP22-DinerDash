package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.impl.Pair;

public interface GameEntityFactory {

    Chef createChef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model);

    Customer createCustomer(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, 
    final Model model, final int numCust);

    Dish createDish(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int dishNumber);

    Table createTable();

    Waitress createWaitress();
    
}
