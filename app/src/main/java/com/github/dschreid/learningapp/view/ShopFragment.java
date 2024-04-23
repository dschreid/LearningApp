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

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.adapter.ShopItemListAdapter;
import com.github.dschreid.learningapp.common.AppCommons;
import com.github.dschreid.learningapp.common.CommonViewModelFactory;
import com.github.dschreid.learningapp.service.ShopService;
import com.github.dschreid.learningapp.viewmodel.ShopItemListViewModel;
import com.github.dschreid.learningapp.viewmodel.UserDataViewModel;

/**
 * Fragment zur visualierung von ShopItems, welche für Punkte gekauft werden können
 *
 * @author dschreid
 */
public class ShopFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        UserDataViewModel userDataViewModel = AppCommons.setStastitics(this, view, getResources());
        ShopItemListViewModel shopItemListViewModel = CommonViewModelFactory.createViewModelProvider(this).get(ShopItemListViewModel.class);
        ShopService shopService = LearningApp.getInstance().getShopService();

        view.findViewById(R.id.buttonBack).setOnClickListener(v -> AppCommons.finishFragment(this));

        RecyclerView recyclerView = view.findViewById(R.id.shopList);
        recyclerView.setAdapter(new ShopItemListAdapter(this, getLifecycle(), shopItemListViewModel, userDataViewModel, shopService));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

}