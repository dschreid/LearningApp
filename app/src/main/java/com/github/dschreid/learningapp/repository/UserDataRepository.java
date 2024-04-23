package com.github.dschreid.learningapp.repository;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.github.dschreid.learningapp.database.UserDataDatabase;
import com.github.dschreid.learningapp.model.UserData;

/**
 * Repositoriy zur Verwaltung von Nutzerdaten
 *
 * @author dschreid
 */
public class UserDataRepository {
    private final UserDataDatabase database;

    public UserDataRepository(UserDataDatabase database) {
        this.database = database;
    }

    public void getUserData(Consumer<UserData> onResult) {
        CompletableFuture.runAsync(() -> onResult.accept(database.getDao().getUserData()));
    }

    public void updateUserData(UserData statistics) {
        CompletableFuture.runAsync(() -> database.getDao().updateUserData(statistics));
    }
}
