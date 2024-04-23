package com.github.dschreid.learningapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.adapter.ReminderListAdapter;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.common.CommonViewModelFactory;
import com.github.dschreid.learningapp.model.Reminder;
import com.github.dschreid.learningapp.service.ReminderService;
import com.github.dschreid.learningapp.viewmodel.ReminderListViewModel;

/**
 * Fragment zur Listung und Manipulieren von Erinnerungen
 * Beim schließen dieses Fragmentes, werden die Erinnerungen mit dem Betriebssystem synchronisiert
 *
 * @author dschreid
 */
public class ListRemindersFragment extends Fragment {

    private ReminderService reminderService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.reminderService = LearningApp.getInstance().getReminderService();
        this.reminderService.requestPermission(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reminder_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewModelProvider viewModelProvider = CommonViewModelFactory.createViewModelProvider(this);
        ReminderListViewModel viewModel = viewModelProvider.get(ReminderListViewModel.class);

        view.findViewById(R.id.buttonBack).setOnClickListener(v -> AppCommons.finishFragment(this));
        view.findViewById(R.id.createReminder).setOnClickListener(v -> {
            AppCommons.transitionToFragment(this, new EditReminderFragment(Reminder.createEmpty(), viewModel), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        });

        RecyclerView table = view.findViewById(R.id.list);
        table.setLayoutManager(new LinearLayoutManager(requireContext()));
        table.setAdapter(new ReminderListAdapter(this, this.getLifecycle(), viewModel));
    }

    @Override
    public void onStop() {
        super.onStop();

        this.reminderService.refreshReminders();
    }


}