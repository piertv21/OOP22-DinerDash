package it.unibo.dinerdash.utility.impl;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.api.GameTimer;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameTimer interface.
 */
public class GameTimerImpl implements GameTimer {

    private static final int INITIAL_DELAY = 1;
    private static final int PERIOD = 1;

    private Optional<ScheduledExecutorService> executorService;
    private Runnable updateTask;
    private final Optional<Model> model;

    /**
     * Class constructor.
     * 
     * @param model is the Model from which a method is called
     */
    public GameTimerImpl(final Model model) {
        this.model = Optional.of(model);
        this.executorService = Optional.empty();
    }

    /**
     * {@inheritDoc}
     * 
     * Here it should be specified which model method to call.
     */
    @Override
    public void startTimer() {
        if (executorService.isEmpty() || executorService.get().isShutdown()) {
            executorService = Optional.of(Executors.newSingleThreadScheduledExecutor());
            updateTask = () -> {
                model.get().decrementRemainingTime();
            };
            executorService.get().scheduleAtFixedRate(updateTask, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
        }
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void stopTimer() {
        executorService.ifPresent(ScheduledExecutorService::shutdownNow);
        executorService = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseTimer() {
        executorService.ifPresent(ScheduledExecutorService::shutdownNow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeTimer() {
        if (executorService.isEmpty() || executorService.get().isShutdown()) {
            executorService = Optional.of(Executors.newSingleThreadScheduledExecutor());
            executorService.get().scheduleAtFixedRate(updateTask, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartTimer() {
        stopTimer();
        startTimer();
    }

}
