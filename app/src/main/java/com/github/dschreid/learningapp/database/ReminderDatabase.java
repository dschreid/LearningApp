package com.github.dschreid.learningapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.github.dschreid.learningapp.model.LearningUnit;
import com.github.dschreid.learningapp.model.Reminder;

/**
 * Datenbank, nach Room implementierung
 *
 * @author dschreid
 */
@Database(entities = {Reminder.class}, version = 1)
public abstract class ReminderDatabase extends RoomDatabase {
    public static ReminderDatabase createInstance(Context context) {
        return Room.databaseBuilder(context, ReminderDatabase.class, "rm_db").build();
    }

    public abstract ReminderDao getDao();
}
