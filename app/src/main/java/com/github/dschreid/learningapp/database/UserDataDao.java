package com.github.dschreid.learningapp.database;

import com.github.dschreid.learningapp.model.UserData;

/**
 * Nutzerdaten Data Access Object
 *
 * @author dschreid
 */
public interface UserDataDao {
    UserData getUserData();

    void updateUserData(UserData data);
}
