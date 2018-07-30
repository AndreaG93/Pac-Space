package graziani.andrea.pacspace.game.logic;



import java.sql.Time;



/**
 * This class provides game logic.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public final class GameLogic {

    /**
     * This method is used to reset game.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public static void resetGame(GameSession pGameSession) {

        pGameSession.getGameStatistics().setFruits(0);
        pGameSession.getGameStatistics().setGameScore(0);
        pGameSession.getGameStatistics().setSurvivalTime(new Time(0));
    }

    /**
     * This method is used to launch game for first time.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public static void launchGame(GameSession pGameSession)
    {
        // Change game status...
        // =================================================================== //
        pGameSession.setGameStatus(GameStatusHolder.startingStatus);

        // Starting all thread (are paused)...
        // =================================================================== //
        pGameSession.getTimerGameThread().start();
        pGameSession.getSpritePositionUpdaterGameThread().start();
        pGameSession.getFrameUpdaterGameThread().start();
        pGameSession.getDrawerGameThread().start();
        pGameSession.getBackgroundUpdaterGameThread().start();

        // Starting music and needed threads...
        // =================================================================== //

        // Start 'DrawerThread' and 'BackgroundPositionUpdaterThread'...
        pGameSession.getDrawerGameThread().setThreadPause(false);
        pGameSession.getBackgroundUpdaterGameThread().setThreadPause(false);

        // Start music if enabled...
        pGameSession.getGameMusic().play();
    }

    /**
     * This method is used to stop game.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public static void stopGame(GameSession pGameSession) {

        // Stopping all thread...
        // =================================================================== //
        pGameSession.getTimerGameThread().stopThread();
        pGameSession.getSpritePositionUpdaterGameThread().stopThread();
        pGameSession.getFrameUpdaterGameThread().stopThread();
        pGameSession.getDrawerGameThread().stopThread();
        pGameSession.getBackgroundUpdaterGameThread().stopThread();

        // Stopping game sounds and release resources...
        // =================================================================== //
        pGameSession.getGameMusic().releaseResource();
        pGameSession.getPauseSoundEffect().releaseResource();
    }

    /**
     * This method is used to start game
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public static void startGame(GameSession pGameSession) {

        // Resuming threats...
        pGameSession.getFrameUpdaterGameThread().setThreadPause(false);
        pGameSession.getSpritePositionUpdaterGameThread().setThreadPause(false);
        pGameSession.getTimerGameThread().setThreadPause(false);

        // Change game status...
        pGameSession.setGameStatus(GameStatusHolder.runningStatus);
    }

    /**
     * This method is used to resume game after "Paused" state.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public static void resumeGame(GameSession pGameSession) {

        // Update music...
        pGameSession.getGameMusic().play();
        pGameSession.getPauseSoundEffect().restart();

        // Start game...
        startGame(pGameSession);
    }

    /**
     * This method is used to pause game after.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public static void pauseGame(GameSession pGameSession) {

        // Stopping threats...
        pGameSession.getFrameUpdaterGameThread().setThreadPause(true);
        pGameSession.getSpritePositionUpdaterGameThread().setThreadPause(true);
        pGameSession.getTimerGameThread().setThreadPause(true);

        // Stopping music...
        pGameSession.getGameMusic().pause();
        pGameSession.getPauseSoundEffect().play();

        // Change game status...
        pGameSession.setGameStatus(GameStatusHolder.pausedStatus);
    }


}