package com.zybooks.mymathmaster.repo;

import android.content.Context;
import androidx.room.Room;
import com.zybooks.mymathmaster.model.MathCategory;

public class MathCategoryRepository {
    private AppDatabase appDatabase;

    public MathCategoryRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "math-master-db").build();
    }

    public MathCategory getMathCategoryById(int id) {
        return appDatabase.mathCategoryDao().getMathCategoryById(id);
    }

    public void insertOrUpdate(MathCategory mathCategory) {
        MathCategoryDao dao = appDatabase.mathCategoryDao();
        if (mathCategory.id == 0) {
            dao.insert(mathCategory);
        } else {
            dao.update(mathCategory);
        }
    }

}
