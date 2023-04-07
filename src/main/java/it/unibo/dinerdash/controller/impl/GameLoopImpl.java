package it.unibo.dinerdash.controller.impl;

import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.controller.api.GameLoop;
import it.unibo.dinerdash.model.api.GameState;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.view.impl.GameView;

public class GameLoopImpl implements GameLoop, Runnable {

    private static final int TARGET_FPS = 60;
    private static final long TARGET_FRAME_TIME = TimeUnit.SECONDS.toNanos(1) / TARGET_FPS;

    private ModelImpl model;
    private GameView view;

    private boolean running;
    private long lastTime;
    private double delta;

    private Thread thread;

    public GameLoopImpl(ModelImpl model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        running = true;
        lastTime = System.nanoTime();

        // Avvia il thread del gameloop
        this.thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            this.thread.join(); // Attende che il thread del game loop si completi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int fps = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            long elapsedTime = now - lastTime;
            lastTime = now;
            delta += elapsedTime / (double) TARGET_FRAME_TIME;

            // Aggiorna il modello e la vista solo se è trascorso il tempo necessario
            // per raggiungere il target di frame rate (60 volte al secondo)
            if (delta >= 1.0 && this.model.getGameState() == GameState.RUNNING) {

                // Aggiorna il modello
                synchronized (model) {
                    // Model usa elapsed time (tempo dall'ultimo ciclo) per calcolare dinamicamente la velocità
                    // e fare in modo che si adatti alla velocità di esecuzione del programma senza aumentare/diminuire a caso
                    model.update(elapsedTime);
                }

                // Ridisegna la vista
                synchronized (view) {
                    view.render();
                }

                delta--;
                fps++;
            }

            // TODO - DEBUG Mostra gli fps nella console ogni secondo
            if (System.currentTimeMillis() - timer >= TimeUnit.SECONDS.toMillis(1)) {
                System.out.println("FPS: " + fps);
                fps = 0;
                timer += TimeUnit.SECONDS.toMillis(1);
            }

            // Pausa il thread solo se necessario per risparmiare risorse della CPU
            if (delta < 1.0) {
                long sleepTime = (long) ((1.0 - delta) * TARGET_FRAME_TIME);
                try {
                    // Converti il sleepTime in millisecondi se la precisione richiesta è inferiore ai nanosecondi
                    if (sleepTime < TimeUnit.MILLISECONDS.toNanos(1)) {
                        Thread.sleep(1);
                    } else {
                        Thread.sleep(TimeUnit.NANOSECONDS.toMillis(sleepTime));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
