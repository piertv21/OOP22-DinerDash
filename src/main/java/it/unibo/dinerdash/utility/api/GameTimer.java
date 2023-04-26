package it.unibo.dinerdash.utility.api;

/*
 * This interface defines a game timer, that calls a function every second.
 */
public interface GameTimer {

    /*
     * Starts the timer.
     */
    void startTimer();

    /*
     * Stop the timer.
     */
    void stopTimer();

    /*
     * Pauses the timer.
     */
    void pauseTimer();

    /*
     * Resume timer.
     */
    void resumeTimer();

    /*
     * Starts the timer over.
     */
    void restartTimer();

}
