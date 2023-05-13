package it.unibo.dinerdash.utility.impl;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private final Runnable function;

    /**
     * Class constructor.
     * 
     * @param function is the method which is called
     */
    public GameTimerImpl(final Runnable function) {
        this.function = function;
        this.executorService = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTimer() {
        if (executorService.isEmpty() || executorService.get().isShutdown()) {
            executorService = Optional.of(Executors.newSingleThreadScheduledExecutor());
            updateTask = () -> {
                function.run();
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
