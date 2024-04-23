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
import com.github.dschreid.learningapp.model.Reminder;
import com.github.dschreid.learningapp.repository.ReminderRepository;
import lombok.Getter;


/**
 * ViewModel für eine Liste von Remindern
 *
 * @author dschreid
 */
public class ReminderListViewModel extends ViewModel {
    public static final ViewModelInitializer<ReminderListViewModel> INITIALIZER =
            new ViewModelInitializer<>(ReminderListViewModel.class, creationExtras -> {
                return new ReminderListViewModel(LearningApp.getInstance().getReminderRepository());
            });

    @Getter
    private MutableLiveData<List<Reminder>> reminders;
    private Set<Reminder> data;
    private ReminderRepository repository;

    public ReminderListViewModel(ReminderRepository repository) {
        this.data = new HashSet<>();
        this.reminders = new MutableLiveData<>();
        this.repository = repository;

        this.repository.fetchReminders(learningReminders -> {
            this.data.addAll(learningReminders);
            publishChanges();
        });
    }

    public void addReminder(Reminder reminder) {
        repository.saveReminder(reminder).whenComplete((savedReminder, throwable) -> {
            data.add(savedReminder);
            this.publishChanges();
        });
    }

    public void deleteReminder(Reminder reminder) {
        if (reminder.getId() == null) return;
        repository.deleteReminder(reminder);

        data.remove(reminder);
        this.publishChanges();
    }

    private void publishChanges() {
        List<Reminder> learningReminders = new ArrayList<>(data);
        learningReminders.sort(Comparator.comparing(Reminder::getId));
        reminders.postValue(learningReminders);
    }
}
