package it.unibo.dinerdash.view.api;

import java.util.Optional;

import javax.swing.JLabel;

public interface LabelDecorator {

    void setState(JLabel state);

    Optional<JLabel> getState();

}
