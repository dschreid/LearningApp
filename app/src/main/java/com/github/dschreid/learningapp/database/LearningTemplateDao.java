package com.github.dschreid.learningapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.github.dschreid.learningapp.model.LearningTemplate;

/**
 * Data Access Object, nach Room implementierung
 *
 * @author dschreid
 */
@Dao
public interface LearningTemplateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(LearningTemplate template);

    @Delete
    void delete(LearningTemplate template);

    @Query("SELECT * FROM lernvolagen")
    List<LearningTemplate> getAll();
}
