package com.github.dschreid.learningapp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.common.TextFieldUtils;
import com.github.dschreid.learningapp.model.Reminder;
import com.github.dschreid.learningapp.viewmodel.ReminderListViewModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Fragment zum Erstellen oder Editieren einer Erinnerung
 *
 * @author dschreid
 * @see https://www.digitalocean.com/community/tutorials/android-date-time-picker-dialog
 */
public class EditReminderFragment extends Fragment {

    @Data
    @AllArgsConstructor
    private static class RemindMe {
        private int value;
        private CheckBox checkBox;
    }

    private final Reminder reminder;
    private final ReminderListViewModel viewModel;
    private final List<RemindMe> remindMes = new ArrayList<>();
    private final Calendar calendar;
    private View buttonDate;
    private View buttonTime;
    private View buttonConfirm;
    private View buttonBack;
    private EditText textCourse;
    private EditText textDate;
    private EditText textTime;

    public EditReminderFragment(Reminder reminder, ReminderListViewModel viewModel) {
        this.reminder = reminder;
        this.viewModel = viewModel;
        this.calendar = Calendar.getInstance();
        if (this.reminder.getId() != null)
            this.calendar.setTimeInMillis(this.reminder.getDate());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reminder_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.textCourse = view.findViewById(R.id.inputCourse);
        this.buttonDate = view.findViewById(R.id.btn_date);
        this.buttonTime = view.findViewById(R.id.btn_time);
        this.textDate = view.findViewById(R.id.in_date);
        this.textTime = view.findViewById(R.id.in_time);
        this.buttonConfirm = view.findViewById(R.id.buttonConfirm);
        this.buttonBack = view.findViewById(R.id.buttonBack);

        this.remindMes.add(new RemindMe(0, view.findViewById(R.id.remindMeNow)));
        this.remindMes.add(new RemindMe(15, view.findViewById(R.id.remindMe15)));
        this.remindMes.add(new RemindMe(30, view.findViewById(R.id.remindMe30)));
        this.remindMes.add(new RemindMe(60, view.findViewById(R.id.remindMe60)));

        if (reminder.getCourse() != null) this.textCourse.getText().append(reminder.getCourse());
        if (reminder.getDate() > 0) this.updateTime(reminder.getDate());

        for (RemindMe remindMe : remindMes) {
            if (reminder.getPreReminders() != null && reminder.getPreReminders().contains(remindMe.value))
                remindMe.getCheckBox().setChecked(true);
        }


        this.setupButtons();
    }

    private void setupButtons() {
        this.buttonDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (ignored, year, monthOfYear, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        this.updateTime(calendar);
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        });

        this.buttonTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (ignored, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        this.updateTime(calendar);
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        });

        buttonBack.setOnClickListener(v -> AppCommons.finishFragment(this));
        buttonConfirm.setOnClickListener(v -> {
            List<Integer> reminders = new ArrayList<>();

            for (RemindMe remindMe : remindMes) {
                if (remindMe.getCheckBox().isChecked()) reminders.add(remindMe.value);
            }


            if (reminders.isEmpty()) {
                for (RemindMe remindMe : remindMes) {
                    remindMe.getCheckBox().setError("You must choose at least one");
                }
                return;
            }

            boolean error = false;
            error = TextFieldUtils.checkAndGiveFeedback(getContext(), textCourse) || error;
            error = TextFieldUtils.checkAndGiveFeedback(getContext(), textDate) || error;
            error = TextFieldUtils.checkAndGiveFeedback(getContext(), textTime) || error;
            if (error) return;

            reminder.setCourse(textCourse.getText().toString());
            reminder.setDate(calendar.getTimeInMillis());
            reminder.setPreReminders(reminders);

            viewModel.addReminder(reminder);
            AppCommons.finishFragment(this);
        });
    }

    private void updateTime(long date) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(date);
        updateTime(instance);
    }

    private void updateTime(Calendar calendar) {
        try {
            this.textDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
            this.textTime.setText(SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(calendar.getTime()));
        } catch (IllegalArgumentException ignored) {

        }
    }

}
