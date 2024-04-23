package com.github.dschreid.learningapp.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.github.dschreid.learningapp.database.ReminderDatabase;
import com.github.dschreid.learningapp.model.Reminder;

/**
 * Repositoriy zur Verwaltung von Erinnerungen
 *
 * @author dschreid
 */
public class ReminderRepository {
    private final ReminderDatabase database;

    public ReminderRepository(ReminderDatabase database) {
        this.database = database;
    }

    public CompletableFuture<Reminder> saveReminder(Reminder reminder) {
        return CompletableFuture.supplyAsync((() -> {
            long newId = database.getDao().insert(reminder);
            reminder.setId(newId);
            return reminder;
        }));
    }

    public void fetchReminders(Consumer<List<Reminder>> onResult) {
        CompletableFuture.runAsync(() -> onResult.accept(database.getDao().getAll()));
    }

    public void deleteReminder(Reminder reminder) {
        CompletableFuture.runAsync(() -> database.getDao().delete(reminder));
    }
}
