package com.github.dschreid.learningapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import com.github.dschreid.learningapp.view.HomeActivity;

/**
 * Startpunkt der App, zeigt SplashScreen und wechselt in HomeScreen
 *
 * @author dschreid
 */
public class MainActivity extends AppCompatActivity {
    private static final int SECONDS = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        }, SECONDS * 1000L);
    }

}
