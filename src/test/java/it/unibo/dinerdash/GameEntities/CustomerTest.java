package it.unibo.dinerdash.GameEntities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public class CustomerTest {
    Model modello =new ModelImpl(null);
    Customer prova = new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
        modello, 2);
    @Test
    void testGetDestination() {
        Optional<Pair<Integer,Integer>> destination=Optional.of(new Pair<Integer,Integer>(200, 300));
       prova.setDestination(destination);
       assertTrue(prova.getDestination().equals(destination));

    }

    @Test
    void testGetPosition() {

       assertTrue(prova.getPosition().equals(new Pair<Integer,Integer>(100, 100)));
    }

    @Test
    void testSetDestination() {
        Optional<Pair<Integer,Integer>> destination = Optional.of(new Pair<Integer,Integer>(500, 300));
        prova.setDestination(destination);
        assertTrue(prova.getDestination().equals(destination));
    }

    @Test
    void testSetPosition() {
        Pair<Integer,Integer> newPosition = new Pair<Integer,Integer>(500, 600);
        prova.setPosition(newPosition);
        assertTrue(prova.getPosition().equals(newPosition));
    }

    @Test
    void testSetState() {

        Customer prova2 = new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
        modello, 2);
        prova2.setState(CustomerState.ORDERING);
        assertTrue(prova2.getState().equals(CustomerState.ORDERING));
    }

    @Test
    void testUpdate() {

        Customer prova3 = new CustomerImpl(new Pair<Integer,Integer>(300, 300), new Pair<Integer,Integer>(100, 100),
        modello, 2);
        Optional<Pair<Integer,Integer>> destination = Optional.of(new Pair<Integer,Integer>(500, 300));
        prova3.setDestination(destination);
        prova3.update();
        prova3.update();
        prova3.update();
        Pair<Integer,Integer> newPosition2 = new Pair<Integer,Integer>(315, 300);
        assertTrue(prova3.getPosition().equals(newPosition2));

    }

    @Test
    void testUpdate2() {

        Customer prova4 = new CustomerImpl(new Pair<Integer,Integer>(200, 300), new Pair<Integer,Integer>(100, 100),
        modello, 2);
        Optional<Pair<Integer,Integer>> destination=Optional.of(new Pair<Integer,Integer>(200, 400));
        prova4.setDestination(destination);
        prova4.update();
        prova4.update();
        prova4.update();
        Pair<Integer,Integer> newPosition3 = new Pair<Integer,Integer>(200, 315);
        assertTrue(prova4.getPosition().equals(newPosition3));

    }
}
