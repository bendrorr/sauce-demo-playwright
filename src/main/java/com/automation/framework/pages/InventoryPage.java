package com.automation.framework.pages;

import com.automation.framework.util.PageWaiterService;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InventoryPage implements Loadable {
    private final Page page;
    private final PageWaiterService pageWaiterService;

    private static final String ITEM_PRICE = "[data-test='inventory-item-price']";
    private static final String INVENTORY_LIST = "[data-test='inventory-list']";
    private static final String SHOPPING_CART = "[data-test='shopping-cart-link']";


    public void addProductToCart(String productId) {
        page.click("[data-test=add-to-cart-" + productId + "]");
    }

    public void goToCart() {
        page.click(SHOPPING_CART);
    }

    @Override
    public boolean isLoaded() {
        return pageWaiterService.waitForSelector(INVENTORY_LIST, 3000);
    }


}