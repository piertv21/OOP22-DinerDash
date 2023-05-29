package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

/**
 * {@inheritDoc}
 *
 * Implementation of the ImageDecorator interface.
 */
public class ImageDecoratorImpl extends AbstractGameEntityViewableDecorator implements ImageDecorator {

    private Optional<Image> state;

    /**
     * Class constructor.
     * 
     * @param decorated is the GameEntityViewable to be decorated
     */
    public ImageDecoratorImpl(final GameEntityViewable decorated) {
        super(decorated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImage(final Image icon) {
        this.state = Optional.ofNullable(icon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Image> getImage() {
        return this.state;
    }

}
