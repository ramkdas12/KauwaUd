package com.sakegakoi.rambo.kauwaud;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Rambo on 08-02-2018.
 */

@Database(entities = {HighScore.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase{
    public abstract HighScoreDao highScoreDao();
}
