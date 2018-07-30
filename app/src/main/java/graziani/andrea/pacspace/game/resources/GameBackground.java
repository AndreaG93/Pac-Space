package graziani.andrea.pacspace.game.resources;

import android.graphics.Bitmap;

/**
 *
 */
public class GameBackground {

    // This bitmap represents game background
    private Bitmap background;

    // Represents the position of background
    private int positionBackground;






    /**
     * This method is used to set a new Background
     *
     * @param pBackground - Represents an {@code Bitmap} object.
     */
    public GameBackground(Bitmap pBackground) {
        this.background = pBackground;
    }

    /**
     * This method update the position of current background.
     *
     * @param surfaceWidth - Represents an {@code int}.
     */
    public void updatePositionBackground(int surfaceWidth) {
        if (this.positionBackground == surfaceWidth)
            this.positionBackground = 1;
        else
            this.positionBackground++;
    }


    public int getPositionBackground() {
        return positionBackground;
    }

    public Bitmap getBackground() {
        return background;
    }
}
