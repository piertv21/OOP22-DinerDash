package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

public class ImageDecoratorImpl extends AbstractGameEntityViewableDecorator implements ImageDecorator {

    private String stateText;
    private Optional<Image> stateIcon;

    public ImageDecoratorImpl(GameEntityViewable decorated) {
        super(decorated);
    }

    @Override
    public void setState(String state) {
        this.stateText = state;
    }
    
    @Override
    public String getState() {
        return this.stateText;
    }

    @Override
    public void setStateIcon(Image icon) {
        this.stateIcon = Optional.ofNullable(icon);
    }

    @Override
    public Optional<Image> getStateIcon() {
        return this.stateIcon;
    }
    
}
