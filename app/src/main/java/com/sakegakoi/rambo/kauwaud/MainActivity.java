package com.sakegakoi.rambo.kauwaud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String MyText = "com.sakegakoi.rambo.kauwaud.string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, newGameActivity.class);
        startActivity(intent);
    }
    public void highScore(View view){
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }
}
