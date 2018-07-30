package graziani.andrea.pacspace.game.states;

import android.graphics.Canvas;

import graziani.andrea.pacspace.game.logic.GameLogic;
import graziani.andrea.pacspace.game.logic.GameSession;
import graziani.andrea.pacspace.game.GameStatus;

/**
 * This class describes game logic to execute when game is in a "Starting" status
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class Starting implements GameStatus {

    @Override
    public void updateGameScreen(GameSession pGameSession, Canvas pCanvas) {
        pGameSession.drawBackground(pCanvas);
        pGameSession.drawStartMessage(pCanvas);
        pGameSession.drawHUD(pCanvas);
    }

    @Override
    public void updateGameAfterOnFlingGesture(GameSession pGameSession, double pAngleDirection) {
    }

    @Override
    public void updateGameAfterSingleTapGesture(GameSession pGameSession) {
        GameLogic.startGame(pGameSession);
    }

    @Override
    public void updateGameAfterDoubleTapGesture(GameSession pGameSession) {
    }

    @Override
    public void updateGameAfterBackButtonPressing(GameSession pGameSession) {
    }
}