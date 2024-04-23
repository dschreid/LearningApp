package com.github.dschreid.learningapp.adapter;

import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.model.LearningUnit;

/**
 * Adapter um eine Lerneinheit an eine Tabellenreihe zu binden
 *
 * @author dschreid
 */
public class LearningUnitTableRowAdapter {
    private final DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    public void bind(Fragment fragment, LearningUnit learningUnit, TableRow row) {
        TextView evaluation = row.findViewById(R.id.bewertung);
        TextView minutes = row.findViewById(R.id.minutes);
        TextView course = row.findViewById(R.id.course);
        TextView uhrZeit = row.findViewById(R.id.uhrZeit);

        course.setText(learningUnit.getCourse());
        minutes.setText(String.valueOf(learningUnit.getMinutesLearned()));
        evaluation.setText(getEvaluationString(fragment, learningUnit.getEvaluation()));
        uhrZeit.setText(format.format(new Date(learningUnit.getTimeStarted())));
    }

    public String getEvaluationString(Fragment fragment, int evaluation) {
        switch (evaluation) {
            case 2:
                return fragment.getString(R.string.very_productiv);
            case 1:
                return fragment.getString(R.string.productiv);
            case -1:
                return fragment.getString(R.string.not_so_productive);
            case -2:
                return fragment.getString(R.string.not_productive);
            default:
                return fragment.getString(R.string.neutral);
        }
    }
}
