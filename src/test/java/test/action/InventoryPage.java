package test.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebElementUtils;

import java.util.List;

public class InventoryPage extends test.ui.InventoryPageUI {
    WebDriver driver;
    WebElementUtils elementUtils;
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new WebElementUtils(driver);
    }

    public int getProductSize() {
        return driver.findElements(
                By.className("cart_item")
        ).size();
    }


    public WebElement getProductByName(String productName) {
        for (WebElement product :
                driver.findElements(InventoryPage.PRODUCT_NAMES)) {

            if (product.getText().trim().equals(productName)) {
                return product;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public void addToCartByProductName(String productName) {
        WebElement product = getProductByName(productName);

        WebElement addToCartBtn =
                product.findElement(By.xpath(
                        "./ancestor::div[@class='inventory_item']" +
                                "//button[contains(@id,'add-to-cart')]"
                ));

        addToCartBtn.click();
    }
    public void moveToCart(){
        elementUtils.click("class","shopping_cart_link");
    }
    public int getCartBadgeCount() {
        String text = driver.findElement(
                By.className("shopping_cart_link")
        ).getText().trim();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }
    // InventoryPage
    public List<WebElement> getProducts() {
        return driver.findElements(PRODUCTS);
    }


    public WebElement getAddToCartButton(WebElement product) {
        return product.findElement(By.xpath(".//button"));
    }
    public String getProductName(WebElement product) {
        return product.findElement(
                By.className("inventory_item_name")
        ).getText();
    }

    public double getProductPrice(WebElement product) {
        String price = product.findElement(
                By.className("inventory_item_price")
        ).getText().replace("$", "");
        return Double.parseDouble(price);
    }

    public String getProductDesc(WebElement product) {
        return product.findElement(
                By.className("inventory_item_desc")
        ).getText();
    }


}
