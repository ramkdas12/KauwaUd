package com.sakegakoi.rambo.kauwaud;

import android.arch.persistence.room.Room;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "HighScore").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        HighScoreDao highScoreDao = db.highScoreDao();
        List<HighScore> highScores = highScoreDao.getSortedAll();
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        for (int i = 0; i <= highScores.size(); i++) {

            TableRow row = new TableRow(this);

            for (int j = 0; j < 2; j++) {

                TextView cell = new android.support.v7.widget.AppCompatTextView(this){
                    @Override
                    protected void onDraw(Canvas canvas) {
                        super.onDraw(canvas);
                        Rect rect = new Rect();
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(Color.BLUE);
                        paint.setStrokeWidth(2);
                        getLocalVisibleRect(rect);
                        canvas.drawRect(rect, paint);
                    }

                };
                if ( i == 0 ) {
                    if ( j == 0 ) {
                        cell.setText("High Score");
                    } else if ( j == 1 ){
                        cell.setText("Time Played");
                    }
                } else {
                    HighScore highScore = highScores.get(i - 1);
                    if ( j == 0 ) {
                        cell.setText(highScore.score + "");
                    } else if ( j == 1 ){
                        cell.setText(highScore.savedTime);
                    }
                }

                cell.setPadding(6, 4, 6, 4);
                row.addView(cell);

            }

            tableLayout.addView(row);
        }
    }
}
