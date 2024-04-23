package com.github.dschreid.learningapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.model.LearningUnit;
import com.github.dschreid.learningapp.repository.LearningUnitRepository;


/**
 * ViewModel für eine Liste von Lerneinheiten
 * @author dschreid
 */
public class LearningUnitListViewModel extends ViewModel {
    public static final ViewModelInitializer<LearningUnitListViewModel> INITIALIZER =
            new ViewModelInitializer<>(LearningUnitListViewModel.class, creationExtras -> {
                return new LearningUnitListViewModel(LearningApp.getInstance().getLearningUnitRepository());
            });

    private Set<LearningUnit> data;
    private MutableLiveData<List<LearningUnit>> learningUnits;
    private LearningUnitRepository repository;

    public LearningUnitListViewModel(LearningUnitRepository repository) {
        this.data = new HashSet<>();
        this.learningUnits = new MutableLiveData<>();
        this.repository = repository;

        this.repository.fetchLearningUnits(data -> {
            this.data.addAll(data);
            publishChanges();
        });
    }

    private void publishChanges() {
        ArrayList<LearningUnit> sorted = new ArrayList<>(data);
        sorted.sort(Comparator.comparing(LearningUnit::getTimeStarted).reversed());
        learningUnits.postValue(sorted);
    }

    public MutableLiveData<List<LearningUnit>> getLearningUnits() {
        return learningUnits;
    }

}
