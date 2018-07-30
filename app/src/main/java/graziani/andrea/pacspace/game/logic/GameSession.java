package graziani.andrea.pacspace.game.logic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Vector;

import graziani.andrea.pacspace.R;
import graziani.andrea.pacspace.activities.MainActivity;
import graziani.andrea.pacspace.game.GameDifficulty;
import graziani.andrea.pacspace.game.GameUserInputObserver;
import graziani.andrea.pacspace.game.GameStatus;
import graziani.andrea.pacspace.game.difficulties.Easy;
import graziani.andrea.pacspace.game.difficulties.Hard;
import graziani.andrea.pacspace.game.difficulties.Normal;
import graziani.andrea.pacspace.game.difficulties.VeryHard;
import graziani.andrea.pacspace.game.resources.GameBackground;
import graziani.andrea.pacspace.game.resources.GameMusic;
import graziani.andrea.pacspace.game.threads.SpritePositionUpdaterGameThread;
import graziani.andrea.pacspace.sprite.sprites.Fruit;
import graziani.andrea.pacspace.sprite.Ghost;
import graziani.andrea.pacspace.sprite.sprites.PacMan;
import graziani.andrea.pacspace.sprite.PowerUp;
import graziani.andrea.pacspace.sprite.Sprite;
import graziani.andrea.pacspace.game.threads.DrawerGameThread;
import graziani.andrea.pacspace.game.threads.BackgroundUpdaterGameThread;
import graziani.andrea.pacspace.game.threads.FrameUpdaterGameThread;
import graziani.andrea.pacspace.game.threads.TimerGameThread;
import graziani.andrea.pacspace.utility.Support;

/**
 * This class represents a game session.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class GameSession implements GameUserInputObserver, SurfaceHolder.Callback {

    // =================================================================== //
    // Android data...
    // =================================================================== //

    private static final String TAG = "GameSession";
    private Activity gameActivity;
    private Context context;
    private GameScreen gameScreen;

    // =================================================================== //
    // Game session data...
    // =================================================================== //

    private byte lives;
    private GameStatus gameStatus;
    private GameDifficulty gameDifficulty;
    private GameBackground gameBackground;
    private GameStatistics gameStatistics;
    private Vector<Sprite> sprites;
    private PacMan pacManPlayer;

    // =================================================================== //
    // Game threads...
    // =================================================================== //

    // This thread is used to update all sprite's position and to check collisions...
    private SpritePositionUpdaterGameThread spritePositionUpdaterGameThread;
    // This thread is used to animate sprites...
    private FrameUpdaterGameThread frameUpdaterGameThread;
    // This thread is used as a timer...
    private TimerGameThread timerGameThread;
    // This thread is used to animate background...
    private BackgroundUpdaterGameThread backgroundUpdaterGameThread;
    // This thread is used to draw...
    private DrawerGameThread drawerGameThread;

    // =================================================================== //
    // Game resources...
    // =================================================================== //

    private GameMusic gameMusic;
    private GameMusic pauseSoundEffect;

    // =================================================================== //
    // 'Override' methods...
    // =================================================================== //

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(TAG, "Surface created.");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i(TAG, "Surface changed.");
        this.loadingSprites();
        GameLogic.launchGame(this);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i(TAG, "Surface destroyed.");
        GameLogic.stopGame(this);
    }

    @Override
    public void updateGameAfterOnFlingGesture(double pAngleDirection) {
        this.gameStatus.updateGameAfterOnFlingGesture(this, pAngleDirection);
    }

    @Override
    public void updateGameAfterSingleTapGesture() {
        this.gameStatus.updateGameAfterSingleTapGesture(this);
    }

    @Override
    public void updateGameAfterDoubleTapGesture() {
        this.gameStatus.updateGameAfterDoubleTapGesture(this);
    }

    @Override
    public void updateGameAfterBackButtonPressing() {
        this.gameStatus.updateGameAfterBackButtonPressing(this);
    }

    // =================================================================== //
    // 'Public' methods...
    // =================================================================== //

    /**
     * Construct a newly allocated {@code GameSession} object.
     *
     * @param pActivity - Represents a {@code Context} object.
     */
    public GameSession(Activity pActivity) {

        // Loading needed data...
        // =================================================================== //
        this.gameActivity = pActivity;
        this.context = pActivity.getApplicationContext();
        this.gameStatistics = new GameStatistics();

        // Setting up game screen...
        // =================================================================== //
        this.gameScreen = new GameScreen(this.context);
        this.gameScreen.getHolder().addCallback(this);

        // Loading game resources...
        // =================================================================== //
        this.gameMusic = new GameMusic(this.context, R.raw.sunset, true);
        this.pauseSoundEffect = new GameMusic(this.context, R.raw.pause, false);

        // Loading game difficulty...
        // =================================================================== //
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.context);
        switch (Integer.valueOf(sharedPreference.getString("chosenGameDifficulty", "1"))) {
            case 0: {
                this.gameDifficulty = new Easy();
                break;
            }
            case 1: {
                this.gameDifficulty = new Normal();
                break;
            }
            case 2: {
                this.gameDifficulty = new Hard();
                break;
            }
            case 3: {
                this.gameDifficulty = new VeryHard();
                break;
            }
        }

        // Loading threads...
        // =================================================================== //
        this.spritePositionUpdaterGameThread = new SpritePositionUpdaterGameThread(this);
        this.frameUpdaterGameThread = new FrameUpdaterGameThread(this);
        this.timerGameThread = new TimerGameThread(this);
        this.backgroundUpdaterGameThread = new BackgroundUpdaterGameThread(this);
        this.drawerGameThread = new DrawerGameThread(this);
    }

    /**
     * This method is used to loading sprite images.
     */
    private void loadingSprites() {

        // Creating sprite vector...
        // =================================================================== //
        this.sprites = new Vector<>();

        // Loading bitmaps
        // =================================================================== //
        Bitmap myPlayerBitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pacman_sprite);
        Bitmap myGhostBitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.ghost_sprite);
        Bitmap myFruitBitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.fruit_sprite);
        Bitmap myPowerUpBitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.power_up_sprite);
        Bitmap myGameBackground = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.background);


        // Loading background...
        // =================================================================== //
        this.gameBackground = new GameBackground(Support.getResizedBitmap(myGameBackground, this.gameScreen.getHeight(), this.gameScreen.getWidth()));

        // Loading sprite...
        // =================================================================== //

        // Add enemies...
        for (int i = 1; i <= this.gameDifficulty.getNumberOfEnemies(); i++)
            this.sprites.add(new Ghost(myGhostBitmap));

        // Add fruits...
        for (int i = 1; i <= this.gameDifficulty.getNumberOfFruits(); i++)
            this.sprites.add(new Fruit(myFruitBitmap));

        // Add power up...
        this.sprites.add(new PowerUp(myPowerUpBitmap));

        // Add player...
        this.sprites.add(new PacMan(myPlayerBitmap));

        // Resize bitmap sprite...
        for (Sprite obj : this.sprites)
            obj.updateBitmap(this.gameScreen.getHeight(), this.gameScreen.getWidth());
    }

    /**
     *
     */
    public void displayPauseMessage() {
        Log.i(TAG, "Building pause message.");

        // This local class represents a button used in a pause dialog...
        // =================================================================== //
        abstract class myPauseDialogButton implements DialogInterface.OnClickListener {

            GameSession localGameSession;

            myPauseDialogButton(GameSession gameSession){
                this.localGameSession = gameSession;
            }
        }

        // "resumeGameEvent"
        // =================================================================== //
        myPauseDialogButton resumeGameEvent = new myPauseDialogButton(this) {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GameLogic.resumeGame(this.localGameSession);
                dialogInterface.dismiss();
            }
        };

        // "resetGameEvent"
        // =================================================================== //
        myPauseDialogButton resetGameEvent = new myPauseDialogButton(this) {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GameLogic.resetGame(this.localGameSession);
                dialogInterface.dismiss();
            }
        };

        // "backToMainMenuEvent"
        // =================================================================== //
        myPauseDialogButton backToMainMenuEvent = new myPauseDialogButton(this) {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Support.openNewActivityWithoutHistory(context, MainActivity.class);
                dialogInterface.dismiss();
            }
        };

        // Constructs a new allocated "AlertDialog.Builder" object...
        // =================================================================== //
        AlertDialog.Builder builder = new AlertDialog.Builder(this.gameActivity);
        builder.setTitle(R.string.str_pause);
        builder.setCancelable(false);
        builder.setIcon(this.context.getDrawable(R.drawable.ghost_icon));

        // Setting up buttons...
        builder.setNegativeButton(R.string.str_resume, resumeGameEvent);
        builder.setPositiveButton(R.string.str_backToMainMenu, resetGameEvent);
        builder.setNeutralButton(R.string.str_retry, backToMainMenuEvent);

        // Custom runnable...
        // =================================================================== //
        class myCustomRunnable implements Runnable {

            private AlertDialog alertDialog;

            public myCustomRunnable(AlertDialog pAlertDialog){
                this.alertDialog = pAlertDialog;
            }

            @Override
            public void run() {
                alertDialog.show();
            }
        }


        this.gameActivity.runOnUiThread(new myCustomRunnable(builder.create()));
    }

    /**
     * This method is used to animate game background.
     */
    public void updateBackground() {
        this.gameBackground.updatePositionBackground(this.gameScreen.getWidth());
    }

    /**
     * This method is used to update sprite position.
     */
    public void updateSpritePosition() {
    }

    /**
     * This method is used to update sprite frame.
     */
    public void updateFrame() {
    }

    /**
     * This method is used to draw on surface.
     *
     * @param pCanvas - Represents a {@code Canvas} object.
     */
    public void draw(Canvas pCanvas) {
        this.gameStatus.updateGameScreen(this, pCanvas);
    }


    public void drawSprites() {
    }

    public void drawHUD(Canvas pCanvas) {
        this.gameScreen.drawHUD(pCanvas, this);
    }

    public void drawBackground(Canvas pCanvas) {
        this.gameScreen.drawBackground(pCanvas, this.gameBackground);
    }

    public void drawStartMessage(Canvas pCanvas) {
        this.gameScreen.drawStartMessage(pCanvas);
    }

    // =================================================================== //
    // Getter/Setter methods...
    // =================================================================== //

    public SpritePositionUpdaterGameThread getSpritePositionUpdaterGameThread() {
        return spritePositionUpdaterGameThread;
    }

    public FrameUpdaterGameThread getFrameUpdaterGameThread() {
        return frameUpdaterGameThread;
    }

    public TimerGameThread getTimerGameThread() {
        return timerGameThread;
    }

    public BackgroundUpdaterGameThread getBackgroundUpdaterGameThread() {
        return backgroundUpdaterGameThread;
    }

    public GameMusic getGameMusic() {
        return gameMusic;
    }

    public GameMusic getPauseSoundEffect() {
        return pauseSoundEffect;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public PacMan getPacManPlayer() {
        return pacManPlayer;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public GameBackground getGameBackground() {
        return gameBackground;
    }

    public void setGameBackground(GameBackground gameBackground) {
        this.gameBackground = gameBackground;
    }

    public Vector<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(Vector<Sprite> sprites) {
        this.sprites = sprites;
    }

    public GameStatistics getGameStatistics() {
        return gameStatistics;
    }

    public void setGameStatistics(GameStatistics gameStatistics) {
        this.gameStatistics = gameStatistics;
    }

    public DrawerGameThread getDrawerGameThread() {
        return drawerGameThread;
    }

    public void setDrawerGameThread(DrawerGameThread drawerGameThread) {
        this.drawerGameThread = drawerGameThread;
    }

    public void updateTimer() {
        this.getGameStatistics().incrementSurvivalTimeByOneSecond();

    }


    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public Context getContext() {
        return context;
    }


    public byte getLives() {
        return lives;
    }
}