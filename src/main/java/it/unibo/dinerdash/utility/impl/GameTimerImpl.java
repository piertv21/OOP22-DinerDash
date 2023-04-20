package it.unibo.dinerdash.utility.impl;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.api.GameTimer;

public class GameTimerImpl implements GameTimer {

    private static final int INITIAL_DELAY = 1;
    private static final int PERIOD = 1;

    private Optional<ScheduledExecutorService> executorService;
    private Runnable updateTask;
    private Model model;

    public GameTimerImpl(Model model) {
        this.model = model;
        this.executorService = Optional.empty();
    }

    @Override
    public void startTimer() {
        if (executorService.isEmpty() || executorService.get().isShutdown()) {
            executorService = Optional.of(Executors.newSingleThreadScheduledExecutor());
            updateTask = () -> {
                model.decrementRemainingTime();
            };
            executorService.get().scheduleAtFixedRate(updateTask, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
        }
    }

    @Override
    public void stopTimer() {
        executorService.ifPresent(ScheduledExecutorService::shutdownNow);
        executorService = Optional.empty();
    }

    @Override
    public void pauseTimer() {
        executorService.ifPresent(ScheduledExecutorService::shutdownNow);
    }

    @Override
    public void resumeTimer() {
        if (executorService.isEmpty() || executorService.get().isShutdown()) {
            executorService = Optional.of(Executors.newSingleThreadScheduledExecutor());
            executorService.get().scheduleAtFixedRate(updateTask, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
        }
    }

    @Override
    public void restartTimer() {
        stopTimer();
        startTimer();
    }
}
