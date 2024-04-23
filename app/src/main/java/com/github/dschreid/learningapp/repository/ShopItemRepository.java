package com.github.dschreid.learningapp.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.dschreid.learningapp.model.ShopItem;

/**
 * Repositoriy zur Verwaltung von Shop Items
 *
 * @author dschreid
 */
public class ShopItemRepository {
    private final Map<String, ShopItem> database;

    private ShopItemRepository(Map<String, ShopItem> database) {
        this.database = database;
    }

    public ShopItemRepository() {
        this(new HashMap<>());
    }

    public void addItem(ShopItem shopItem) {
        this.database.put(shopItem.getId(), shopItem);
    }

    public void fetchShopItems(Consumer<List<ShopItem>> onResult) {
        onResult.accept(new ArrayList<>(database.values()));
    }

}
