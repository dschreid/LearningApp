package com.github.dschreid.learningapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;

import com.github.dschreid.learningapp.model.LearningTemplate;

/**
 * Datenbank, nach Room implementierung
 *
 * @author dschreid
 */
@Database(entities = {LearningTemplate.class}, version = 1)
public abstract class LearningTemplateDatabase extends RoomDatabase {
    public static LearningTemplateDatabase createInstance(Context context) {
        return Room.databaseBuilder(context, LearningTemplateDatabase.class, "lt_db").build();
    }

    public abstract LearningTemplateDao getDao();
}
