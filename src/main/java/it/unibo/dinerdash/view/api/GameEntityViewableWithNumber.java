package it.unibo.dinerdash.view.api;

public class GameEntityViewableWithNumber extends AbstractGameEntityViewableDecorator implements NumberDecorator {

    private int number;

    public GameEntityViewableWithNumber(GameEntityViewable decorated) {
        super(decorated);
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return this.number;
    }
    
}
