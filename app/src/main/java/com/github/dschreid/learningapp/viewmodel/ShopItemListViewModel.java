package com.github.dschreid.learningapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.List;

import com.github.dschreid.learningapp.LearningApp;
import com.github.dschreid.learningapp.model.ShopItem;
import com.github.dschreid.learningapp.repository.ShopItemRepository;
import lombok.Getter;

/**
 * ViewModel für eine Liste von ShopItem
 *
 * @author dschreid
 */
public class ShopItemListViewModel extends ViewModel {

    public static final ViewModelInitializer<ShopItemListViewModel> INITIALIZER =
            new ViewModelInitializer<>(ShopItemListViewModel.class, creationExtras -> {
                return new ShopItemListViewModel(LearningApp.getInstance().getShopItemRepository());
            });

    @Getter
    private MutableLiveData<List<ShopItem>> shopItems;
    private List<ShopItem> data;
    private ShopItemRepository repository;

    public ShopItemListViewModel(ShopItemRepository repository) {
        this.data = new ArrayList<>();
        this.shopItems = new MutableLiveData<>();
        this.repository = repository;

        this.repository.fetchShopItems(items -> {
            this.data.addAll(items);
            this.shopItems.postValue(data);
        });
    }

    /**
     * Invert die selektion eines ShopItems und benachrichtigt die
     * observer
     *
     * @param shopItem shop item welches invertiert werden soll
     */
    public void toggleSelected(ShopItem shopItem) {
        shopItem.setSelected(!shopItem.isSelected());
        this.shopItems.postValue(data);
    }

}
