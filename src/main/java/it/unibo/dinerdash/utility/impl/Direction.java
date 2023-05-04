package it.unibo.dinerdash.utility.impl;

/**
 * This Enum contains 4 directions for entity movement management.
 */
public enum Direction {

    /**
     * Direction.UP represents the upward movement by subtracting 1 from y coordinate.
     */
    UP(0, -1),

    /**
     * Direction.DOWN represents the downward movement by adding 1 to the y coordinate.
     */
    DOWN(0, 1),

    /**
     * Direction.RIGHT represents the right movement by adding 1 to the x coordinate.
     */
    RIGHT(1, 0),

    /**
     * Direction.LEFT represents the left movement by subtracting 1 from the x coordinate.
     */
    LEFT(-1, 0);

    private int x;
    private int y;

    /**
     * Class constructor.
     *
     * @param x movement direction coordinate
     * @param y movement direction coordinate
     */
    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Gets the x value of direction.
     *
     * @return x value of direction
     */
    public int getX() {
        return this.x;
    }
    /**
     * Gets the y value of direction.
     *
     * @return y value of direction
     */
    public int getY() {
        return this.y;
    }

}
