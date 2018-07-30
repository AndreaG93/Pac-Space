package graziani.andrea.pacspace.game.states;

import android.graphics.Canvas;

import graziani.andrea.pacspace.game.logic.GameLogic;
import graziani.andrea.pacspace.game.logic.GameSession;
import graziani.andrea.pacspace.game.GameStatus;

/**
 * This class describes game logic to execute when game is in a "Running" status
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class Running implements GameStatus {

    @Override
    public void updateGameScreen(GameSession pGameSession, Canvas pCanvas) {
        pGameSession.drawBackground(pCanvas);
        pGameSession.drawSprites();
        pGameSession.drawHUD(pCanvas);
    }

    @Override
    public void updateGameAfterOnFlingGesture(GameSession pGameSession, double pAngleDirection) {
        pGameSession.getPacManPlayer().updateSpeed(pAngleDirection);
    }

    @Override
    public void updateGameAfterSingleTapGesture(GameSession pGameSession) {
    }

    @Override
    public void updateGameAfterDoubleTapGesture(GameSession pGameSession) {
        pGameSession.getPacManPlayer().stopPlayer();
    }

    @Override
    public void updateGameAfterBackButtonPressing(GameSession pGameSession) {
        GameLogic.pauseGame(pGameSession);
        pGameSession.displayPauseMessage();
    }
}