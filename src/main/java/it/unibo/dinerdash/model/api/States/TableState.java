package it.unibo.dinerdash.model.api.States;

/**
 * Table state
 */
public enum TableState {

    /**
     * Custumer just seated and thinking on what order.
     */
    THINKING,

    /**
     * Custumer start ordering food.
     */
    ORDERING,

    /**
     * Custumer waiting for his order.
     */
    WAITING_MEAL,

    /**
     * Custumer started eating.
     */
    EATING,

    /**
     * Custumer finished eating and want to pay.
     */
    WANTING_TO_PAY,

    /**
     * Custumer left the table.
     */
    EMPTY
    
}
