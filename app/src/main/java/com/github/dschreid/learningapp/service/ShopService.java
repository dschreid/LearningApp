package com.github.dschreid.learningapp.service;

import com.github.dschreid.learningapp.model.ShopItem;
import com.github.dschreid.learningapp.viewmodel.ShopItemListViewModel;
import com.github.dschreid.learningapp.viewmodel.UserDataViewModel;

/**
 * Klasse Verwantwortlich für die Logik des Shops
 *
 * @author dschreid
 */
public interface ShopService {
    /**
     * Ueberprueft ob der Nutzer ein shopItem gekauft hat.
     *
     * @param shopItem          item welches geprüft werden soll
     * @param userDataViewModel Nutzer Modell, zur Abfrage
     * @return ob der Nutzer das Item gekauft hat
     */
    boolean hasBought(ShopItem shopItem, UserDataViewModel userDataViewModel);

    /**
     * Invert die selektion eines ShopItems und benachrichtigt gibt dies
     * and die Observer weiter
     *
     * @param shopItem              shop item welches invertiert werden soll
     * @param shopItemListViewModel Shop Modell
     */
    void toggle(ShopItem shopItem, ShopItemListViewModel shopItemListViewModel);

    /**
     * Versaucht, mit den Punkten des Nutzers das Shop Item zu kaufen.
     * Bei Fehlschlag passiert nichts.
     *
     * @param shopItem          item welches gekauft werden soll
     * @param viewModel         view model des shops
     * @param userDataViewModel view model des Nutzers
     */
    void buyShopItem(ShopItem shopItem, ShopItemListViewModel viewModel, UserDataViewModel userDataViewModel);

    /**
     * Ueberprueft ob der Nutzer ein shopItem selektiert hat
     *
     * @param shopItem item welches geprüft werden soll
     * @return ob der Nutzer das Item aktiviert/selektiert hat
     */
    boolean isSelected(ShopItem shopItem);
}
