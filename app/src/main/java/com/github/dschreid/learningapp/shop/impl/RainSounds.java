package com.github.dschreid.learningapp.shop.impl;

import com.github.dschreid.learningapp.R;
import com.github.dschreid.learningapp.model.ShopItem;

/**
 *
 */
public class RainSounds extends ShopItem {
    public static final String ID = "rainsounds";

    public RainSounds(int price) {
        super(ID, R.string.shop_rainsounds, price);
    }

}
