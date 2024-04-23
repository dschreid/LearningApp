package com.github.dschreid.learningapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.model.LearningTemplate;
import com.github.dschreid.learningapp.repository.LearningTemplateRepository;
import lombok.Getter;


/**
 * ViewModel für eine Liste von Vorlagen
 * @author dschreid
 */
public class LearningTemplateListViewModel extends ViewModel {
    public static final ViewModelInitializer<LearningTemplateListViewModel> INITIALIZER =
            new ViewModelInitializer<>(LearningTemplateListViewModel.class, creationExtras -> {
                return new LearningTemplateListViewModel(LearningApp.getInstance().getLearningTemplateRepository());
            });

    @Getter
    private MutableLiveData<List<LearningTemplate>> templates;
    private Set<LearningTemplate> data;
    private LearningTemplateRepository repository;

    public LearningTemplateListViewModel(LearningTemplateRepository repository) {
        this.data = new HashSet<>();
        this.templates = new MutableLiveData<>();
        this.repository = repository;

        this.repository.fetchTemplates(learningTemplates -> {
            this.data.addAll(learningTemplates);
            publishChanges();
        });
    }

    public void addTemplate(LearningTemplate template) {
        repository.saveTemplate(template).whenComplete((savedTemplate, throwable) -> {
            data.add(savedTemplate);
            this.publishChanges();
        });
    }

    public void deleteTemplate(LearningTemplate template) {
        if (template.getId() == null) return;
        repository.deleteTemplate(template);

        data.remove(template);
        this.publishChanges();
    }

    private void publishChanges() {
        List<LearningTemplate> learningTemplates = new ArrayList<>(data);
        learningTemplates.sort(Comparator.comparing(LearningTemplate::getTimeCreated).reversed());
        templates.postValue(learningTemplates);
    }
}
