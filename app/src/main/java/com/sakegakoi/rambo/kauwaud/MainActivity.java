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
        System.out.println("Let us start");
        Intent intent = new Intent(this, newGameActivity.class);
        intent.putExtra(MyText, "Let us play the game");
        startActivity(intent);
    }
}
