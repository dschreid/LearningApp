package com.github.dschreid.learningapp.service;

import android.app.Activity;

/**
 * Service zur Logik von Erinerrungen
 *
 * @author dschreid
 */
public interface ReminderService {

    /**
     * Synchronisiert alle Erinnerungen mit den des Betriebssystemes
     */
    void refreshReminders();

    /**
     * @param activity activity von wo der Request stattfinden soll
     */
    void requestPermission(Activity activity);
}
