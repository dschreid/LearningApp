package com.github.dschreid.learningapp.database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

import com.github.dschreid.learningapp.model.UserData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Nutzerdaten-Datenbank
 * Speichert die Nutzerdaten in SharedPreferences on Android
 *
 * @author dschreid
 */
public class UserDataDatabase {

    public static UserDataDatabase createInstance(Context context) {
        return new UserDataDatabase(context);
    }

    private static final String KEY_LANG = "language";
    private static final String KEY_POINTS = "points";
    private static final String KEY_MINUTES = "minutes-learned";
    private static final String KEY_ITEMS = "bougt-items";

    @Getter
    private final UserDataDao dao;
    private final SharedPreferences storage;

    private UserDataDatabase(Context context) {
        this.storage = context.getSharedPreferences("statistics", Context.MODE_PRIVATE);
        this.dao = new UserDataDaoImpl(storage);
    }

    @RequiredArgsConstructor
    private static final class UserDataDaoImpl implements UserDataDao {
        private final SharedPreferences storage;

        @Override
        public UserData getUserData() {
            UserData userData = new UserData();
            userData.setLanguage(storage.getString(KEY_LANG, "de_DE"));
            userData.setPoints(storage.getInt(KEY_POINTS, 0));
            userData.setMinutesLearned(storage.getInt(KEY_MINUTES, 0));
            userData.setBoughtItems(storage.getStringSet(KEY_ITEMS, new HashSet<>()));
            return userData;
        }

        @Override
        public void updateUserData(UserData data) {
            SharedPreferences.Editor editor = storage.edit();
            editor.putString(KEY_LANG, data.getLanguage());
            editor.putInt(KEY_POINTS, data.getPoints());
            editor.putInt(KEY_MINUTES, data.getMinutesLearned());
            editor.putStringSet(KEY_ITEMS, data.getBoughtItems());
            editor.apply();
        }
    }
}
