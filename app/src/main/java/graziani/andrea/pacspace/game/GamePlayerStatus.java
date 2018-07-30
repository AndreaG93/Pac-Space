package graziani.andrea.pacspace.game;

import graziani.andrea.pacspace.sprite.Sprite;

/**
 * This class is used to represents player status and it is used to set correct game behavior
 * according to player status.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public interface GamePlayerStatus {

    /**
     * This method is used to update game after an player-enemy collision.
     *
     * @param pSprite - Represents a {@code pSprite} object
     */
    void updateGameAfterPlayerEnemyCollision(Sprite pSprite);

    /**
     * This method is used to update game after an player-fruit collision.
     *
     * @param pSprite - Represents a {@code pSprite} object
     */
    void updateGameAfterPlayerFruitCollision(Sprite pSprite);

    /**
     * This method is used to update game after an player-PowerUP collision.
     *
     * @param pSprite - Represents a {@code pSprite} object
     */
    void updateGameAfterPlayerPowerUPCollision(Sprite pSprite);
}