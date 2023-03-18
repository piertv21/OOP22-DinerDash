package it.unibo.dinerdash.view.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.dinerdash.controller.impl.GameController;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GameView {

    private static final String FRAME_NAME = "Diner Dash";
    private static final String QUIT = "Quit";
    private static final String RESTART = "Restart";
    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;

    private GameController controller;
    private final JFrame frame = new JFrame(FRAME_NAME);

    public GameView() {
        this.setController(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JPanel panel = new JPanel(new GridLayout(DEFAULT_WIDTH/2, DEFAULT_HEIGHT/2));
        frame.getContentPane().add(panel);

        JButton restartButton = new JButton(RESTART);
        restartButton.addActionListener(e -> this.controller.restart());
        panel.add(restartButton);

        JButton quitButton = new JButton(QUIT);
        restartButton.addActionListener(e -> this.controller.quit());
        panel.add(quitButton);

        // TO-DO

        //frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void draw() {
        //TO-DO
    }

}
