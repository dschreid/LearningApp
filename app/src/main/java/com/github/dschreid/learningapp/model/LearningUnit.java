package com.github.dschreid.learningapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lerneinheiten Modell
 *
 * @author dschreid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "lerneinheiten")
public class LearningUnit {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private int minutesStart;
    private int minutesLearned;
    private long timeStarted;
    private String course;
    private int evaluation;

    public static LearningUnit of(LearningTemplate template) {
        return builder()
                .course(template.getCourse())
                .minutesStart(template.getMinuten())
                .timeStartedNow()
                .build();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningUnit that = (LearningUnit) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder-Pattern für das Lerneinheiten-Modell
     *
     * @author dschreid
     */
    public static class Builder {
        private Long _id;
        private int _minutesStart;
        private int _minutesLearned;
        private long _timeStarted;
        private int _evaluation;
        private String _fach;

        public Builder course(String course) {
            this._fach = course;
            return this;
        }

        public Builder id(long id) {
            this._id = id;
            return this;
        }

        public Builder timeStartedNow() {
            this._timeStarted = System.currentTimeMillis();
            return this;
        }

        public Builder timeStarted(long timeStarted) {
            this._timeStarted = timeStarted;
            return this;
        }

        public Builder minutesLearned(int minutesLearned) {
            this._minutesLearned = minutesLearned;
            return this;
        }

        public Builder minutesStart(int minutesStart) {
            this._minutesStart = minutesStart;
            return this;
        }

        public Builder evaluate(int evaluation) {
            this._evaluation = evaluation;
            return this;
        }

        public LearningUnit build() {
            return new LearningUnit(_id, _minutesStart, _minutesLearned, _timeStarted, _fach, _evaluation);
        }
    }
}
