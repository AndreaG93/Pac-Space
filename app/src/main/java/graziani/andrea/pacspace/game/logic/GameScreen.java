package graziani.andrea.pacspace.game.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.view.SurfaceView;
import android.view.View;

import graziani.andrea.pacspace.R;
import graziani.andrea.pacspace.game.resources.GameBackground;

public class GameScreen extends SurfaceView {

    private Context context;
    private Paint paint;

    public GameScreen(Context pContext) {
        super(pContext);

        this.context = pContext;

        // Setting up "Paint" objects...
        // =================================================================== //
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        this.paint.setAntiAlias(true);
        this.paint.setTextSize(50);
        this.paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));

/*
        // Enable fullscreen mode...
        // =================================================================== //
        this.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
                */
    }

    /**
     * This method is used to draw start message on current surface.
     *
     * @param pCanvas - Represents a {@code Canvas} object.
     */
    public void drawStartMessage(Canvas pCanvas) {

        int posX = this.getWidth() / 3;
        int posY = this.getHeight() / 2;

        pCanvas.drawText(this.context.getString(R.string.str_tapToStart), posX, posY, this.paint);
    }

    /**
     * This method is used to draw a background on current surface.
     *
     * @param pCanvas         - Represents a {@code Canvas} object.
     * @param pGameBackground - Represents a {@code GameBackground} object.
     */
    public void drawBackground(Canvas pCanvas, GameBackground pGameBackground) {

        Bitmap myBackground = pGameBackground.getBackground();
        int currentBackgroundPosition = pGameBackground.getPositionBackground();

        // Drawing...
        pCanvas.drawBitmap(myBackground, currentBackgroundPosition, 0, null);
        pCanvas.drawBitmap(myBackground, currentBackgroundPosition - this.getWidth(), 0, null);
    }

    /**
     * This method is used to draw an HUD.
     *
     * @param pCanvas      - Represents a {@code Canvas} object.
     * @param pGameSession - Represents a {@code pGameSession} object.
     */
    public void drawHUD(Canvas pCanvas, GameSession pGameSession) {

        // Life...
        String myVar0 = "LIVES x " + String.valueOf(pGameSession.getLives()) + " - ";
        // Fruits
        String myVar1 = "FRUITS x " + String.valueOf(pGameSession.getGameStatistics().getFruits()) + " - ";
        // Time
        String myVar2 = "TIME: " + String.valueOf(DateFormat.format("mm:ss", pGameSession.getGameStatistics().getSurvivalTime()));

        // Drawing...
        pCanvas.drawText(myVar0 + myVar1 + myVar2, 2, this.getHeight() / 45, this.paint);
    }




}