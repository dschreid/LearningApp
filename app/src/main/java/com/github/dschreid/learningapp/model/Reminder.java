package com.github.dschreid.learningapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
import java.util.Objects;

import com.github.dschreid.learningapp.common.ListConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Erinnerung Modell
 *
 * @author dschreid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "erinnerungen")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String course;
    private long date;
    @TypeConverters({ListConverter.class})
    private List<Integer> preReminders;

    public static Reminder createEmpty() {
        return new Reminder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reminder that = (Reminder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
