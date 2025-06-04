package com.automation.framework.util;

import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PageWaiterService {
    private final Page page;

    public boolean waitForSelector(String selector, int timeoutMillis) {
        long startTime = System.currentTimeMillis();
        try {
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMillis));
            long duration = System.currentTimeMillis() - startTime;
            log.info("Selector '{}' appeared in {}ms", selector, duration);
            return true;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.warn("Selector '{}' did not appear after {}ms", selector, duration);
            return false;
        }
    }
}
