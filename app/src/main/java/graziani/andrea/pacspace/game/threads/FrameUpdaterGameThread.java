package graziani.andrea.pacspace.game.threads;




import graziani.andrea.pacspace.game.logic.GameSession;



public class FrameUpdaterGameThread extends AbstractGameThread {

    private GameSession gameSession;

    /**
     * Construct a newly allocated {@code SpritePositionUpdaterGameThread} object.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public FrameUpdaterGameThread(GameSession pGameSession){
        this.gameSession = pGameSession;
    }

    @Override
    public void run() {

        while (isRunning)
            try {

                while (isPaused)
                    yield();

                Thread.sleep(100);
                this.gameSession.updateFrame();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}