package graziani.andrea.pacspace.game.threads;

import graziani.andrea.pacspace.game.logic.GameSession;

/**
 * This class is used to implements a timer
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class TimerGameThread extends AbstractGameThread {

    private GameSession gameSession;

    /**
     * Construct a newly allocated {@code TimerGameThread} object.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public TimerGameThread(GameSession pGameSession){
        this.gameSession = pGameSession;
    }

    @Override
    public void run() {

        while (isRunning)
            try {

                while (isPaused)
                    yield();

                Thread.sleep(1000);
                this.gameSession.updateTimer();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}

