package com.github.dschreid.learningapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Vorlagen Modell
 *
 * @author dschreid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "lernvolagen")
public class LearningTemplate {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private String course;
    private long timeCreated;
    private int minuten;

    /**
     * Erstellt eine leere Vorlage
     *
     * @return leere Vorlage
     */
    public static LearningTemplate createEmpty() {
        LearningTemplate learningTemplate = new LearningTemplate();
        learningTemplate.setTimeCreated(System.currentTimeMillis());
        return learningTemplate;
    }

    public LearningUnit toLearningUnit() {
        return LearningUnit.of(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningTemplate that = (LearningTemplate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
