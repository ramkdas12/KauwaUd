package com.sakegakoi.rambo.kauwaud;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Rambo on 07-02-2018.
 */
@Entity
public class HighScore {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int score;
    public String savedTime;
}
