package it.unibo.dinerdash.model.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.utility.impl.Pair;

public class CustomerImplTest {
    Model modello =new ModelImpl(null);
    @Test
    void testGetDestination() {
        
        Customer prova= new CustomerImpl(new Pair<Integer,Integer>(100, 100), new Pair<Integer,Integer>(100, 100),
         modello, 2);
        assertTrue(prova.getPosition().equals(new Pair<Integer,Integer>(100, 100)), "è giusto"); 
    }

    @Test
    void testGetPosition() {

    }

    @Test
    void testSetDestination() {

    }

    @Test
    void testSetPosition() {

    }

    @Test
    void testSetState() {

    }

    @Test
    void testUpdate() {

    }
}
