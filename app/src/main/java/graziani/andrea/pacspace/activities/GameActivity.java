package graziani.andrea.pacspace.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;
import graziani.andrea.pacspace.game.logic.GameSession;
import graziani.andrea.pacspace.utility.Support;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;

/**
 * This class represents a {@code GameActivity} whose responsibilities are detecting user inputs and notify them to current
 * game session.furthermore it displays game screen.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String TAG = "GameActivity";
    private GestureDetectorCompat gestureDetectorCompat;
    private GameSession gameSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        // =================================================================== //


        // Loading current game session...
        // =================================================================== //
        this.gameSession = new GameSession(GameActivity.this);

        // Setting up "Gesture detection"...
        // =================================================================== //
        this.gestureDetectorCompat = new GestureDetectorCompat(this.getApplicationContext(), this);
        this.gestureDetectorCompat.setOnDoubleTapListener(this);

        // Display game screen...
        // =================================================================== //
        setContentView(this.gameSession.getGameScreen());
        hideSystemUI();

        // Loading complete...
        Log.i(TAG, String.format(Locale.ENGLISH, "'%s' successfully loaded.", this.getClass().getSimpleName()));
    }

    public void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.i(TAG, "'DoubleTap' detected.");
        this.gameSession.updateGameAfterDoubleTapGesture();
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i(TAG, "'SingleTapUp' detected.");
        this.gameSession.updateGameAfterSingleTapGesture();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.i(TAG, "'Fling' detected.");

        float x1 = motionEvent.getX();
        float y1 = motionEvent.getY();

        float x2 = motionEvent1.getX();
        float y2 = motionEvent1.getY();

        this.gameSession.updateGameAfterOnFlingGesture(Support.getAngle(x1, y1, x2, y2));
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        // "Back Button" pressed...
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.i(TAG, "'Back Button' pressed.");
            this.gameSession.updateGameAfterBackButtonPressing();
            return true;
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}