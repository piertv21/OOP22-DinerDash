package it.unibo.dinerdash.gameentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactory;
import it.unibo.dinerdash.model.api.gameentities.GameEntityFactoryImpl;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.model.impl.TableImpl;
import it.unibo.dinerdash.utility.impl.Pair;

final class TableTest {
    private Table table = new TableImpl(null, null, 0);
    private final GameEntityFactory factory = new GameEntityFactoryImpl();
    private Model model;

    @BeforeEach
    void init() {
        table = this.factory.createTable(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                1);
    }

    @Test
    void testSetAndGetState() {
        table.setState(TableState.ORDERING);
        assertEquals(TableState.ORDERING, table.getState());
    }

    @Test
    void testSetAndGetCustomer() {
        this.model = new ModelImpl();
        Customer customer = this.factory.createCustomer(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                model, 2);
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
        Table table = this.factory.createTable(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                2);
        table.startEating();
        final Optional<Long> timeFinishEating = ((TableImpl) table).getTimeFinishEating();
        assertTrue(timeFinishEating.isPresent());
        assertTrue(timeFinishEating.get() > 0);

    }

    @Test
    void testUpdate() {
        Table table = this.factory.createTable(
                new Pair<Integer, Integer>(100, 100),
                new Pair<Integer, Integer>(100, 100),
                2);
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
    }
}
