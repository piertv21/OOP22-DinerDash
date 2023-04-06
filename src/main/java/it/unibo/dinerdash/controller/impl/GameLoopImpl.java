package it.unibo.dinerdash.controller.impl;

import it.unibo.dinerdash.controller.api.GameLoop;
import it.unibo.dinerdash.model.api.GameState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.view.impl.ViewImpl;

public class GameLoopImpl implements GameLoop, Runnable {
    
    private boolean running;
    private long desiredFPS = 60;
    private long desiredDeltaLoop = (1000 * 1000 * 1000) / desiredFPS;

    private ModelImpl model;
    private ViewImpl view;
    private ControllerImpl controller;    

    public GameLoopImpl(ModelImpl model, ViewImpl view, ControllerImpl controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void start() {
        running = true;
        new Thread(this).start();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long beginLoopTime;
        long endLoopTime;
        long currentUpdateTime = System.nanoTime();
        long lastUpdateTime;
        long deltaLoop;

        while (running) {
            beginLoopTime = System.nanoTime();

            // Calcola il tempo trascorso dall'ultimo aggiornamento del gioco
            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            long elapsedUpdateTime = currentUpdateTime - lastUpdateTime;

            if (model.getGameState() == GameState.RUNNING) {
                // Aggiorna il modello di gioco solo se lo stato di gioco è RUNNING
                model.update(elapsedUpdateTime);
            }

            // Effettua il rendering della vista
            // view.render(); DA FARE ON DEMAND (Observer)

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            // Attendere il tempo necessario per raggiungere il frame rate desiderato
            if (deltaLoop < desiredDeltaLoop) {
                try {
                    Thread.sleep((desiredDeltaLoop - deltaLoop) / (1000 * 1000));
                } catch (InterruptedException e) {
                    // Gestione dell'eccezione
                }
            }
        }
    }

}
