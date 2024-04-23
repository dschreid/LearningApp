package com.github.dschreid.learningapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.model.LearningUnit;
import com.github.dschreid.learningapp.repository.LearningUnitRepository;
import lombok.Getter;

/**
 * ViewModel für eine LearningUnit
 *
 * @author dschreid
 */
public class LearningUnitViewModel extends ViewModel {
    private final LearningUnit data;
    private final LearningUnitRepository repository;

    @Getter
    private final MutableLiveData<LearningUnit> learningUnit;
    @Getter
    private final MutableLiveData<Integer> timer;
    @Getter
    private final MutableLiveData<Boolean> running;

    private LearningUnitViewModel(LearningUnit data) {
        this.data = data;
        this.repository = LearningApp.getInstance().getLearningUnitRepository();
        this.learningUnit = new MutableLiveData<>(data);
        this.timer = new MutableLiveData<>(data.getMinutesStart());
        this.running = new MutableLiveData<>(true);
    }

    public static LearningUnitViewModel of(LearningUnit learningUnit) {
        Objects.requireNonNull(learningUnit, "Template cannot be null");

        return new LearningUnitViewModel(learningUnit);
    }

    public void switchRunning() {
        this.running.postValue(Boolean.FALSE.equals(running.getValue()));
    }

    public void addMinute() {
        this.data.setMinutesLearned(data.getMinutesLearned() + 1);
        this.learningUnit.postValue(data);
        this.timer.postValue(getMinutesLeft());
    }

    public boolean shouldContinue() {
        return getMinutesLeft() > 0;
    }

    public int getMinutesLeft() {
        return data.getMinutesStart() - data.getMinutesLearned();
    }

    public void save() {
        this.repository.saveLearningUnit(data);
    }

    public void setEvaluation(int i) {
        this.data.setEvaluation(i);
        this.learningUnit.postValue(data);
        this.save();
    }
}
