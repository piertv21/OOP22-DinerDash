package it.unibo.dinerdash;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.utility.impl.Direction;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class DirectionTest {

    private Direction direction;

    @Test
    void testUp() {
        direction = Direction.UP;
        assertEquals(0, direction.getX());
        assertEquals(-1, direction.getY());
    }

    @Test
    void testDown() {
        direction = Direction.DOWN;
        assertEquals(0, direction.getX());
        assertEquals(1, direction.getY());
    }

    @Test
    void testRight() {
        direction = Direction.RIGHT;
        assertEquals(1, direction.getX());
        assertEquals(0, direction.getY());
    }

    @Test
    void testLeft() {
        direction = Direction.LEFT;
        assertEquals(-1, direction.getX());
        assertEquals(0, direction.getY());
    }

}
