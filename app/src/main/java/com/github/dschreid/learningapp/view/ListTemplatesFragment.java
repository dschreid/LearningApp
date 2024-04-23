package com.github.dschreid.learningapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.adapter.LearningTemplateListAdapter;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.common.CommonViewModelFactory;
import com.github.dschreid.learningapp.model.LearningTemplate;
import com.github.dschreid.learningapp.viewmodel.LearningTemplateListViewModel;

/**
 * Fragment zur Listung und Manipulieren von Vorlagen
 *
 * @author dschreid
 */
public class ListTemplatesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lernvorlagen_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewModelProvider viewModelProvider = CommonViewModelFactory.createViewModelProvider(this);
        LearningTemplateListViewModel viewModel = viewModelProvider.get(LearningTemplateListViewModel.class);

        view.findViewById(R.id.buttonBack).setOnClickListener(v -> AppCommons.finishFragment(this));
        view.findViewById(R.id.createTemplate).setOnClickListener(v -> {
            AppCommons.transitionToFragment(this, new EditTemplateFragment(LearningTemplate.createEmpty(), viewModel), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        });

        RecyclerView table = view.findViewById(R.id.list);
        table.setLayoutManager(new LinearLayoutManager(requireContext()));
        table.setAdapter(new LearningTemplateListAdapter(this, this.getLifecycle(), viewModel));
    }
}