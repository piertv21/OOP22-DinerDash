package it.unibo.dinerdash.model.api.states;

/**
 * Waitress state
 */
public enum WaitressState {

    /**
     * Waitress waiting for custumer order.
     */
    WAITING,

    /**
     * Waitress his called.
     */
    CALLING,

    /**
     * Waitress taking order form custumer.
     */
    TAKING_DISH,

    /**
     * Waitress is serving the order.
     */
    SERVING,

    /**
     * Waitress is taking money from the custumer.
     */
    TAKING_MONEY

}
