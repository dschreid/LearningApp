package com.github.dschreid.learningapp.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.model.Reminder;
import com.github.dschreid.learningapp.repository.ReminderRepository;

public class ReminderServiceImpl implements ReminderService {
    private final ReminderRepository reminderRepository;
    private final Context context;

    public ReminderServiceImpl(ReminderRepository reminderRepository, Context context) {
        this.reminderRepository = reminderRepository;
        this.context = context;
        this.setupChannel();
    }

    @Override
    public void refreshReminders() {
        AlarmManager alarmManager = context.getSystemService(AlarmManager.class);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.cancelAll();

        this.reminderRepository.fetchReminders(reminders -> {
            reminders.forEach(reminder -> this.createReminder(reminder, alarmManager));
        });
    }

    private void createReminder(Reminder reminder, AlarmManager alarmManager) {
        if (System.currentTimeMillis() > reminder.getDate()) return;

        for (Integer preReminder : reminder.getPreReminders()) {
            this.createReminderWithOffset(reminder, alarmManager, preReminder);
        }
    }

    private void createReminderWithOffset(Reminder reminder, AlarmManager alarmManager, int offset) {
        Intent intent = new Intent(context, ReminderNotificationReceiver.class);
        intent.putExtra(ReminderNotificationReceiver.COURSE_EXTRA, reminder.getCourse());
        intent.putExtra(ReminderNotificationReceiver.ID_EXTRA, reminder.getId());
        intent.putExtra(ReminderNotificationReceiver.OFFSET_EXTRA, offset);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getDate() - toMinutes(offset), pendingIntent);
    }

    private long toMinutes(int offset) {
        return 60 * 1000L * offset;
    }

    private void setupChannel() {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(new NotificationChannel(
                context.getString(R.string.chanel_id),
                "Broadcast",
                NotificationManager.IMPORTANCE_DEFAULT
        ));
    }

    @Override
    public void requestPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 123);
        }
    }
}
