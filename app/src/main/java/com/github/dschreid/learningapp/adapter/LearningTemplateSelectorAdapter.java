package com.github.dschreid.learningapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.model.LearningTemplate;
import com.github.dschreid.learningapp.view.SelectTemplateFragment;
import com.github.dschreid.learningapp.viewmodel.LearningTemplateListViewModel;

/**
 * ReclyclerView Adapter für eine Selektion von Volagen
 *
 * @author dschreid
 */
public class LearningTemplateSelectorAdapter extends RecyclerView.Adapter<LearningTemplateSelectorAdapter.LearningTemplateListViewHolder> implements LifecycleOwner {
    private final SelectTemplateFragment fragment;
    private final List<LearningTemplate> data;
    private final Lifecycle lifecycle;
    private final LearningTemplateListViewModel viewModel;
    private final Consumer<LearningTemplate> onSelected;

    public LearningTemplateSelectorAdapter(SelectTemplateFragment fragment, Lifecycle lifecycle, LearningTemplateListViewModel viewModel, Consumer<LearningTemplate> onSelected) {
        this.fragment = fragment;
        this.data = new ArrayList<>();
        this.onSelected = onSelected;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lernvorlagen_select_entry, parent, false);
        return new LearningTemplateListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningTemplateListViewHolder holder, int position) {
        LearningTemplate template = data.get(position);

        TextView textName = holder.itemView.findViewById(R.id.name);
        textName.setText(template.getName());

        TextView textCourse = holder.itemView.findViewById(R.id.course);
        textCourse.setText(template.getCourse());

        TextView textMinutes = holder.itemView.findViewById(R.id.minutes);
        textMinutes.setText(String.valueOf(template.getMinuten()));

        Button entryButton = holder.itemView.findViewById(R.id.select);
        entryButton.setOnClickListener(v -> {
            AppCommons.finishFragment(fragment);
            onSelected.accept(template);
        });
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
