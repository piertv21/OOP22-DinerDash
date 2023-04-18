package it.unibo.dinerdash.view.api;

import java.util.Optional;

import javax.swing.JLabel;

public class GameEntityViewableWithLabel extends AbstractGameEntityViewableDecorator implements LabelDecorator {

    private Optional<JLabel> state;

    public GameEntityViewableWithLabel(GameEntityViewable decorated) {
        super(decorated);
    }

    @Override
    public void setState(JLabel state) {
        this.state = Optional.ofNullable(state);
    }

    @Override
    public Optional<JLabel> getState() {
        return this.state;
    }
    
}
