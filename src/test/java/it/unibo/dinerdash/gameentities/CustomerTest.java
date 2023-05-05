package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.states.CustomerState;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class CustomerTest {

    private Model model;
    private Customer testClient1;

    @BeforeEach
	void init() {
		this.model = new ModelImpl();
        testClient1 = new CustomerImpl(new Pair<Integer, Integer>(100, 100), new Pair<Integer, Integer>(100, 100),
        model, 2);
	}

    
    @Test
    void testGetDestination() {
        Optional<Pair<Integer, Integer>> destination = Optional.of(new Pair<Integer,Integer>(200, 300));
        testClient1.setDestination(destination);
        assertTrue(testClient1.getDestination().equals(destination));

    }

    @Test
    void testGetPosition() {
       assertEquals(new Pair<Integer, Integer>(100, 100), testClient1.getPosition());
    }

    @Test
    void testSetDestination() {
        Optional<Pair<Integer, Integer>> destination = Optional.of(new Pair<Integer, Integer>(500, 300));
        testClient1.setDestination(destination);
        assertEquals(destination, testClient1.getDestination());
    }

    @Test
    void testSetPosition() {
        Pair<Integer,Integer> newPosition = new Pair<Integer, Integer>(500, 600);
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

        Customer testClient3 = new CustomerImpl(new Pair<Integer,Integer>(300, 300), new Pair<Integer,Integer>(100, 100),
        model, 2);
        Optional<Pair<Integer,Integer>> destination = Optional.of(new Pair<Integer,Integer>(500, 300));
        testClient3.setDestination(destination);
        testClient3.update();
        testClient3.update();
        testClient3.update();
        Pair<Integer,Integer> newPosition2 = new Pair<Integer,Integer>(315, 300);
        assertEquals(newPosition2, testClient3.getPosition());

    }

    @Test
    void testUpdate2() {

        Customer testClient4 = new CustomerImpl(new Pair<Integer,Integer>(200, 300), new Pair<Integer,Integer>(100, 100),
        model, 2);
        Optional<Pair<Integer, Integer>> destination = Optional.of(new Pair<Integer, Integer>(200, 400));
        testClient4.setDestination(destination);
        testClient4.update();
        testClient4.update();
        testClient4.update();
        Pair<Integer,Integer> newPosition3 = new Pair<Integer, Integer>(200, 315);
        assertEquals(newPosition3, testClient4.getPosition());

    }

    @Test
    void testGetCustomerPatience() {
        testClient1.setState(CustomerState.LINE);
        assertEquals(7, testClient1.getCustomerPatience());
        testClient1.update();
        assertEquals(6, testClient1.getCustomerPatience());
    }
}
