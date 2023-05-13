package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.utility.api.GameTimer;
import it.unibo.dinerdash.utility.impl.GameTimerImpl;

final class GameTimerTest {

    private static final int TOLERANCE = 300;
    private static GameTimer gameTimer;
    private static AtomicInteger testValue;

    @BeforeAll
    static void init() {
        testValue = new AtomicInteger(10);
        gameTimer = new GameTimerImpl(testValue::decrementAndGet);
    }

    @Test
    void testStartTimer() throws InterruptedException {
        final var value = testValue.get();
        gameTimer.startTimer();
        Thread.sleep(1000 + TOLERANCE);
        gameTimer.stopTimer();

        assertEquals(value - 1, testValue.get());
    }

    @Test
    void testPauseTimer() throws InterruptedException {
        final var value = testValue.get();

        gameTimer.startTimer();
        Thread.sleep(2000 + TOLERANCE);
        gameTimer.pauseTimer();

        assertEquals(value - 2, testValue.get());
    }

    @Test
    void testRestartTimer() throws InterruptedException {
        final var value = testValue.get();

        gameTimer.restartTimer();
        Thread.sleep(1000 + TOLERANCE);
        gameTimer.stopTimer();

        assertEquals(value - 1, testValue.get());
    }

    @Test
    void testResumeTimer() throws InterruptedException {
        final var value = testValue.get();
        gameTimer.startTimer();

        Thread.sleep(1000 + TOLERANCE);
        gameTimer.pauseTimer();
        Thread.sleep(500);
        gameTimer.resumeTimer();
        Thread.sleep(1000 + TOLERANCE);
        gameTimer.stopTimer();

        assertEquals(value - 2, testValue.get());
    }

}
