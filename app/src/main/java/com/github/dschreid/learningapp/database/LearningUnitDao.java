package com.github.dschreid.learningapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.github.dschreid.learningapp.model.LearningUnit;

/**
 * Data Access Object, nach Room implementierung
 *
 * @author dschreid
 */
@Dao
public interface LearningUnitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(LearningUnit learningUnit);

    @Delete
    void delete(LearningUnit learningUnit);

    @Query("SELECT * FROM lerneinheiten")
    List<LearningUnit> getAll();
}
