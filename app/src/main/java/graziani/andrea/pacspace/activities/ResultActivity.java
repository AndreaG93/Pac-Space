package graziani.andrea.pacspace.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

import graziani.andrea.pacspace.R;
import graziani.andrea.pacspace.activities.event.SaveDataOnDbEvent;
import graziani.andrea.pacspace.adapter.StatisticsListViewResultAdapter;
import graziani.andrea.pacspace.adapter.StatisticsListViewResultAdapterHeader;
import graziani.andrea.pacspace.adapter.StatisticsListViewResultAdapterObject;
import graziani.andrea.pacspace.game.difficulties.Easy;
import graziani.andrea.pacspace.game.difficulties.Hard;
import graziani.andrea.pacspace.game.difficulties.Normal;
import graziani.andrea.pacspace.game.logic.GameStatistics;
import graziani.andrea.pacspace.game.database.StatisticsDatabase;
import graziani.andrea.pacspace.utility.IntentHelper;
import graziani.andrea.pacspace.utility.Support;


public class ResultActivity extends AppCompatActivity {

    private int totalFruitsScore;
    private int totalTimeScore;
    private int totalEnemyScore;
    private int totalDifficultyScore;
    private GameStatistics myGameStatistics;

    /**
     * This method is used to initialize current activity.
     */
    private void initializationActivity() {

        final Context myContext = this.getApplicationContext();

        // Retrieve player's statistic
        IntentHelper myIntentHelper = IntentHelper.getInstance();
        this.myGameStatistics = (GameStatistics) myIntentHelper.getObjectForKey("statisticsObject");

        // Calculation Score
        this.totalDifficultyScore = this.myGameStatistics.getGameDifficulty().getDifficultyPoints();
        this.totalFruitsScore = this.myGameStatistics.getFruitTotalScore();
        this.totalTimeScore = this.myGameStatistics.getTimeTotalScore();
        this.totalEnemyScore = this.myGameStatistics.getEnemyTotalScore();
        this.myGameStatistics.setGameScore(this.totalDifficultyScore + this.totalFruitsScore + this.totalTimeScore + this.totalEnemyScore);

        /**
         * LstVwResult ListView initialization
         */
        ListView resultList = (ListView) findViewById(R.id.activity_result_LstVwResult);
        resultList.setAdapter(new StatisticsListViewResultAdapter(myContext, getVectorForListViewPopulation()));

        /**
         * LstVwResultHeader ListView initialization
         */
        ListView resultListHeader = (ListView) findViewById(R.id.activity_result_LstVwResultHeader);
        resultListHeader.setAdapter(new StatisticsListViewResultAdapterHeader(myContext));

        /**
         * totalResult TextView initialization
         */
        TextView totalResult = (TextView) findViewById(R.id.totalResult);
        totalResult.setText(String.valueOf(this.myGameStatistics.getGameScore()));

        /**
         * Retry Button initialization
         */
        Button retryButton = (Button) findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.openNewActivityWithoutHistory(myContext, GameActivity.class);
            }
        });

        /**
         * backToMainMenu Button initialization
         */
        Button backToMainMenuButton = (Button) findViewById(R.id.backToMainMenuButton);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.openNewActivityWithoutHistory(myContext, MainActivity.class);
            }
        });

        /**
         * Save Button initialization
         */
        Button saveButton = (Button) findViewById(R.id.saveResult);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDialog();
            }
        });

        if (this.myGameStatistics.getGameScore() > StatisticsDatabase.getInstance(myContext).getMaxScore()) {
            TextView newRecordTextView = (TextView) findViewById(R.id.record);
            newRecordTextView.setText(R.string.str_newRecord);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle(R.string.str_resultActivityTitle);
        initializationActivity();
    }

    private Vector<StatisticsListViewResultAdapterObject> getVectorForListViewPopulation() {
        Vector<StatisticsListViewResultAdapterObject> myVector = new Vector<>();

        // Fruits
        StatisticsListViewResultAdapterObject var0 = new StatisticsListViewResultAdapterObject();
        var0.setIcon(getApplicationContext().getDrawable(R.drawable.fruit_icon));
        var0.setName(getApplicationContext().getString(R.string.str_fruit));
        var0.setQuantity(String.valueOf(this.myGameStatistics.getFruits()));
        var0.setPoints(String.valueOf(this.totalFruitsScore));

        // Time
        StatisticsListViewResultAdapterObject var1 = new StatisticsListViewResultAdapterObject();
        var1.setIcon(getApplicationContext().getDrawable(R.drawable.vector_time));
        var1.setName(getApplicationContext().getString(R.string.str_time));
        var1.setQuantity(String.valueOf(this.myGameStatistics.getTimeRepresentationalString()));
        var1.setPoints(String.valueOf(this.totalTimeScore));

        // Difficulty
        StatisticsListViewResultAdapterObject var2 = new StatisticsListViewResultAdapterObject();
        var2.setIcon(getApplicationContext().getDrawable(R.drawable.vector_difficulty));
        var2.setName(getApplicationContext().getString(R.string.str_difficulty));

        // Enemy
        StatisticsListViewResultAdapterObject var3 = new StatisticsListViewResultAdapterObject();
        var3.setIcon(getApplicationContext().getDrawable(R.drawable.ghost_icon));
        var3.setName(getApplicationContext().getString(R.string.str_enemy));
        var3.setQuantity(String.valueOf(this.myGameStatistics.getKilledEnemies()));
        var3.setPoints(String.valueOf(this.totalEnemyScore));

        String myGameDifficulty = (this.myGameStatistics.getGameDifficulty()).getClass().getName();
        String myString;

        if (myGameDifficulty.equals(Easy.class.getName()))
            myString = getString(R.string.str_easy);
        else if (myGameDifficulty.equals(Normal.class.getName()))
            myString = getString(R.string.str_normal);
        else if (myGameDifficulty.equals(Hard.class.getName()))
            myString = getString(R.string.str_hard);
        else
            myString = getString(R.string.str_veryHard);

        var2.setQuantity(myString);
        var2.setPoints(String.valueOf(this.totalDifficultyScore));

        myVector.add(var0);
        myVector.add(var1);
        myVector.add(var2);
        myVector.add(var3);
        return myVector;
    }

    public void saveDialog() {

        LayoutInflater myInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View myView = myInflater.inflate(R.layout.custom_dialog, null);

        SaveDataOnDbEvent myEvent = new SaveDataOnDbEvent(this, (EditText) myView.findViewById(R.id.editText_insertName), this.myGameStatistics.getGameScore());

        // Constructs a new allocated "Builder" object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_message);
        builder.setCancelable(false);
        builder.setIcon(this.getDrawable(R.drawable.ghost_icon));
        builder.setView(myView);

        // setting ok button...
        builder.setPositiveButton(R.string.str_ok, myEvent);

        // Create dialog and show
        builder.create().show();
    }
}
