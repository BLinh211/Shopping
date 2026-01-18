package test.ui;

import org.openqa.selenium.By;

public class InventoryPageUI {
    // locator chung cho tất cả product name
    public static final By PRODUCT_NAMES =
            By.className("inventory_item_name");

    // locator động cho button add to cart
    public static final String ADD_TO_CART_BY_PRODUCT_NAME =
            "//div[@class='inventory_item_name' and normalize-space()='%s']" +
                    "/ancestor::div[@class='inventory_item']//button";
    public static final By SHOPPING_CART =
            By.className("shopping_cart_link");
    // InventoryPageUI
    public static final By PRODUCTS =
            By.className("inventory_item");

}
