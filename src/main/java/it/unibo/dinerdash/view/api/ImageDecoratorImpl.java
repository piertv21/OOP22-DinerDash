package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

public class ImageDecoratorImpl extends AbstractGameEntityViewableDecorator implements ImageDecorator {

    private Optional<Image> state;

    public ImageDecoratorImpl(GameEntityViewable decorated) {
        super(decorated);
    }

    @Override
    public void setState(Image state) {
        this.state = Optional.ofNullable(state);
    }
    
    @Override
    public Optional<Image> getState() {
        return this.state;
    }
    
}
