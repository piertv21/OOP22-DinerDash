package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.impl.ChefImpl;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public class GameEntityFactoryImpl implements GameEntityFactory {
    
    @Override
    public Chef createChef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        return new ChefImpl(coordinates, size, model);
    }

    @Override
    public Customer createCustomer(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, 
    final Model model, final int numCust) {
       return new CustomerImpl(coordinates, size, model, numCust);
    }

    @Override
    public Dish createDish(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int dishNumber) {
        return new DishImpl(coordinates, size, dishNumber);
    }

    @Override
    public Table createTable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTable'");
    }

    @Override
    public Waitress createWaitress() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWaitress'");
    }
    
}
