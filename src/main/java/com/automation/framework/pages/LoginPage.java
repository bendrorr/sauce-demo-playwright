package com.automation.framework.pages;

import com.automation.framework.util.PageWaiterService;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginPage implements Loadable {
    private final Page page;
    private final PageWaiterService pageWaiterService;

    private static final String USERNAME_INPUT = "[data-test='username']";
    private static final String PASSWORD_INPUT = "[data-test='password']";
    private static final String LOGIN_BUTTON = "[data-test='login-button']";

    public void navigate() {
        page.navigate("https://www.saucedemo.com/");
    }

    public void login(String username, String password) {
        page.fill(USERNAME_INPUT, username);
        page.fill(PASSWORD_INPUT, password);
        page.click(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return page.locator("[data-test='error']").textContent().trim();
    }

    @Override
    public boolean isLoaded() {
        return pageWaiterService.waitForSelector(LOGIN_BUTTON, 3000);
    }
}
