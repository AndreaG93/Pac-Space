package graziani.andrea.pacspace.game;

import android.graphics.Canvas;
import graziani.andrea.pacspace.game.logic.GameSession;

/**
 * This class is used to represents a game status and it is used to set correct game behavior
 * according to game status.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public interface GameStatus {

    /**
     * This method is used to update game screen.
     *
     * @param pGameSession - Represents a {@code GameSession} object that contains all object to draw.
     * @param pCanvas      - Represents a {@code Canvas} object used to perform "draw" calls.
     */
    void updateGameScreen(GameSession pGameSession, Canvas pCanvas);

    /**
     * This method is used to update game after a "Fling" gesture.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     * @param pAngleDirection - Represents a {@code double} value.
     */
    void updateGameAfterOnFlingGesture(GameSession pGameSession, double pAngleDirection);

    /**
     * This method is used to update game after a "Single Tap" gesture.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    void updateGameAfterSingleTapGesture(GameSession pGameSession);

    /**
     * This method is used to update game after a "Double Tap" gesture.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    void updateGameAfterDoubleTapGesture(GameSession pGameSession);

    /**
     * This method is used to update game when user touch back button.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    void updateGameAfterBackButtonPressing(GameSession pGameSession);
}