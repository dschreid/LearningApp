package com.github.dschreid.learningapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.model.LearningTemplate;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.view.EditTemplateFragment;
import com.github.dschreid.learningapp.view.UnitStartFragment;
import com.github.dschreid.learningapp.viewmodel.LearningTemplateListViewModel;

/**
 * ReclyclerView Adapter für Vorlagen
 *
 * @author dschreid
 */
public class LearningTemplateListAdapter extends RecyclerView.Adapter<LearningTemplateListAdapter.LearningTemplateListViewHolder> implements LifecycleOwner {
    private final Fragment origin;
    private final List<LearningTemplate> data;
    private final Lifecycle lifecycle;
    private final LearningTemplateListViewModel viewModel;

    public LearningTemplateListAdapter(Fragment origin, Lifecycle lifecycle, LearningTemplateListViewModel viewModel) {
        this.origin = origin;
        this.data = new ArrayList<>();
        this.lifecycle = lifecycle;
        this.viewModel = viewModel;
        this.viewModel.getTemplates().observe(this, this::publishUpdate);
    }

    private void publishUpdate(List<LearningTemplate> templates) {
        this.data.clear();
        this.data.addAll(templates);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LearningTemplateListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lernvorlagen_list_entry, parent, false);
        return new LearningTemplateListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningTemplateListViewHolder holder, int position) {
        LearningTemplate template = data.get(position);

        View view = holder.itemView;
        TextView name = view.findViewById(R.id.name);
        TextView minutes = view.findViewById(R.id.zeit);
        TextView course = view.findViewById(R.id.course);
        View edit = view.findViewById(R.id.buttonEdit);
        View delete = view.findViewById(R.id.buttonDelete);
        View train = view.findViewById(R.id.buttonTrain);

        delete.setOnClickListener(v -> {
            viewModel.deleteTemplate(template);
        });

        train.setOnClickListener(v -> {
            AppCommons.transitionToFragment(origin, UnitStartFragment.of(template), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        });

        edit.setOnClickListener(v -> {
            AppCommons.transitionToFragment(origin, new EditTemplateFragment(template, viewModel), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        });

        name.setText(template.getName());
        course.setText(template.getCourse());
        minutes.setText(String.format("%d", template.getMinuten()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycle;
    }

    public static class LearningTemplateListViewHolder extends RecyclerView.ViewHolder {
        public LearningTemplateListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
