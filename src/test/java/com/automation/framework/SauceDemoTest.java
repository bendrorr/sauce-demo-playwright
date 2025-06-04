package com.automation.framework;

import com.automation.framework.config.ComponentScanConfig;
import com.automation.framework.pages.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("loginDataProvider")
    public void login_shouldBehaveAsExpected(String username, String password, boolean shouldLoginSucceed) {
        loginPage.navigate();
        assertThat(loginPage.isLoaded()).isTrue();

        loginPage.login(username, password);

        assertThat(inventoryPage.isLoaded()).isEqualTo(shouldLoginSucceed);
    }

    @Test
    public void purchase_shouldCompleteSuccessfully() {
        loginPage.navigate();
        assertThat(loginPage.isLoaded()).isTrue();

        loginPage.login("standard_user", "secret_sauce");
        assertThat(inventoryPage.isLoaded()).isTrue();

        inventoryPage.addProductToCart("sauce-labs-backpack");
        inventoryPage.addProductToCart("sauce-labs-bike-light");
        inventoryPage.goToCart();
        assertThat(cartPage.isLoaded()).isTrue();

        cartPage.clickCheckout();
        assertThat(checkoutPage.isLoaded()).isTrue();

        checkoutPage.fillInformation("firstName", "lastName", "55555");
        assertThat(checkoutOverviewPage.isLoaded()).isTrue();

        checkoutOverviewPage.finishOrder();
    }

    private static Stream<Arguments> loginDataProvider() {
        return Stream.of(
                Arguments.of("standard_user", "secret_sauce", true),
                Arguments.of("problem_user", "secret_sauce", true),
                Arguments.of("invalid_user", "invalid_password", false)
        );
    }
}
