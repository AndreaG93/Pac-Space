package graziani.andrea.pacspace.game;

/**
 * This class is used to represents game difficulty and it is used to set correct game behavior
 * according to selected game difficulty.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public interface GameDifficulty {

    /**
     * This method is used to get number of enemies.
     *
     * @return A {@code byte} value;
     */
    byte getNumberOfEnemies();

    /**
     * This method is used to get number of fruits.
     *
     * @return A {@code byte} value;
     */
    byte getNumberOfFruits();

    /**
     * This method is used to get number of player's lives.
     *
     * @return A {@code byte} value;
     */
    byte getNumberOfPlayerLives();

    /**
     * This method is used to get correct amount of points according to selected difficulty.
     *
     * @return A {@code byte} value;
     */
    byte getDifficultyPoints();

    /**
     * This method is used to get correct amount of fruit points according to selected difficulty.
     *
     * @return A {@code byte} value;
     */
    byte getFruitsPoints();

    /**
     * This method is used to get correct amount of time points according to selected difficulty.
     *
     * @return A {@code byte} value;
     */
    byte getTimePoints();
}