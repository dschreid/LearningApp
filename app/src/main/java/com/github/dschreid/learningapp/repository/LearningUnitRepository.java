package com.github.dschreid.learningapp.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.github.dschreid.learningapp.database.LearningUnitDatabase;
import com.github.dschreid.learningapp.model.LearningUnit;

/**
 * Repositoriy zur Verwaltung von Lerneinheiten
 *
 * @author dschreid
 */
public class LearningUnitRepository {
    private final LearningUnitDatabase database;

    public LearningUnitRepository(LearningUnitDatabase database) {
        this.database = database;
    }

    public void saveLearningUnit(LearningUnit learningUnit) {
        CompletableFuture.runAsync(() -> {
            long newId = database.getDao().insert(learningUnit);
            learningUnit.setId(newId);
        });
    }

    public void fetchLearningUnits(Consumer<List<LearningUnit>> onResult) {
        CompletableFuture.runAsync(() -> onResult.accept(database.getDao().getAll()));
    }
}
