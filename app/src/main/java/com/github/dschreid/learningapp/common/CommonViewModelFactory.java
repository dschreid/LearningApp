package com.github.dschreid.learningapp.common;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.github.dschreid.learningapp.viewmodel.LearningTemplateListViewModel;
import com.github.dschreid.learningapp.viewmodel.LearningUnitListViewModel;
import com.github.dschreid.learningapp.viewmodel.ReminderListViewModel;
import com.github.dschreid.learningapp.viewmodel.ShopItemListViewModel;
import com.github.dschreid.learningapp.viewmodel.UserDataViewModel;

public class CommonViewModelFactory {
    /**
     * View Model Providers
     *
     * @author dschreid
     */
    public static ViewModelProvider createViewModelProvider(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, ViewModelProvider.Factory.from(
                UserDataViewModel.INITIALIZER,
                LearningUnitListViewModel.INITIALIZER,
                ReminderListViewModel.INITIALIZER,
                ShopItemListViewModel.INITIALIZER,
                LearningTemplateListViewModel.INITIALIZER)
        );
    }
}
