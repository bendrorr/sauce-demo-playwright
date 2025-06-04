package com.automation.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartPage implements Loadable {
    private final Page page;

    private static final String CHECKOUT = "[data-test='checkout']";

    public void clickCheckout() {
        page.click(CHECKOUT);
    }

    public boolean containsProduct(String productId) {
        String expectedName = convertProductIdToDisplayName(productId);

        Locator items = page.locator(".cart_item");

        for (int i = 0; i < items.count(); i++) {
            Locator item = items.nth(i);
            String name = item.locator(".inventory_item_name").textContent().trim();
            if (name.equalsIgnoreCase(expectedName)) {
                return true;
            }
        }

        return false;
    }

    public void removeProduct(String productId) {
        String selector = "#remove-" + productId;
        Locator removeButton = page.locator(selector);

        if (removeButton.isVisible()) {
            removeButton.click();
        } else {
            throw new RuntimeException("Remove button for product '" + productId + "' not found or not visible.");
        }
    }

    @Override
    public boolean isLoaded() {
        return page.url().contains("cart");
    }

    private String convertProductIdToDisplayName(String productId) {
        return switch (productId) {
            case "sauce-labs-backpack" -> "Sauce Labs Backpack";
            case "sauce-labs-bike-light" -> "Sauce Labs Bike Light";
            case "sauce-labs-bolt-t-shirt" -> "Sauce Labs Bolt T-Shirt";
            case "sauce-labs-fleece-jacket" -> "Sauce Labs Fleece Jacket";
            default -> productId;
        };
    }
}
