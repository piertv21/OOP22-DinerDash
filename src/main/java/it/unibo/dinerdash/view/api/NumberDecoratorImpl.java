package it.unibo.dinerdash.view.api;

/**
 * {@inheritDoc}
 *
 * Implementation of the NumberDecorator interface.
 */
public class NumberDecoratorImpl extends AbstractGameEntityViewableDecorator implements NumberDecorator {

    private int number;

    /**
     * Class constructor.
     * 
     * @param decorated is the GameEntityViewable to be decorated
     */
    public NumberDecoratorImpl(final GameEntityViewable decorated) {
        super(decorated);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setNumber(final int number) {
        this.number = number;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumber() {
        return this.number;
    }
    
}
