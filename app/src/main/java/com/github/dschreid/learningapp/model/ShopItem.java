package com.github.dschreid.learningapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Shop Item Modell
 *
 * @author dschreid
 */
@Data
@RequiredArgsConstructor
public class ShopItem {
    private final String id;
    private final int nameId;
    private final int price;
    private boolean selected;

}
