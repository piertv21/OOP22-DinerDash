package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.utility.api.GameTimer;
import it.unibo.dinerdash.utility.impl.GameTimerImpl;

final class GameTimerTest {

    private static final int TOLERANCE = 550;
    private static final int HALF = 500;
    private static final int ONE = 1000;
    private static final int TWO = 2000;
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
        Thread.sleep(ONE + TOLERANCE);
        gameTimer.stopTimer();

        assertEquals(value - 1, testValue.get());
    }

    @Test
    void testPauseTimer() throws InterruptedException {
        final var value = testValue.get();

        gameTimer.startTimer();
        Thread.sleep(TWO + TOLERANCE);
        gameTimer.pauseTimer();

        assertEquals(value - 2, testValue.get());
    }

    @Test
    void testRestartTimer() throws InterruptedException {
        final var value = testValue.get();

        gameTimer.restartTimer();
        Thread.sleep(ONE + TOLERANCE);
        gameTimer.stopTimer();

        assertEquals(value - 1, testValue.get());
    }

    @Test
    void testResumeTimer() throws InterruptedException {
        final var value = testValue.get();
        gameTimer.startTimer();

        Thread.sleep(ONE + TOLERANCE);
        gameTimer.pauseTimer();
        Thread.sleep(HALF);
        gameTimer.resumeTimer();
        Thread.sleep(ONE + TOLERANCE);
        gameTimer.stopTimer();

        assertEquals(value - 2, testValue.get());
    }

}
