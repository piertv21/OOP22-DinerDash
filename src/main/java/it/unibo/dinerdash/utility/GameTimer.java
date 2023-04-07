package it.unibo.dinerdash.utility;

import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.impl.ModelImpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameTimer {

    private static final int INITIAL_DELAY = 0;
    private static final int PERIOD = 1; // periodo in secondi
    
    private ScheduledExecutorService executor;
    private ModelImpl model;
    private volatile boolean isPaused; // utilizzo di volatile per garantire la visibilità tra thread

    public GameTimer(ModelImpl model) {
        executor = Executors.newSingleThreadScheduledExecutor();
        this.model = model;
    }

    // Metodo per sospendere l'aggiornamento del tempo
    public void pause() {
        isPaused = true;
    }

    // Metodo per riprendere l'aggiornamento del tempo
    public void resume() {
        isPaused = false;
    }

    // Metodo per fermare l'aggiornamento del tempo
    public void stop() {
        executor.shutdown();
    }

    // Metodo per avviare l'aggiornamento del tempo
    public void start() {
        executor.scheduleAtFixedRate(() -> {
            if (!isPaused) {
                model.decrementRemainingTime();
            }
        }, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }

    // Metodo per riavviare l'aggiornamento del tempo
    public void restart() {
        stop(); // ferma il timer
        start(); // avvia il timer
    }

}
