package com.github.dschreid.learningapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.model.ShopItem;
import com.github.dschreid.learningapp.service.ShopService;
import com.github.dschreid.learningapp.viewmodel.ShopItemListViewModel;
import com.github.dschreid.learningapp.viewmodel.UserDataViewModel;

/**
 * ReclyclerView Adapter für Shop Items
 *
 * @author dschreid
 */
public class ShopItemListAdapter extends RecyclerView.Adapter<ShopItemListAdapter.ShopItemListViewHolder> implements LifecycleOwner {
    private final Fragment origin;
    private final List<ShopItem> data;
    private final Lifecycle lifecycle;
    private final ShopItemListViewModel shopItemListViewModel;
    private final UserDataViewModel userDataViewModel;
    private final ShopService shopService;

    public ShopItemListAdapter(Fragment origin, Lifecycle lifecycle, ShopItemListViewModel shopItemListViewModel, UserDataViewModel userDataViewModel, ShopService shopService) {
        this.origin = origin;
        this.userDataViewModel = userDataViewModel;
        this.shopService = shopService;
        this.data = new ArrayList<>();
        this.lifecycle = lifecycle;
        this.shopItemListViewModel = shopItemListViewModel;
        this.shopItemListViewModel.getShopItems().observe(this, this::publishUpdate);
    }

    private void publishUpdate(List<ShopItem> shopItems) {
        this.data.clear();
        this.data.addAll(shopItems);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_entry, parent, false);
        return new ShopItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemListViewHolder holder, int position) {
        ShopItem shopItem = data.get(position);

        View view = holder.itemView;
        TextView name = view.findViewById(R.id.name);
        TextView price = view.findViewById(R.id.price);
        SwitchCompat selector = view.findViewById(R.id.buttonSelect);
        Button purchase = view.findViewById(R.id.buttonPurchase);

        name.setText(origin.getResources().getString(shopItem.getNameId()));
        price.setText(String.valueOf(shopItem.getPrice()));

        if (shopService.hasBought(shopItem, userDataViewModel)) {
            price.setVisibility(View.GONE);
            purchase.setVisibility(View.GONE);
            selector.setVisibility(View.VISIBLE);
        } else {
            selector.setVisibility(View.GONE);
        }

        selector.setChecked(shopItem.isSelected());
        selector.setOnCheckedChangeListener((buttonView, isChecked) -> shopService.toggle(shopItem, shopItemListViewModel));
        purchase.setOnClickListener(v -> shopService.buyShopItem(shopItem, shopItemListViewModel, userDataViewModel));
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

    public static class ShopItemListViewHolder extends RecyclerView.ViewHolder {
        public ShopItemListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
