package com.github.dschreid.learningapp.shop;

import com.github.dschreid.learningapp.model.ShopItem;
import com.github.dschreid.learningapp.repository.ShopItemRepository;
import com.github.dschreid.learningapp.shop.impl.RainSounds;

/**
 * Klasse, welchen den Zugriff auf Shop-Elemente erleichtern soll
 *
 * @author dschreid
 */
public class ShopItems {
    public static final ShopItem RAIN_SOUNDS = new RainSounds(45);

    public static void addToShop(ShopItemRepository repository) {
        repository.addItem(RAIN_SOUNDS);
    }



}
