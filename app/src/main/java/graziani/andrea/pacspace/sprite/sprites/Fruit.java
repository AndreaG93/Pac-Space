package graziani.andrea.pacspace.sprite.sprites;

import android.graphics.Bitmap;

import java.util.Random;

import graziani.andrea.pacspace.sprite.Sprite;

public class Fruit extends Sprite {

    public Fruit(Bitmap arg1) {

        this.BMP_ROWS = 1;
        this.BMP_COLUMNS = 1;
        this.myBitmap = arg1;

        spriteInitialization();
    }

    @Override
    public void updateFrame() {
        this.currentFrame = 0;
        this.animationRow = 0;
    }


    /**
     * This method is used to reset position and speed of current {@code Sprite} object.
     *
     * @param surfaceHeight Represents an {@code int}.
     * @param surfaceWidth  Represents an {@code int}.
     */
    @Override
    public void resetPositionAndSpeed(int surfaceHeight, int surfaceWidth) {
        this.posY = new Random().nextInt(surfaceHeight);
        this.posX = surfaceWidth + ((new Random().nextInt(30 - 20) + 20) * this.spriteWidth);

        this.xSpeed = -(new Random().nextInt(30 - 20) + 20);
        this.ySpeed = 0;
    }

    /**
     * This method is used to update position of current {@code Sprite} object.
     *
     * @param surfaceWidth  Represents a {@code int}.
     * @param surfaceHeight Represents a {@code int}.
     */
    @Override
    public void updatePosition(int surfaceWidth, int surfaceHeight) {
        if (this.posX + this.xSpeed < surfaceWidth && this.posX + this.xSpeed > 0)
            posX = posX + (int) xSpeed;
        else {
            posX = surfaceWidth - 1;
            posY = new Random().nextInt(surfaceHeight);
        }
    }
}