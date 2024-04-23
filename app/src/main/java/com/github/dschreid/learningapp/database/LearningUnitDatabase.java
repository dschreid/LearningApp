package com.github.dschreid.learningapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.github.dschreid.learningapp.model.LearningUnit;

/**
 * Datenbank, nach Room implementierung
 *
 * @author dschreid
 */
@Database(entities = {LearningUnit.class}, version = 1)
public abstract class LearningUnitDatabase extends RoomDatabase {
    public static LearningUnitDatabase createInstance(Context context) {
        return Room.databaseBuilder(context, LearningUnitDatabase.class, "lu_db").build();
    }

    public abstract LearningUnitDao getDao();
}
