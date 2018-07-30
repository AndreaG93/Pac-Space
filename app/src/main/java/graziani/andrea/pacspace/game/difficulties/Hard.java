package graziani.andrea.pacspace.game.difficulties;

import graziani.andrea.pacspace.game.GameDifficulty;

/**
 * This class is used to change game behavior when "Hard" difficulty has been selected.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class Hard implements GameDifficulty {

    @Override
    public byte getNumberOfEnemies() {
        return 0;
    }

    @Override
    public byte getNumberOfFruits() {
        return 0;
    }

    @Override
    public byte getNumberOfPlayerLives() {
        return 0;
    }

    @Override
    public byte getDifficultyPoints() {
        return 0;
    }

    @Override
    public byte getFruitsPoints() {
        return 0;
    }

    @Override
    public byte getTimePoints() {
        return 0;
    }
}