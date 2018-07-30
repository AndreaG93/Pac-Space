package graziani.andrea.pacspace.game.threads;

/**
 * This class is used to represents a generic game thread
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public abstract class AbstractGameThread extends Thread {

    protected boolean isRunning = true;
    protected boolean isPaused = true;

    /**
     * This method is used to pause/un-pause current thread.
     *
     * @param arg0 - Represents a {@code boolean} value
     */
    public void setThreadPause(boolean arg0) {
        this.isPaused = arg0;
    }

    /**
     * This method is used to stop current thread.
     */
    public void stopThread() {
        this.isRunning = false;
    }
}