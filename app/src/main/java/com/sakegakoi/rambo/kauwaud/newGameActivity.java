package com.sakegakoi.rambo.kauwaud;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;

public class newGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.eagle);
        String imageString = loadJSONFromAsset();
        final long timeInterval = 1000;
        try{
            JSONObject object = new JSONObject(imageString);
            JSONObject getObject = object.getJSONObject("images");
            final JSONArray getArray = getObject.getJSONArray("imageArray");
            final Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run(){
                    try {
                        int n = getRandomNumber(getArray);
                        JSONObject imageObject = getArray.getJSONObject(n - 1);
                        String imageName = imageObject.get("name").toString();
                        System.out.println(imageName);
                        int res = getResources().getIdentifier( imageName, "drawable", getApplicationContext().getPackageName());
                        image.setImageResource(res);
                        handler.postDelayed(this, 3000);//set to go off again in 3 seconds.
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(r,3000);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getRandomNumber(JSONArray imageArray) {
        Random random = new Random();
        int n = random.nextInt(imageArray.length() - 1) + 1;
        return n;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("image_info.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
