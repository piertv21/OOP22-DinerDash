package it.unibo.dinerdash.GameEntities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public class CustomerTest {

    private Model model;

    @BeforeAll
	public void init() {
		this.model =new ModelImpl(null);
	}

    
    @Test
    void testGetDestination() {
        Customer testClient1 = new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
        model, 2);
        Optional<Pair<Integer,Integer>> destination=Optional.of(new Pair<Integer,Integer>(200, 300));
        testClient1.setDestination(destination);
        assertTrue(testClient1.getDestination().equals(destination));

    }

    @Test
    void testGetPosition() {
        Customer testClient1 = new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
        model, 2);

       assertTrue(testClient1.getPosition().equals(new Pair<Integer,Integer>(100, 100)));
    }

    @Test
    void testSetDestination() {
        Customer testClient1 = new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
        model, 2);
        Optional<Pair<Integer,Integer>> destination = Optional.of(new Pair<Integer,Integer>(500, 300));
        testClient1.setDestination(destination);
        assertTrue(testClient1.getDestination().equals(destination));
    }

    @Test
    void testSetPosition() {
        Customer testClient1 = new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
        model, 2);
        Pair<Integer,Integer> newPosition = new Pair<Integer,Integer>(500, 600);
        testClient1.setPosition(newPosition);
        assertTrue(testClient1.getPosition().equals(newPosition));
    }

    @Test
    void testSetState() {

        Customer testClient2 = new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
        model, 2);
        testClient2.setState(CustomerState.ORDERING);
        assertTrue(testClient2.getState().equals(CustomerState.ORDERING));
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
        assertTrue(testClient3.getPosition().equals(newPosition2));

    }

    @Test
    void testUpdate2() {

        Customer testClient4 = new CustomerImpl(new Pair<Integer,Integer>(200, 300), new Pair<Integer,Integer>(100, 100),
        model, 2);
        Optional<Pair<Integer,Integer>> destination=Optional.of(new Pair<Integer,Integer>(200, 400));
        testClient4.setDestination(destination);
        testClient4.update();
        testClient4.update();
        testClient4.update();
        Pair<Integer,Integer> newPosition3 = new Pair<Integer,Integer>(200, 315);
        assertTrue(testClient4.getPosition().equals(newPosition3));

    }
}
