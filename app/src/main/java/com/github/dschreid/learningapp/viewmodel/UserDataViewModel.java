package com.github.dschreid.learningapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.model.UserData;
import com.github.dschreid.learningapp.repository.UserDataRepository;
import lombok.Getter;

/**
 * ViewModel für UserData
 *
 * @author dschreid
 */
public class UserDataViewModel extends ViewModel {
    public static final ViewModelInitializer<UserDataViewModel> INITIALIZER =
            new ViewModelInitializer<>(UserDataViewModel.class, creationExtras -> {
                return new UserDataViewModel(LearningApp.getInstance().getUserDataRepository());
            });

    @Getter
    private MutableLiveData<UserData> userData;
    private UserData modelData;
    private UserDataRepository repository;

    public UserDataViewModel(UserDataRepository repository) {
        this.userData = new MutableLiveData<>();
        this.repository = repository;

        this.repository.getUserData(this::onUserDataChange);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCleared() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onUserDataChange(UserData data) {
        if (Objects.equals(this.modelData, data))
            return;

        this.modelData = data;
        this.userData.postValue(modelData);
    }

    public void addPoints(int points) {
        UserData modelData = userData.getValue();
        if (modelData == null) return;

        modelData.setPoints(modelData.getPoints() + points);
        repository.updateUserData(modelData);
        userData.postValue(modelData);
    }

    public void takePoints(int points) {
        UserData modelData = userData.getValue();
        if (modelData == null) return;

        modelData.setPoints(Math.max(0, modelData.getPoints() - points));
        repository.updateUserData(modelData);
        publishChanges();
    }

    public void addMinutesLearned(int minutes) {
        if (modelData == null) return;

        modelData.setMinutesLearned(modelData.getMinutesLearned() + minutes);
        repository.updateUserData(modelData);
        publishChanges();
    }

    public void addMinutesAndPoints(int sum) {
        if (modelData == null) return;

        modelData.setMinutesLearned(modelData.getMinutesLearned() + sum);
        modelData.setPoints(modelData.getPoints() + sum);
        repository.updateUserData(modelData);
        publishChanges();
    }

    public void addBoughtItem(String id) {
        if (modelData == null) return;

        modelData.getBoughtItems().add(id);
        repository.updateUserData(modelData);
        publishChanges();
    }

    public int getPoints() {
        if (modelData == null) return 0;

        return modelData.getPoints();
    }

    public boolean hasBoughtItem(String id) {
        if (modelData == null) return false;

        return modelData.getBoughtItems().contains(id);
    }

    private void publishChanges() {
        this.userData.postValue(modelData);
        EventBus.getDefault().post(modelData);
    }
}
