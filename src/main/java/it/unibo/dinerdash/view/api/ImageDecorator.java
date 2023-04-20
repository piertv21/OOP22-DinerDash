package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

public interface ImageDecorator {

    void setState(String state);

    String getState();

    void setStateIcon(Image icon);

    Optional<Image> getStateIcon();

}
