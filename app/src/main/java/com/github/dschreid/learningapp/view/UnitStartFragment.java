package com.github.dschreid.learningapp.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.model.LearningTemplate;
import com.github.dschreid.learningapp.model.LearningUnit;
import com.github.dschreid.learningapp.service.ShopService;
import com.github.dschreid.learningapp.shop.ShopItems;
import com.github.dschreid.learningapp.viewmodel.LearningUnitViewModel;
import com.github.dschreid.learningapp.viewmodel.UserDataViewModel;

/**
 * Fragment zum starten einer Lerneinheit
 * Zeigt Timer und bei Ablauf transitiert in {@link EvaluationFragment}
 *
 * @author dschreid
 */
public class UnitStartFragment extends Fragment {
    private static final long ONE_MINUTE = 60 * 1000;
    private final ShopService shopService;
    private final Timer timer = new Timer();
    private final LearningUnitViewModel learningUnitViewModel;
    private UserDataViewModel userDataViewModel;
    private Button buttonTimeControl;
    private Button buttonAbort;
    private ProgressBar progressBar;
    private TextView timerText;
    private TextView fachLabel;
    private int minutesLearned;
    private boolean timerStarted = false;
    private MediaPlayer mediaPlayer;

    private UnitStartFragment(LearningUnit unit) {
        this.learningUnitViewModel = LearningUnitViewModel.of(unit);
        this.shopService = LearningApp.getInstance().getShopService();
    }

    public static UnitStartFragment of(LearningUnit unit) {
        Objects.requireNonNull(unit);
        Objects.requireNonNull(unit.getId());

        return new UnitStartFragment(unit);
    }

    public static UnitStartFragment of(LearningTemplate template) {
        Objects.requireNonNull(template);

        LearningUnit learningUnit = template.toLearningUnit();
        return new UnitStartFragment(learningUnit);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lerneinheit_starten, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.userDataViewModel = AppCommons.setStastitics(this, view, getResources());

        this.buttonTimeControl = view.findViewById(R.id.timeControl);
        this.buttonAbort = view.findViewById(R.id.buttonAbort);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.timerText = view.findViewById(R.id.timer);
        this.fachLabel = view.findViewById(R.id.course);

        this.learningUnitViewModel.getLearningUnit().observe(getViewLifecycleOwner(), unit -> {
            this.fachLabel.setText(unit.getCourse());
        });

        learningUnitViewModel.getRunning().observe(getViewLifecycleOwner(), running -> {
            String newText = running ? getString(R.string.pause_timer) : getString(R.string.continue_timer);
            this.buttonTimeControl.setText(newText);
        });

        this.learningUnitViewModel.getTimer().observe(getViewLifecycleOwner(), integer -> {
            this.timerText.setText(String.valueOf(integer));
            if (!timerStarted) {
                this.progressBar.setMax(integer);
                timerStarted = true;
            }
            this.progressBar.setProgress(integer, false);
        });

        this.timer.schedule(new MinuteTimerTask(), ONE_MINUTE, ONE_MINUTE);
        this.buttonTimeControl.setOnClickListener(v -> learningUnitViewModel.switchRunning());
        this.buttonAbort.setOnClickListener(v -> AppCommons.finishFragment(this));

        //Visible Unvisible

        this.timerText.setOnClickListener(v -> {

            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.INVISIBLE);
                timerText.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                timerText.setVisibility(View.INVISIBLE);
            }
        });

        this.progressBar.setOnClickListener(v -> {
            if (timerText.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
                timerText.setVisibility(View.INVISIBLE);
            } else {
                timerText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        // Raining Sounds
        if (shopService.isSelected(ShopItems.RAIN_SOUNDS)) {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.rain_sounds);
            mediaPlayer.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mediaPlayer != null) mediaPlayer.stop();
        this.timer.cancel();
    }

    private class MinuteTimerTask extends TimerTask {
        @Override
        public void run() {
            if (Boolean.FALSE.equals(learningUnitViewModel.getRunning().getValue())) {
                return;
            }

            minutesLearned++;
            learningUnitViewModel.addMinute();

            if (!learningUnitViewModel.shouldContinue()) {
                userDataViewModel.addMinutesAndPoints(minutesLearned);

                AppCommons.finishFragment(UnitStartFragment.this);
                AppCommons.transitionToFragment(UnitStartFragment.this, new EvaluationFragment(learningUnitViewModel, minutesLearned), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                cancel();
            }
        }
    }
}