package it.unibo.dinerdash.model.api.states;

/**
 * States that can be used for Customer.
 */
public enum CustomerState {

    /**
     * Customer waiting in line.
     */
    LINE,

    /**
     * Customer whom waited too much ,and want to leave.
     */
    ANGRY,

    /** 
     * Customer walking to the table.
     */
    WALKING,

    /**
     * Customer thinking about what to order.
     */
    THINKING,

    /**
     * Customer wanting to order.
     */
    ORDERING

}
