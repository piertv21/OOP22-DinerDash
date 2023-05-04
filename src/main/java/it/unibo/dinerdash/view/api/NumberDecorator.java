package it.unibo.dinerdash.view.api;

/**
 * This interface defines a decorator that
 * adds a number field to a GameEntityViewable.
 */
public interface NumberDecorator extends GameEntityViewableDecorator {

    /**
     * Setter for Tables, Customer's and Dishes
     * Moltiplicity for the view.
     * 
     * @param number used for molteplicity 
     */
    void setNumber(int number);

    /**
     * Getter for Tables, Dishes and Customer's Moltiplicity 
     * for the view.
     * 
     * @return GameEntityViewable's molteplicity value
     */
    int getNumber();

}
