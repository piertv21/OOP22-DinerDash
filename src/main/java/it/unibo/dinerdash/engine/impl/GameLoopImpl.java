package it.unibo.dinerdash.engine.impl;

import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.engine.api.GameLoop;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.States.GameState;

public class GameLoopImpl implements GameLoop, Runnable {

    private static final boolean DEBUG = true;
    private static final int TARGET_FPS = 60;
    private static final long TARGET_FRAME_TIME = TimeUnit.SECONDS.toNanos(1) / TARGET_FPS;

    private Model model;
    private Controller controller;

    private boolean running;
    private long lastTime;
    private double delta;

    private Thread thread;

    public GameLoopImpl(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    public void start() {
        running = true;
        lastTime = System.nanoTime();
        this.thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            this.thread.join();
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
            
            if (delta >= 1.0 && this.model.getGameState() == GameState.RUNNING) {
                
                synchronized (model) {
                    //TODO Model usa elapsed time (tempo dall'ultimo ciclo) per calcolare dinamicamente la velocità di cameriera e clienti
                    // e fare in modo che non aumenti/diminuisca a caso
                    model.update(elapsedTime);
                }

                synchronized (controller) {
                    controller.syncChanges();
                }

                delta--;
                fps++;
            }

            //TODO - DEBUG Mostra gli fps nella console ogni secondo
            if (System.currentTimeMillis() - timer >= TimeUnit.SECONDS.toMillis(1) && DEBUG) {
                System.out.println("FPS: " + fps);
                fps = 0;
                timer += TimeUnit.SECONDS.toMillis(1);
            }

            // Pausa il thread solo se necessario per risparmiare risorse della CPU
            if (delta < 1.0) {
                long sleepTime = (long) ((1.0 - delta) * TARGET_FRAME_TIME);
                try {
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
