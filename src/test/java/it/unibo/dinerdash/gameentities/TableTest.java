package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.TableImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class TableTest {
    private Table table = new TableImpl(null, null, 0);

    @BeforeEach
    void setUp() {
        final Pair<Integer, Integer> coordinates = new Pair<>(0, 0);
        final Pair<Integer, Integer> size = new Pair<>(1, 1);
        final int tableNumber = 1;
        table = new TableImpl(coordinates, size, tableNumber);
    }

    @Test
    void testSetAndGetState() {
        table.setState(TableState.ORDERING);
        assertEquals(TableState.ORDERING, table.getState());
    }

    @Test
    void testSetAndGetCustomer() {
        final Customer customer = new CustomerImpl(null, null, null, 0);
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
        table.setSeatedPeople(2);
        assertEquals(2, table.getPeopleSeatedNumber());
    }

    @Test
    void testStartEating() {
        final Table table = new TableImpl(new Pair<>(0, 0), new Pair<>(2, 2), 1);
        table.startEating();
        final Optional<Long> timeFinishEating = ((TableImpl) table).getTimeFinishEating();
        assertTrue(timeFinishEating.isPresent());
        assertTrue(timeFinishEating.get() > 0);

    }

    @Test
    void testUpdate() {
        final Table table = new TableImpl(new Pair<>(0, 0), new Pair<>(2, 2), 1);
        table.startEating();
        final Optional<Long> timeFinishEating = ((TableImpl) table).getTimeFinishEating();
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
