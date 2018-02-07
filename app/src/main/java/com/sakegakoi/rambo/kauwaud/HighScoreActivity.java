package com.sakegakoi.rambo.kauwaud;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    Context context = null;
    SharedPreferences sharedPref = null;
    TextView highScore = null;
    TextView savedTime = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        context = getApplicationContext();
        highScore = findViewById(R.id.highScore);
        savedTime = findViewById(R.id.savedTime);
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long highScoreValue = sharedPref.getInt(getString(R.string.saved_high_score), 1);
        String savedTimeValue = sharedPref.getString(getString(R.string.savedTime), "01/01/2017");
        String newHighScoreValue = highScoreValue + "";
        System.out.println(highScoreValue);
        System.out.println(savedTimeValue);
        highScore.setText(newHighScoreValue);
        savedTime.setText(savedTimeValue);
    }

}
