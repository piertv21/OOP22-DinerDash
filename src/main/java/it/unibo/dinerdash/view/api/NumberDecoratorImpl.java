package it.unibo.dinerdash.view.api;

public class NumberDecoratorImpl extends AbstractGameEntityViewableDecorator implements NumberDecorator {

    private int number;

    public NumberDecoratorImpl(final GameEntityViewable decorated) {
        super(decorated);
    }
    
    @Override
    public void setNumber(final int number) {
        this.number = number;
    }
    
    @Override
    public int getNumber() {
        return this.number;
    }
    
}
