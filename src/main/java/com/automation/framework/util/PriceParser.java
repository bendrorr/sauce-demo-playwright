package com.automation.framework.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceParser {

    public static Double parsePrice(String priceText) {
        return Double.valueOf(priceText.replace("$", ""));
    }
}
