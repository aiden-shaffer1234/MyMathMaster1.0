package com.zybooks.mymathmaster.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MathCategory {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int correctAddition;
    public int incorrectAddition;
    public int correctSubtraction;
    public int incorrectSubtraction;
    public int correctMultiplication;
    public int incorrectMultiplication;
    public int correctDivision;
    public int incorrectDivision;
}
