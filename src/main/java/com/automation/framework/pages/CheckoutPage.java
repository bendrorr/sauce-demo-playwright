package com.automation.framework.pages;

import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckoutPage implements Loadable {
    private final Page page;

    public static final String FIRST_NAME = "[data-test='firstName']";
    public static final String LAST_NAME = "[data-test='lastName']";
    public static final String POSTAL_CODE = "[data-test='postalCode']";
    public static final String CONTINUE = "[data-test='continue']";

    public void fillInformation(String firstName, String lastName, String postalCode) {
        page.fill(FIRST_NAME, firstName);
        page.fill(LAST_NAME, lastName);
        page.fill(POSTAL_CODE, postalCode);
        page.click(CONTINUE);
    }

    @Override
    public boolean isLoaded() {
        return page.url().contains("checkout-step-one");
    }
}
