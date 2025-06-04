package com.automation.framework.pages;

import com.automation.framework.mapper.ProductMapper;
import com.automation.framework.model.Product;
import com.automation.framework.util.PageWaiterService;
import com.automation.framework.util.PriceParser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class InventoryPage implements Loadable {
    private final Page page;
    private final PageWaiterService pageWaiterService;
    private final ProductMapper productMapper;

    private static final String ITEM_PRICE = "[data-test='inventory-item-price']";
    private static final String INVENTORY_LIST = "[data-test='inventory-list']";
    private static final String SHOPPING_CART = "[data-test='shopping-cart-link']";
    private static final String INVENTORY_ITEM = "[data-test='inventory-item']";


    public void addProductToCart(String productId) {
        page.click("[data-test=add-to-cart-" + productId + "]");
    }

    public List<Double> getSortedPricesDescending() {
        return page.locator(INVENTORY_ITEM)
                .locator("[data-test='inventory-item-price']")
                .allTextContents().stream()
                .map(PriceParser::parsePrice)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Locator items = page.locator(INVENTORY_ITEM);

        for (int i = 0; i < items.count(); i++) {
            products.add(productMapper.toProduct(items.nth(i)));

        }

        return products;
    }

    public boolean isRemoveButtonVisible(String productId) {
        Locator removeButton = page.locator("#remove-" + productId);
        return removeButton.isVisible();
    }

    public void goToCart() {
        page.click(SHOPPING_CART);
    }

    @Override
    public boolean isLoaded() {
        return pageWaiterService.waitForSelector(INVENTORY_LIST, 3000);
    }


}