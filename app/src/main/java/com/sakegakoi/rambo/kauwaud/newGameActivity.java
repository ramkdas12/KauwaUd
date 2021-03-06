package com.sakegakoi.rambo.kauwaud;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

public class newGameActivity extends AppCompatActivity {

    final Handler handler = new Handler();
    final long timeInterval = 2000;
    JSONObject imageLoaded = null;
    Runnable r = null;
    TextView score = null;
    TextView life = null;
    int gameScore = 0;
    int lifeScore = 3;
    Context context = null;
    private HighScoreDao highScoreDao;
    Button fly = null;
    Button notFly = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        final ImageView image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.eagle);
        score = findViewById(R.id.textValue);
        life = findViewById(R.id.lifeValue);
        fly = findViewById(R.id.fly);
        notFly = findViewById(R.id.noFly);
        life.setText("3");
        score.setText("0");
        String imageString = loadJSONFromAsset();
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();// disable dismiss by tapping outside of the dialog
        context = getApplicationContext();
        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "HighScore").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        highScoreDao = db.highScoreDao();
        try {
            JSONObject object = new JSONObject(imageString);
            JSONObject getObject = object.getJSONObject("images");
            final JSONArray getArray = getObject.getJSONArray("imageArray");
            r = new Runnable() {
                public void run() {
                    try {
                        fly.setEnabled(true);
                        notFly.setEnabled(true);
                        int n = getRandomNumber(getArray);
                        JSONObject imageObject = getArray.getJSONObject(n - 1);
                        imageLoaded = imageObject;
                        String imageName = imageObject.get("name").toString();
                        int res = getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());
                        image.setImageResource(res);

                        handler.postDelayed(this, timeInterval);//set to go off again in 3 seconds.
                        if (progress.isShowing()) {
                            progress.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(r, timeInterval);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkFlyable(View arg) {
        handler.removeCallbacksAndMessages(null);
        Button button = (Button) arg;
        String buttonText = button.getText().toString();
        fly.setEnabled(false);
        notFly.setEnabled(false);
        try {
            Boolean isFlyable = (Boolean) imageLoaded.get("flying");
            if (isFlyable && buttonText.equalsIgnoreCase("I fly")) {
                gameScore++;
            } else if (!isFlyable && buttonText.equalsIgnoreCase("I cannot")) {
                gameScore++;
            } else {
                lifeScore--;

                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                if (isFlyable) {
                    text = "I fly";
                } else {
                    text = "I can't fly";
                }
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            if (lifeScore == 0) {
                showDialog();
            } else {
                score = findViewById(R.id.textValue);
                String newScore = gameScore + "";
                String newLife = lifeScore + "";
                life.setText(newLife);
                score.setText(newScore);
                handler.postDelayed(r, timeInterval);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog() throws Resources.NotFoundException {
        handler.removeCallbacksAndMessages(null);
        final String finalScore = gameScore + "";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.gameOverTitle))
                .setMessage(finalScore)
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        getResources().getString(R.string.newGameString),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = getIntent();
                                Date date = new Date();
                                String newDate = date.toString();
                                HighScore highScore = new HighScore();
                                highScore.score = gameScore;
                                highScore.savedTime = newDate;
                                highScoreDao.insert(highScore);
                                finish();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton(
                        getResources().getString(R.string.gameEndString),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = getIntent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Date date = new Date();
                                String newDate = date.toString();
                                HighScore highScore = new HighScore();
                                highScore.score = gameScore;
                                highScore.savedTime = newDate;
                                highScoreDao.insert(highScore);
                                finish();
                                Intent parent = new Intent(getApplicationContext(), MainActivity.class);
                                parent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(parent);
                            }
                        }).setCancelable(false).show();
    }

    @Override
    public void onBackPressed() {
        try {
            handler.removeCallbacksAndMessages(null);
            final String finalScore = gameScore + "";
            new AlertDialog.Builder(this)
            .setTitle(getResources().getString(R.string.endGameText))
            .setMessage(getResources().getString(R.string.gameOverTitle) + " " + finalScore)
            .setIcon(
                getResources().getDrawable(
                    android.R.drawable.ic_dialog_alert))
            .setPositiveButton(
                getResources().getString(R.string.yesText),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    Intent intent = getIntent();
                    Date date = new Date();
                    String newDate = date.toString();
                    HighScore highScore = new HighScore();
                    highScore.score = gameScore;
                    highScore.savedTime = newDate;
                    highScoreDao.insert(highScore);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    }
                })
            .setNegativeButton(
                getResources().getString(R.string.noText),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        handler.postDelayed(r, timeInterval);
                    }
                }).setCancelable(false).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRandomNumber(JSONArray imageArray) {
        Random random = new Random();
        return random.nextInt(imageArray.length() - 1) + 1;
    }

    public String loadJSONFromAsset() {
        try {
            InputStream is = getAssets().open("image_info.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            String json = new String(buffer, "UTF-8");

            return json;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
