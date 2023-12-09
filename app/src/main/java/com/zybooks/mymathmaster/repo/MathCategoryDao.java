package com.zybooks.mymathmaster.repo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.mymathmaster.model.MathCategory;

@Dao
public interface MathCategoryDao {
    @Insert
    void insert(MathCategory mathCategory);

    @Query("SELECT * FROM MathCategory WHERE id = :id")
    MathCategory getMathCategoryById(int id);

    @Update
    void update(MathCategory mathCategory);


    // Add more queries as needed for updating and retrieving specific data
}
