package com.zybooks.mymathmaster.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.zybooks.mymathmaster.model.MathCategory;

@Database(entities = {MathCategory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MathCategoryDao mathCategoryDao();
}
