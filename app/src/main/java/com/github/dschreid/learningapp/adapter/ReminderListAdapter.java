package com.github.dschreid.learningapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.model.Reminder;
import com.github.dschreid.learningapp.view.EditReminderFragment;
import com.github.dschreid.learningapp.viewmodel.ReminderListViewModel;

/**
 * ReclyclerView Adapter für Erinnerungen
 *
 * @author dschreid
 */
public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderListViewHolder> implements LifecycleOwner {
    private final Fragment origin;
    private final List<Reminder> data;
    private final Lifecycle lifecycle;
    private final ReminderListViewModel viewModel;

    public ReminderListAdapter(Fragment origin, Lifecycle lifecycle, ReminderListViewModel viewModel) {
        this.origin = origin;
        this.data = new ArrayList<>();
        this.lifecycle = lifecycle;
        this.viewModel = viewModel;
        this.viewModel.getReminders().observe(this, this::publishUpdate);
    }

    private void publishUpdate(List<Reminder> reminders) {
        this.data.clear();
        this.data.addAll(reminders);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReminderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_entry, parent, false);
        return new ReminderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderListViewHolder holder, int position) {
        Reminder reminder = data.get(position);

        View view = holder.itemView;
        TextView name = view.findViewById(R.id.name);
        TextView minutes = view.findViewById(R.id.zeit);
        TextView course = view.findViewById(R.id.course);

        View edit = view.findViewById(R.id.buttonEdit);
        View delete = view.findViewById(R.id.buttonDelete);

        delete.setOnClickListener(v -> {
            viewModel.deleteReminder(reminder);
        });


        edit.setOnClickListener(v -> {
            AppCommons.transitionToFragment(origin, new EditReminderFragment(reminder, viewModel), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        });

        course.setText(reminder.getCourse());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycle;
    }

    public static class ReminderListViewHolder extends RecyclerView.ViewHolder {
        public ReminderListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
