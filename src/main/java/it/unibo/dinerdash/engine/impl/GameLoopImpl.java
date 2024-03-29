package it.unibo.dinerdash.engine.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.engine.api.GameLoop;
import it.unibo.dinerdash.model.api.states.GameState;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameLoop interface.
 */
public class GameLoopImpl implements GameLoop, Runnable {

    private static final int TARGET_FPS = 60;
    private static final long TARGET_FRAME_TIME = TimeUnit.SECONDS.toNanos(1) / TARGET_FPS;

    private final Optional<Controller> controller;

    private volatile boolean running;
    private long lastTime;
    private double delta;

    private final Thread thread;

    /**
     * Class constructor.
     * 
     * @param controller is the game controller
     */
    public GameLoopImpl(final Controller controller) {
        this.running = false;
        this.lastTime = 0;
        this.delta = 0;
        this.controller = Optional.of(controller);
        this.thread = new Thread(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void start() {
        if (!this.running) {
            this.running = true;
            this.lastTime = System.nanoTime();
            this.thread.start();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void stop() {
        if (this.running) {
            this.running = false;
            this.thread.interrupt();
        }
    }

    /**
     * GameLoop main thread function. Until execution is finished
     * it calls a method on the controller based on the Target FPS
     * and eventually waits via Thread.sleep().
     */
    @Override
    public void run() {
        long frameTimer = System.nanoTime();

        while (running) {
            final long currentTime = System.nanoTime();
            final long elapsedTime = currentTime - lastTime;
            lastTime = currentTime;
            delta += elapsedTime / (double) TARGET_FRAME_TIME;

            if (delta >= 1.0 && this.controller.get().getGameState() == GameState.RUNNING) {

                synchronized (controller.get()) {
                    this.controller.get().updateGame();
                }

                delta--;
            }

            final long sleepTime = TimeUnit.NANOSECONDS.toMillis(frameTimer + TARGET_FRAME_TIME - System.nanoTime());
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
