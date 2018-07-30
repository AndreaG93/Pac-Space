package graziani.andrea.pacspace.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Vector;

import graziani.andrea.pacspace.R;
import graziani.andrea.pacspace.adapter.StatisticsListViewScoreAdapter;
import graziani.andrea.pacspace.adapter.StatisticsListViewScoreAdapterHeader;
import graziani.andrea.pacspace.game.logic.GameStatistics;
import graziani.andrea.pacspace.game.database.StatisticsDatabase;


public class ScoreActivity extends AppCompatActivity {

    /**
     * This method is used to initialize current activity.
     */
    private void initializationActivity() {

        // Retrieve context object
        final Context myContext = getApplicationContext();
        Vector<GameStatistics> myGameStatisticsVector;

        // Retrieve DB
        StatisticsDatabase myStatisticsDatabase = StatisticsDatabase.getInstance(myContext);
        myGameStatisticsVector = myStatisticsDatabase.get(8);

        /**
         * LstVwResult ListView initialization
         */
        ListView resultList = (ListView) findViewById(R.id.activity_score_LstVwScore);
        resultList.setAdapter(new StatisticsListViewScoreAdapter(myContext, myGameStatisticsVector));

        /**
         * LstVwScoreHeader ListView initialization
         */
        ListView resultListHeader = (ListView) findViewById(R.id.activity_score_LstVwScoreHeader);
        resultListHeader.setAdapter(new StatisticsListViewScoreAdapterHeader(myContext));


        /**
         * backToMainMenu Button initialization
         */
        Button backToMainMenuButton = (Button) findViewById(R.id.backToMainMenuButton);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(myContext, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myIntent);

            }
        });

        TextView myTextView = (TextView) findViewById(R.id.nobodyRank);
        if(myGameStatisticsVector.size() != 0)
            myTextView.setVisibility(View.INVISIBLE);




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        setTitle(R.string.str_scoreActivityTitle);
        initializationActivity();
    }
}
