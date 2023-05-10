package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.states.CustomerState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class CustomerTest {
    private Model model;
    private Customer testClient1;
    private static final  Optional<Pair<Integer, Integer>> DESTINATION = Optional.of(new Pair<Integer, Integer>(200, 300));
    private final GameEntityFactory factory =  new GameEntityFactoryImpl();

    @BeforeEach
    void init() {
	    this.model = new ModelImpl();
        testClient1 = this.factory.createCustomer(
            new Pair<Integer, Integer>(100, 100),
             new Pair<Integer, Integer>(100, 100),
            model, 2);
	}

    @Test
    void testGetDestination() {
        testClient1.setDestination(DESTINATION);
        assertEquals(testClient1.getDestination(), DESTINATION);
    }

    @Test
    void testGetPosition() {
       assertEquals(new Pair<Integer, Integer>(100, 100), testClient1.getPosition());
    }

    @Test
    void testSetDestination() {
        testClient1.setDestination(DESTINATION);
        assertEquals(DESTINATION, testClient1.getDestination());
    }

    @Test
    void testSetPosition() {
        final Pair<Integer, Integer> newPosition = new Pair<>(500, 600);
        testClient1.setPosition(newPosition);
        assertEquals(newPosition, testClient1.getPosition());
    }

    @Test
    void testSetState() {
        testClient1.setState(CustomerState.ORDERING);
        assertEquals(CustomerState.ORDERING, testClient1.getState());
    }

    @Test
    void testUpdate() {

        final Customer testClient3 = this.factory.createCustomer(new Pair<Integer, Integer>(300, 300), new Pair<Integer, Integer>(100, 100),
        model, 2);
        final Optional<Pair<Integer, Integer>> destination = Optional.of(new Pair<Integer, Integer>(500, 300));
        testClient3.setDestination(destination);
        testClient3.update();
        testClient3.update();
        testClient3.update();
        final Pair<Integer, Integer> newPosition2 = new Pair<>(315, 300);
        assertEquals(newPosition2, testClient3.getPosition());

    }

    @Test
    void testUpdate2() {
        final Customer testClient4 = this.factory.createCustomer(DESTINATION.get(), new Pair<Integer, Integer>(100, 100),
        model, 2);
        final Optional<Pair<Integer, Integer>> destination = Optional.of(new Pair<Integer, Integer>(200, 400));
        testClient4.setDestination(destination);
        testClient4.update();
        testClient4.update();
        testClient4.update();
        final Pair<Integer, Integer> newPosition3 = new Pair<>(200, 315);
        assertEquals(newPosition3, testClient4.getPosition());
    }

    @Test
    void testGetCustomerPatience() {
        final int exepect = 7;
        final int got = 6;
        testClient1.setState(CustomerState.LINE);
        assertEquals(exepect, testClient1.getCustomerPatience());
        testClient1.update();
        assertEquals(got, testClient1.getCustomerPatience());
    }
}
