package com.automation.framework.config;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PlaywrightConfig {
    @Value("${browser.headless:false}")
    private boolean headless;

    @Value("${browser.timeout:30000}")
    private int timeout;

    @Bean
    public Playwright playwright() {
        log.info("Creating Playwright instance");
        return Playwright.create();
    }

    @Bean
    public Browser browser(Playwright playwright) {
        log.info("Creating browser instance - headless: {}", headless);
        return playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(100));
    }

    @Bean
    public BrowserContext browserContext(Browser browser) {
        log.info("Creating browser context with timeout: {}", timeout);
        BrowserContext context = browser.newContext();
        context.setDefaultTimeout(timeout);
        return context;
    }

    @Bean
    public Page page(BrowserContext context) {
        log.info("Creating new page");
        return context.newPage();
    }

}
