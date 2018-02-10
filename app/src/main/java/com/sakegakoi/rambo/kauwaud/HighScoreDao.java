package com.sakegakoi.rambo.kauwaud;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Rambo on 07-02-2018.
 */
@Dao
public interface HighScoreDao {
    @Query("SELECT * FROM HighScore")
    List<HighScore> getAll();

    @Query("SELECT * FROM HighScore ORDER BY score")
    List<HighScore> getSortedAll();

    @Insert
    void insert(HighScore highScore);

    @Delete
    void deleteHighScores(HighScore... highScores);

}
