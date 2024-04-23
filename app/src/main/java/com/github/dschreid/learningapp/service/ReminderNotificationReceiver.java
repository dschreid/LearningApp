package com.github.dschreid.learningapp.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.github.dschreid.learningapp.R;

/**
 * Receiver-Klasse um eine Push-Benachrichtung zu Ausgewählten Zeiten zu senden
 *
 * @author Dominik
 */
public class ReminderNotificationReceiver extends BroadcastReceiver {
    public final static String ID_EXTRA = "reminder-id";
    public final static String OFFSET_EXTRA = "reminder-offset";
    public final static String COURSE_EXTRA = "course-name";

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(ID_EXTRA, 0);
        int offset = intent.getIntExtra(OFFSET_EXTRA, -1);
        String course = intent.getStringExtra(COURSE_EXTRA);

        if (id == 0) return;
        if (offset == -1) return;
        if (course == null) return;

        @SuppressLint("StringFormatMatches")
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.chanel_id))
                .setSmallIcon(R.drawable.train)
                .setContentTitle(context.getString(R.string.reminder_title))
                .setContentText(String.format(context.getString(R.string.reminder_content), offset, course))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int) id, builder.build());
    }

}