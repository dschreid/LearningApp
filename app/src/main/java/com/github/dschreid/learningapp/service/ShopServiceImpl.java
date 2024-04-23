package com.github.dschreid.learningapp.service;

import com.github.dschreid.learningapp.model.ShopItem;
import com.github.dschreid.learningapp.repository.ShopItemRepository;
import com.github.dschreid.learningapp.shop.ShopItems;
import com.github.dschreid.learningapp.viewmodel.ShopItemListViewModel;
import com.github.dschreid.learningapp.viewmodel.UserDataViewModel;

public class ShopServiceImpl implements ShopService {

    public ShopServiceImpl(ShopItemRepository repository) {
        ShopItems.addToShop(repository);
    }

    @Override
    public boolean hasBought(ShopItem shopItem, UserDataViewModel userDataViewModel) {
        return userDataViewModel.hasBoughtItem(shopItem.getId());
    }

    @Override
    public void toggle(ShopItem shopItem, ShopItemListViewModel shopItemListViewModel) {
        shopItemListViewModel.toggleSelected(shopItem);
    }

    @Override
    public void buyShopItem(ShopItem shopItem, ShopItemListViewModel viewModel, UserDataViewModel userDataViewModel) {
        int purse = userDataViewModel.getPoints();
        if (purse < shopItem.getPrice()) return;

        userDataViewModel.takePoints(shopItem.getPrice());
        userDataViewModel.addBoughtItem(shopItem.getId());
        viewModel.toggleSelected(shopItem);
    }

    @Override
    public boolean isSelected(ShopItem shopItem) {
        return shopItem.isSelected();
    }
}
