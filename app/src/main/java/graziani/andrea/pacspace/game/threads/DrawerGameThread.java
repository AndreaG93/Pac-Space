package graziani.andrea.pacspace.game.threads;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import graziani.andrea.pacspace.game.logic.GameSession;

/**
 * This class is used to implements a drawer threat.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class DrawerGameThread extends AbstractGameThread {

    private GameSession gameSession;

    /**
     * Construct a newly allocated {@code DrawerGameThread} object.
     *
     * @param pGameSession - Represents a {@code GameSession} object.
     */
    public DrawerGameThread(GameSession pGameSession) {
        this.gameSession = pGameSession;
    }

    @Override
    public void run() {

        Canvas myCanvas = null;
        SurfaceHolder mySurfaceHolder = null;

        while (isRunning) {

            while (isPaused)
                yield();

            try {
                mySurfaceHolder = this.gameSession.getGameScreen().getHolder();

                if (mySurfaceHolder != null) {

                    // This instruction return a "Canvas" object that is used to draw into the surface. It can be "null".
                    myCanvas = mySurfaceHolder.lockCanvas();

                    if (myCanvas != null)
                        this.gameSession.draw(myCanvas);
                }

            } catch (IllegalArgumentException e) {
                Log.e(String.valueOf(Thread.currentThread().getId()) + ":", "Error during lockCanvas():\n" + e.getMessage());
            } finally {
                if (myCanvas != null && mySurfaceHolder != null) {
                    try {
                        // Finish editing pixels in the surface.
                        // After this call, the surface's current pixels will be shown on the screen
                        mySurfaceHolder.unlockCanvasAndPost(myCanvas);

                    } catch (IllegalArgumentException e) {
                        Log.e(String.valueOf(Thread.currentThread().getId()) + ":", "Error during unlockCanvasAndPost():\n" + e.getMessage());
                    }
                }
            }
        }
    }
}