package it.unibo.dinerdash.model.api.gameentities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.impl.ChefImpl;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.model.impl.TableImpl;
import it.unibo.dinerdash.model.impl.WaitressImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the Game Entity Factory.
 */
public class GameEntityFactoryImpl implements GameEntityFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Chef createChef(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final Optional<Model> model
    ) {
        return new ChefImpl(coordinates, size, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer createCustomer(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final Model model,
        final int numCust
    ) {
        return new CustomerImpl(coordinates, size, Optional.of(model), numCust);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dish createDish(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final int dishNumber
    ) {
        return new DishImpl(coordinates, size, dishNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Table createTable(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final int i
    ) {
        return new TableImpl(coordinates, size, i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Waitress createWaitress(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final Optional<Model> model
    ) {
        return new WaitressImpl(coordinates, size, model);
    }

}
