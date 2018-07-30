package graziani.andrea.pacspace.game.threads;

import graziani.andrea.pacspace.game.logic.GameSession;

/**
 * This class is used to update sprite position and detect collision.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class SpritePositionUpdaterGameThread extends AbstractGameThread {

    private GameSession gameSession;

    /**
     * Construct a newly allocated {@code SpritePositionUpdaterGameThread} object.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public SpritePositionUpdaterGameThread(GameSession pGameSession){
        this.gameSession = pGameSession;
    }

    @Override
    public void run() {

        while (isRunning)
            try {

                while (isPaused)
                    yield();

                Thread.sleep(50);
                this.gameSession.updateSpritePosition();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}