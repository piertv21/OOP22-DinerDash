package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.impl.ChefImpl;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.model.impl.TableImpl;
import it.unibo.dinerdash.model.impl.WaitressImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Factory method for game entities
 */
public class GameEntityFactoryImpl implements GameEntityFactory {
    
    @Override
    public Chef createChef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        return new ChefImpl(coordinates, size, model);
    }

    @Override
    public Customer createCustomer(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final Model model,
        final int numCust
    ) {
       return new CustomerImpl(coordinates, size, model, numCust);
    }

    @Override
    public Dish createDish(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int dishNumber) {
        return new DishImpl(coordinates, size, dishNumber);
    }

    @Override
    public Table createTable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int i) {
        return new TableImpl(coordinates, size, i);
    }

    @Override
    public Waitress createWaitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model) {
        return new WaitressImpl(coordinates, size, model);
    }
    
}
