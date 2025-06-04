package com.automation.framework.pages;

import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckoutOverviewPage implements Loadable {
    public static final String FINISH = "[data-test='finish']";
    private final Page page;

    public void finishOrder() {
        page.click(FINISH);
    }

    @Override
    public boolean isLoaded() {
        return page.url().contains("checkout-step-two");
    }
}
