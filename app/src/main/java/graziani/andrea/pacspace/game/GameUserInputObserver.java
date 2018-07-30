package graziani.andrea.pacspace.game;

import graziani.andrea.pacspace.game.logic.GameSession;

/**
 * This class is used to represents an "User input" observer.
 */
public interface GameUserInputObserver {

    /**
     * This method is used to update game after a "Fling" gesture.
     *
     * @param pAngleDirection - Represents a {@code double} value.
     */
    void updateGameAfterOnFlingGesture(double pAngleDirection);

    /**
     * This method is used to update game after a "Single Tap" gesture.
     */
    void updateGameAfterSingleTapGesture();

    /**
     * This method is used to update game after a "Double Tap" gesture.
     */
    void updateGameAfterDoubleTapGesture();

    /**
     * This method is used to update game when user touch back button.
     */
    void updateGameAfterBackButtonPressing();
}
