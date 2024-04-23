package com.github.dschreid.learningapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.viewmodel.LearningUnitViewModel;

/**
 * Fragment zur Bewertung einer Lerneinheit
 *
 * @author dschreid
 */
public class EvaluationFragment extends Fragment {
    private final LearningUnitViewModel learningUnitViewModel;
    private final int minutesLearned;

    public EvaluationFragment(LearningUnitViewModel learningUnitViewModel, int minutesLearned) {
        this.learningUnitViewModel = learningUnitViewModel;
        this.minutesLearned = minutesLearned;
        this.learningUnitViewModel.save();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lerneinheit_bewerten, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppCommons.setStastitics(this, view, getResources());

        view.findViewById(R.id.evaluate1).setOnClickListener(v -> this.evaluate(2));
        view.findViewById(R.id.evaluate2).setOnClickListener(v -> this.evaluate(1));
        view.findViewById(R.id.evaluate3).setOnClickListener(v -> this.evaluate(0));
        view.findViewById(R.id.evaluate4).setOnClickListener(v -> this.evaluate(-1));
        view.findViewById(R.id.evaluate5).setOnClickListener(v -> this.evaluate(-2));
    }

    private void evaluate(int note) {
        learningUnitViewModel.setEvaluation(note);
        AppCommons.finishFragment(this);
    }

}
