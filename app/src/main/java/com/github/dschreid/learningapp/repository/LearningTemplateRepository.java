package com.github.dschreid.learningapp.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.github.dschreid.learningapp.database.LearningTemplateDatabase;
import com.github.dschreid.learningapp.model.LearningTemplate;

/**
 * Repositoriy zur Verwaltung von Vorlagen
 *
 * @author dschreid
 */
public class LearningTemplateRepository {
    private final LearningTemplateDatabase database;

    public LearningTemplateRepository(LearningTemplateDatabase database) {
        this.database = database;

        fetchTemplates(learningTemplates -> {
            if (learningTemplates.size() == 0) saveDefaults();
        });
    }

    private void saveDefaults() {
        LearningTemplate learningTemplate = new LearningTemplate(null, "Example", "Math", System.currentTimeMillis(), 60);
        saveTemplate(learningTemplate);
    }

    public CompletableFuture<LearningTemplate> saveTemplate(LearningTemplate template) {
        return CompletableFuture.supplyAsync(() -> {
            long newId = database.getDao().insert(template);
            template.setId(newId);
            return template;
        });
    }

    public void fetchTemplates(Consumer<List<LearningTemplate>> onResult) {
        CompletableFuture.runAsync(() -> onResult.accept(database.getDao().getAll()));
    }

    public void deleteTemplate(LearningTemplate template) {
        CompletableFuture.runAsync(() -> database.getDao().delete(template));
    }
}
