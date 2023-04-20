package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

public interface ImageDecorator {

    void setState(Image state);

    Optional<Image> getState();

}
