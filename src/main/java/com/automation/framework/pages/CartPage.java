package com.automation.framework.pages;

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

    @Override
    public boolean isLoaded() {
        return page.url().contains("cart");
    }
}
