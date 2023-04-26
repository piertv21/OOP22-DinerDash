package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

public interface ImageDecorator extends GameEntityViewableDecorator {

    void setState(Optional<Image> icon);

    Optional<Image> getState();

}
