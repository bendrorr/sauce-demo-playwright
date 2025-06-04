package com.automation.framework;

import com.automation.framework.config.ComponentScanConfig;
import com.automation.framework.model.Product;
import com.automation.framework.pages.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {ComponentScanConfig.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SauceDemoTest {
    private final LoginPage loginPage;
    private final InventoryPage inventoryPage;
    private final CartPage cartPage;
    private final CheckoutPage checkoutPage;
    private final CheckoutOverviewPage checkoutOverviewPage;

    @Test
    void validLogin_shouldNavigateToInventoryPage() {
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        assertThat(inventoryPage.isLoaded())
                .isTrue();
    }

    @Test
    void lockedUserLogin_shouldShowError() {
        loginPage.navigate();
        loginPage.login("locked_out_user", "secret_sauce");

        assertThat(loginPage.getErrorMessage()
                .contains("Sorry, this user has been locked out"));
    }

    @Test
    void loginWithEmptyFields_shouldShowValidationError() {
        loginPage.navigate();
        loginPage.login("", "");

        assertThat(loginPage.getErrorMessage()
                .contains("Username is required"));
    }

    @Test
    void productsShouldDisplayNameAndPrice() {
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        for (Product product : inventoryPage.getAllProducts()) {
            assertThat(product.getName())
                    .isNotNull();

            assertThat(product.getPrice())
                    .isGreaterThan(0);
        }
    }

    @Test
    void addToCartButtonShouldToggleToRemove() {
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("sauce-labs-backpack");

        assertThat(inventoryPage.isRemoveButtonVisible("sauce-labs-backpack"))
                .isTrue();
    }

    @Test
    void addedProductShouldAppearInCart() {
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("sauce-labs-bike-light");
        inventoryPage.goToCart();
        assertThat(cartPage.containsProduct("sauce-labs-bike-light")).isTrue();
    }

    @Test
    void cartShouldUpdateAfterRemovingProduct() {
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("sauce-labs-bike-light");
        inventoryPage.goToCart();
        cartPage.removeProduct("sauce-labs-bike-light");
        assertThat(cartPage.containsProduct("sauce-labs-bike-light")).isFalse();
    }

    @Test
    void completeCheckout_shouldShowThankYouMessage() {
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("sauce-labs-backpack");
        inventoryPage.goToCart();
        cartPage.clickCheckout();

        checkoutPage.fillInformation("John", "Doe", "12345");
        checkoutOverviewPage.finishOrder();

        assertThat(checkoutOverviewPage.getConfirmationMessage())
                .isEqualTo("Thank you for your order!");
    }
}
