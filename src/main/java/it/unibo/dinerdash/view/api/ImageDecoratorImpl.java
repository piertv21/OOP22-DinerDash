package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

public class ImageDecoratorImpl extends AbstractGameEntityViewableDecorator implements ImageDecorator {

    private Optional<Image> state;

    public ImageDecoratorImpl(GameEntityViewable decorated) {
        super(decorated);
    }

    @Override
    public void setState(Optional<Image> icon) {
        this.state = icon;
    }

    @Override
    public Optional<Image> getState() {
        return this.state;
    }
    
}
