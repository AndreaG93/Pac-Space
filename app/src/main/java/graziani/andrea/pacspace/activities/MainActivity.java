package graziani.andrea.pacspace.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import graziani.andrea.pacspace.R;
import graziani.andrea.pacspace.activities.setting.SettingActivity;

/**
 * This class represents main activity.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // =================================================================== //
        // UI initialization...
        // =================================================================== //

        // "startButton" initialization...
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });

        // "settingButton" initialization...
        findViewById(R.id.settingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingActivity();
            }
        });

        // "scoreButton" initialization...
        findViewById(R.id.scoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoreActivity();
            }
        });
    }

    /**
     * This method is used to starts {@code GameActivity} activity.
     */
    private void openGameActivity(){
        startActivity(new Intent(this, GameActivity.class));
    }

    /**
     * This method is used to starts {@code ScoreActivity} activity.
     */
    private void openScoreActivity(){
        startActivity(new Intent(this, ScoreActivity.class));
    }

    /**
     * This method is used to starts {@code SettingActivity} activity.
     */
    private void openSettingActivity(){
        startActivity(new Intent(this, SettingActivity.class));
    }
}