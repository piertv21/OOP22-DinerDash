package it.unibo.dinerdash.utility;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.impl.ModelImpl;

public class GameTimer {
    private static final int INITIAL_DELAY = 1; // Ritardo iniziale per l'esecuzione del task in secondi
    private static final int PERIOD = 1; // Periodo di esecuzione del task in secondi

    private Optional<ScheduledExecutorService> executorService = Optional.empty(); // Optional per l'executor service
    private Runnable updateTask; // Task per l'aggiornamento del tempo rimanente
    private ModelImpl model; // Riferimento al modello

    public GameTimer(ModelImpl model) {
        this.model = model;
    }

    public void startTimer() {
        if (executorService.isEmpty() || executorService.get().isShutdown()) {
            executorService = Optional.of(Executors.newSingleThreadScheduledExecutor());
            updateTask = () -> {
                model.decrementRemainingTime(); // Chiama il metodo del modello per decrementare il tempo rimanente
            };
            executorService.get().scheduleAtFixedRate(updateTask, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS); // Esegue il task ogni secondo
        }
    }

    public void stopTimer() {
        executorService.ifPresent(ScheduledExecutorService::shutdownNow); // Ferma il task se presente
        executorService = Optional.empty();
    }

    public void pauseTimer() {
        executorService.ifPresent(ScheduledExecutorService::shutdownNow); // Ferma il task se presente
    }

    public void resumeTimer() {
        if (executorService.isEmpty() || executorService.get().isShutdown()) {
            executorService = Optional.of(Executors.newSingleThreadScheduledExecutor());
            executorService.get().scheduleAtFixedRate(updateTask, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS); // Esegue il task ogni secondo
        }
    }

    public void restartTimer() {
        stopTimer(); // Ferma il timer
        startTimer(); // Avvia il timer da capo
    }
}
