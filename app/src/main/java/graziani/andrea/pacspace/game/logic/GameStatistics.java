package graziani.andrea.pacspace.game.logic;

import android.text.format.DateFormat;

import java.sql.Time;

import graziani.andrea.pacspace.game.GameDifficulty;

/**
 * The {@code GameStatistics} is used to represent player's statistics.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class GameStatistics {

    private static final String MY_TIME_FORMAT = "mm:ss";

    private String playerName;
    private int killedEnemies;
    private int gameScore;
    private int fruits;
    private GameDifficulty gameDifficulty;
    private Time survivalTime;

    public GameStatistics() {
        this.survivalTime = new Time(0);

        this.gameScore = 0;
        this.fruits = 0;
        this.killedEnemies = 0;
    }

    /**
     * This method is used to increment "Survival Time" by one second.
     */
    public void incrementSurvivalTimeByOneSecond() {
        this.survivalTime.setTime(this.survivalTime.getTime() + 1000);
    }

    /**
     * This method is used to increment "Fruits" by one.
     */
    public void addOneFruit() {
        this.fruits++;
    }


    /**
     * This method is used to increment "Killed Enemies" by one.
     */
    public void addOneKilledEnemy() {
        this.killedEnemies++;
    }

    /**
     * This method is used to get a representational string of time
     *
     * @return A {@code String} object.
     */
    public String getTimeRepresentationalString() {
        return String.valueOf(DateFormat.format(MY_TIME_FORMAT, this.getSurvivalTime()));
    }

    /**
     * This method is used to get score according taken fruits.
     *
     * @return An {@code int}.
     */
    public int getFruitTotalScore() {
        return this.gameDifficulty.getFruitsPoints() * this.fruits;
    }

    /**
     * This method is used to get score according play time.
     *
     * @return An {@code int}.
     */
    public int getTimeTotalScore() {
        return this.gameDifficulty.getTimePoints() * ((int) (this.survivalTime.getTime() / 1000));
    }

    /**
     * This method is used to get score according to killed enemies.
     *
     * @return An {@code int}.
     */
    public int getEnemyTotalScore() {
        return this.gameDifficulty.getDifficultyPoints() * this.killedEnemies;
    }

    /***********************************************************************************************
     * Getter and Setter methods
     */

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public Time getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(Time survivalTime) {
        this.survivalTime = survivalTime;
    }

    public int getFruits() {
        return fruits;
    }

    public void setFruits(int fruits) {
        this.fruits = fruits;
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }
}
