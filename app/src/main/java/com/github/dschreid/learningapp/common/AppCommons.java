package com.github.dschreid.learningapp.common;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.viewmodel.UserDataViewModel;

/**
 * Widerkehrende Methoden, gebündelt und zentralisiert in einer Klasse
 *
 * @author dschreid
 */
public class AppCommons {

    /**
     * Sets statistic elements into common UI elements, such as
     * - Points
     * - Time Learned
     */
    public static <T extends ViewModelStoreOwner & LifecycleOwner> UserDataViewModel setStastitics(T owner, View view, Resources resources) {
        UserDataViewModel userDataViewModel = CommonViewModelFactory.createViewModelProvider(owner).get(UserDataViewModel.class);
        userDataViewModel.getUserData().observe(owner, userData -> {
            TextView viewById = view.findViewById(R.id.textViewPoints);
            if (viewById != null) viewById.setText(String.format(resources.getString(R.string.text_format_points), userData.getPoints()));

            viewById = view.findViewById(R.id.textViewTimeLearned);
            if (viewById != null) viewById.setText(String.format(resources.getString(R.string.text_format_min_learned), userData.getMinutesLearned()));
        });
        return userDataViewModel;
    }

    public static void finishFragment(Fragment fragment) {
        fragment.requireActivity().getSupportFragmentManager().popBackStack();
    }

    public static void transitionToFragment(Fragment from, Fragment fragment, int transition) {
        transitionToFragment(from.requireActivity().getSupportFragmentManager(), fragment, transition);
    }

    public static void transitionToFragment(AppCompatActivity context, Fragment fragment, int transition) {
        transitionToFragment(context.getSupportFragmentManager(), fragment, transition);
    }

    public static void transitionToFragment(FragmentManager fragmentManager, Fragment fragment, int transition) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.home, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(transition);
        transaction.commit();
    }
}
