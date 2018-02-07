package com.sakegakoi.rambo.kauwaud;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    Context context = null;
    SharedPreferences sharedPref = null;
    TextView highScore = null;
    TextView savedTime = null;
    private HighScoreDao highScoreDao;

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
        //highScore.setText(newHighScoreValue);
        //savedTime.setText(savedTimeValue);
        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "HighScore").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        highScoreDao = db.highScoreDao();
        List<HighScore> highScores = highScoreDao.getSortedAll();
        int N = highScores.size();
        final TextView[] myTextViews = new TextView[N];
        LinearLayout lView = (LinearLayout) findViewById(R.id.linearLayout);
        for (HighScore element : highScores) {
            final TextView score = new TextView(this);
            final TextView time = new TextView(this);
            score.setText(element.score + "");
            time.setText(element.savedTime);
            lView.addView(score);
            lView.addView(time);
            System.out.println(element.id);
            System.out.println(element.score);
            System.out.println(element.savedTime);
        }
    }

}
