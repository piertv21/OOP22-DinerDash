package it.unibo.dinerdash.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

public class TableImplTest {
    private TableImpl table;

    @BeforeEach
    void setUp() {
        Pair<Integer, Integer> coordinates = new Pair<>(0, 0);
        Pair<Integer, Integer> size = new Pair<>(1, 1);
        int tableNumber = 1;
        table = new TableImpl(coordinates, size, tableNumber);
    }

    @Test
    void testSetAndGetState() {
        table.setState(TableState.ORDERING);
        assertEquals(TableState.ORDERING, table.getState());
    }

    @Test
    void testSetAndGetCustomer() {
        Customer customer = new CustomerImpl(null, null, null, 0);
        table.setCustom(Optional.of(customer));
        assertTrue(table.getCustomer().isPresent());
        assertEquals(customer, table.getCustomer().get());
    }

    @Test
    void testGetTableNumber() {
        assertEquals(1, table.getTableNumber());
    }

    @Test
    void testSetAndGetSeatedPeople() {
        table.setseatedPeople(2);
        assertEquals(2, table.getPeopleSeatedNumber());
    }

    @Test
    void testStartEating() {
        Table table = new TableImpl(new Pair<>(0, 0), new Pair<>(2, 2), 1);
        table.startEating();
        Optional<Long> timeFinishEating = ((TableImpl) table).timeFinishEating;
        assertTrue(timeFinishEating.isPresent());
        assertTrue(timeFinishEating.get() > 0);

    }

    @Test
    void testUpdate() {
        Table table = new TableImpl(new Pair<>(0, 0), new Pair<>(2, 2), 1);
        table.startEating();
        Optional<Long> timeFinishEating = ((TableImpl) table).timeFinishEating;
        table.update();
        assertEquals(TableState.EMPTY, table.getState());
        assertTrue(timeFinishEating.isPresent());
    }

    @Test
    void testGetStateInText() {
        table.setState(TableState.ORDERING);
        assertEquals("wantToOrder", table.getStateInText());
        table.setState(TableState.WANTING_TO_PAY);
        assertEquals("wantToPay", table.getStateInText());
        table.setState(TableState.EATING);
        assertEquals("eating", table.getStateInText());
        // table.setState(null);
        // assertEquals("null", table.getStateInText());
    }
}