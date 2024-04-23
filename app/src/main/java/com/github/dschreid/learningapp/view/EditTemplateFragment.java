package com.github.dschreid.learningapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.common.TextFieldUtils;
import com.github.dschreid.learningapp.model.LearningTemplate;
import com.github.dschreid.learningapp.viewmodel.LearningTemplateListViewModel;

/**
 * Fragment zum Erstellen oder Editieren einer Vorlage
 *
 * @author dschreid
 */
public class EditTemplateFragment extends Fragment {
    private final LearningTemplate template;
    private final LearningTemplateListViewModel viewModel;
    private EditText name;
    private EditText course;
    private EditText minuten;

    public EditTemplateFragment(LearningTemplate template, LearningTemplateListViewModel viewModel) {
        this.template = template;
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lernvorlagen_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.name = view.findViewById(R.id.inputName);
        this.course = view.findViewById(R.id.inputCourse);
        this.minuten = view.findViewById(R.id.inputMinuten);

        if (template.getName() != null) this.name.getText().append(template.getName());
        if (template.getCourse() != null) this.course.getText().append(template.getCourse());
        if (template.getMinuten() > 0) this.minuten.getText().append(String.valueOf(template.getMinuten()));

        view.findViewById(R.id.buttonBack).setOnClickListener(v -> AppCommons.finishFragment(this));
        view.findViewById(R.id.buttonConfirm).setOnClickListener(v -> {
            boolean error = false;
            error = TextFieldUtils.checkAndGiveFeedback(getContext(), name) || error;
            error = TextFieldUtils.checkAndGiveFeedback(getContext(), course) || error;
            error = TextFieldUtils.checkAndGiveFeedback(getContext(), minuten) || error;

            if (error) return;

            template.setName(name.getText().toString());
            template.setCourse(course.getText().toString());
            template.setMinuten(Integer.valueOf(minuten.getText().toString()));

            viewModel.addTemplate(template);
            AppCommons.finishFragment(this);
        });
    }


}
