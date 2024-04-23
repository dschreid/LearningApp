package com.github.dschreid.learningapp.common;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Klasse um eine Liste von Integern mithilfe von Gson
 * zu konvertieren
 *
 * @author dschreid
 */
public class ListConverter {
    @TypeConverter
    public static List<Integer> restoreList(String raw) {
        return new Gson().fromJson(raw, new TypeToken<List<Integer>>() {
        }.getType());
    }

    @TypeConverter
    public static String saveList(List<Integer> list) {
        return new Gson().toJson(list);
    }
}
