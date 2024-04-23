package com.github.dschreid.learningapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.adapter.LearningTemplateSelectorAdapter;
import com.github.dschreid.learningapp.model.LearningTemplate;
import com.github.dschreid.learningapp.common.CommonViewModelFactory;
import com.github.dschreid.learningapp.viewmodel.LearningTemplateListViewModel;

/**
 * Klasse welches ein Fenster Öffnet, in der man eine Vorlage Auswählt.
 * Nach dem Auswählen einer Vorlage, wird onSelected ausgeführt
 *
 * @author dschreid
 */
public class SelectTemplateFragment extends Fragment {
    private final Consumer<LearningTemplate> onSelected;

    public SelectTemplateFragment(Consumer<LearningTemplate> onSelected) {
        this.onSelected = onSelected;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lernvorlagen_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LearningTemplateListViewModel viewModel = CommonViewModelFactory.createViewModelProvider(this).get(LearningTemplateListViewModel.class);

        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new LearningTemplateSelectorAdapter(this, getLifecycle(), viewModel, onSelected));
    }
}
