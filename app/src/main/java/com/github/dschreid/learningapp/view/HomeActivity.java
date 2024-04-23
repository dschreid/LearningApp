package com.github.dschreid.learningapp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.common.AppCommons;

/**
 * Home Activity. Verantwortlich für das Hosten der Fragmente
 * und Schnittpunktstelle zu allen Aktivitäten
 *
 * @author dschreid,
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        AppCommons.setStastitics(this, this.findViewById(android.R.id.content), getResources());

        findViewById(R.id.buttonStart).setOnClickListener(v -> {
            AppCommons.transitionToFragment(this, new SelectTemplateFragment(learningTemplate -> {
                transitionToFragment(UnitStartFragment.of(learningTemplate));
            }), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        });

        findViewById(R.id.buttonClose).setOnClickListener(v -> finish());
        findViewById(R.id.buttenShop).setOnClickListener(v -> transitionToFragment(new ShopFragment()));
        findViewById(R.id.buttonErrinerung).setOnClickListener(v -> transitionToFragment(new ListRemindersFragment()));
        findViewById(R.id.buttonTemplates).setOnClickListener(v -> transitionToFragment(new ListTemplatesFragment()));
        findViewById(R.id.buttonLerneinheit).setOnClickListener(v -> transitionToFragment(new UnitHistoryFragment()));
    }

    private void transitionToFragment(Fragment fragment) {
        AppCommons.transitionToFragment(this, fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }
}