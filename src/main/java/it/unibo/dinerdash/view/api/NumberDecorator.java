package it.unibo.dinerdash.view.api;

/**
 * This interface defines a decorator that
 * adds a number field to a GameEntityViewable.
 */
public interface NumberDecorator extends GameEntityViewableDecorator {

    /**
     * Setter for additional number.
     * 
     * @param number is the number to be set 
     */
    void setNumber(int number);

    /**
     * Getter for additional number.
     * 
     * @return the number stored
     */
    int getNumber();

}
