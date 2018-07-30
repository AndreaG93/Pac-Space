package graziani.andrea.pacspace.game.threads;

import graziani.andrea.pacspace.game.logic.GameSession;

/**
 * This class is used to implements a threat that update background.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class BackgroundUpdaterGameThread extends AbstractGameThread {

    private GameSession gameSession;

    /**
     * Construct a newly allocated {@code BackgroundUpdaterGameThread} object.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public BackgroundUpdaterGameThread(GameSession pGameSession){
        this.gameSession = pGameSession;
    }

    @Override
    public void run() {

        while (isRunning)
            try {

                while (isPaused)
                    yield();

                Thread.sleep(50);
                this.gameSession.updateBackground();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}