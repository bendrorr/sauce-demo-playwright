package com.automation.framework.mapper;

import com.automation.framework.model.Product;
import com.automation.framework.util.NullUtil;
import com.automation.framework.util.PriceParser;
import com.microsoft.playwright.Locator;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(Locator itemLocator) {

        return Product.builder()
                .name(NullUtil.getOrNull(() -> itemLocator.locator(".inventory_item_name").textContent().trim()))
                .description(NullUtil.getOrNull(() -> itemLocator.locator(".inventory_item_desc").textContent().trim()))
                .price(NullUtil.getOrNull(() -> PriceParser.parsePrice(itemLocator.locator(".inventory_item_price").textContent())))
                .build();
    }
}
