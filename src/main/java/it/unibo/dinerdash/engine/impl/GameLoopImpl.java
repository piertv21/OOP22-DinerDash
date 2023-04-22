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

    private final Model model;
    private final Controller controller;

    private volatile boolean running = false;
    private long lastTime = 0;
    private double delta = 0;

    private Thread thread;

    public GameLoopImpl(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    public synchronized void start() {
        if (!this.running) {
            running = true;
            lastTime = System.nanoTime();
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() {
        if (this.running) {
            running = false;
            thread.interrupt();
        }
    }

    @Override
    public void run() {
        int fps = 0;
        long fpsTimer = System.nanoTime();
        long frameTimer = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastTime;
            lastTime = currentTime;
            delta += elapsedTime / (double) TARGET_FRAME_TIME;

            if (delta >= 1.0 && model.getGameState() == GameState.RUNNING) {
                synchronized (model) {
                    model.update(elapsedTime);
                }

                synchronized (controller) {
                    controller.syncChanges();
                }

                delta--;
                fps++;
            }

            // Mostra gli fps nella console ogni secondo
            if (currentTime - fpsTimer >= TimeUnit.SECONDS.toNanos(1) && DEBUG) {
                System.out.println("FPS: " + fps);
                fps = 0;
                fpsTimer += TimeUnit.SECONDS.toNanos(1);
            }

            // Pausa il thread solo se necessario per risparmiare risorse della CPU
            long sleepTime = TimeUnit.NANOSECONDS.toMillis(frameTimer + TARGET_FRAME_TIME - System.nanoTime());
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            frameTimer = System.nanoTime();
        }
    }
}
