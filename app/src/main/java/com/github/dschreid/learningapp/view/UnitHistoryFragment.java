package com.github.dschreid.learningapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.adapter.LearningUnitTableRowAdapter;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.common.CommonViewModelFactory;
import com.github.dschreid.learningapp.model.LearningUnit;
import com.github.dschreid.learningapp.viewmodel.LearningUnitListViewModel;

/**
 * Fragment zur Listung und durchgeführten Lerneinheiten
 *
 * @author dschreid
 */
public class UnitHistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bisherige_lernheiten, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewModelProvider viewModelProvider = CommonViewModelFactory.createViewModelProvider(this);
        LearningUnitListViewModel viewModel = viewModelProvider.get(LearningUnitListViewModel.class);

        AppCommons.setStastitics(this, view, getResources());
        view.findViewById(R.id.buttonBack).setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        TableLayout table = view.findViewById(R.id.historyTable);
        TableRow headerRow = view.findViewById(R.id.headerRow);

        viewModel.getLearningUnits().observe(getViewLifecycleOwner(), learningUnits -> {
            table.removeAllViews();
            table.addView(headerRow);
            learningUnits.forEach(unit -> createAndAddRow(table, unit));
        });
    }

    private void createAndAddRow(TableLayout table, LearningUnit unit) {
        TableRow row = (TableRow) LayoutInflater.from(requireContext()).inflate(R.layout.bisherige_lerneinheiten_row, table, false);
        LearningUnitTableRowAdapter learningUnitTableRowAdapter = new LearningUnitTableRowAdapter();
        learningUnitTableRowAdapter.bind(this, unit, row);
        table.addView(row);
    }
}