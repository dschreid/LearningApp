package com.github.dschreid.learningapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.github.dschreid.learningapp.model.Reminder;

/**
 * Data Access Object, nach Room implementierung
 *
 * @author dschreid
 */
@Dao
public interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("SELECT * FROM erinnerungen")
    List<Reminder> getAll();
}
