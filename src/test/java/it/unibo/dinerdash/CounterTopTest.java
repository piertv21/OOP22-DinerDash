package it.unibo.dinerdash;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Countertop;
import it.unibo.dinerdash.model.impl.CountertopImpl;

final class CountertopTest {
    
    private static Countertop countertop;
    
    @BeforeAll
    static void init() {
        countertop = new CountertopImpl(Optional.empty());
    }

    @Test
    void testAddOrder() {
        final var result = countertop.addOrder(1);
        assertEquals(result, true);
        assertEquals(countertop.addOrder(2), true);
        assertEquals(countertop.addOrder(3), true);
        assertEquals(countertop.addOrder(4), true);
        assertEquals(countertop.addOrder(5), false);
    }

    @Test
    void testThereAreDishesToPrepare() {
        assertEquals(countertop.thereAreDishesToPrepare(), true);
    }

    @Test
    void testGetNextDishToPrepare() {

    }

    @Test
    void testSetDishReady() {

    }

    @Test
    void testTakeDish() {

    }

}
