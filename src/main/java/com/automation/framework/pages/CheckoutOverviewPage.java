package com.automation.framework.pages;

import com.automation.framework.util.PageWaiterService;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckoutOverviewPage implements Loadable {
    private final Page page;
    private final PageWaiterService pageWaiterService;
    public static final String FINISH = "[data-test='finish']";

    public void finishOrder() {
        page.click(FINISH);
    }

    public String getConfirmationMessage() {
        pageWaiterService.waitForSelector(".complete-header", 3000);
        return page.locator(".complete-header").textContent().trim();
    }

    @Override
    public boolean isLoaded() {
        return page.url().contains("checkout-step-two");
    }
}
