package test.inventory;

import models.Product;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import utils.WaitElement;
import utils.WebElementUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class YourCartTest {
    WebDriver driver;
    WebElementUtils elementUtils;
    List<Product> products;
    @Before
    public void setup() {

        //  KHỞI TẠO DRIVER
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        elementUtils = new WebElementUtils(driver);
        products = new ArrayList<>();

        //  LOGIN
        elementUtils.sendKeys("id", "user-name", "standard_user");
        elementUtils.sendKeys("id", "password", "secret_sauce");
        elementUtils.click("id", "login-button");
    }
    @Test
    public void testValidCartInformation() {
        List<WebElement> listElementProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
        Assert.assertEquals(products.size(), listElementProduct.size(), "incorrect quantity");
        for (int i = 0; i < products.size(); i++) {
            WebElement nameProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
            Assert.assertEquals(products.get(i).getName(), nameProduct.getText(), "Product name number " + (i + 1) + " is incorrect.");
            WebElement descProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            Assert.assertEquals(products.get(i).getDesc(), descProduct.getText(), "Product desc number " + (i + 1) + " is incorrect.");

            WebElement priceProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            Assert.assertEquals("$" + products.get(i).getPrice(), priceProduct.getText(), "Product price number " + (i + 1) + " is incorrect.");

            WebElement quantityProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"cart_quantity\"]"));
            Assert.assertEquals("" + products.get(i).getQuantity(), quantityProduct.getText(), "Product quantity number " + (i + 1) + " is incorrect.");
        }
        WebElement totalShoppingCart = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCart.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");
    }
    @Test
    public void testRemoveAllProduct() {
        int size=products.size();
        for (int i = 0; i < size; i++) {
            List<WebElement> listElementProductBeforeRemove = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
            Assert.assertEquals(listElementProductBeforeRemove.size(), products.size(), "incorrect quantity");
            WebElement nameProduct = listElementProductBeforeRemove.getFirst().findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
            WebElement btnRemove = listElementProductBeforeRemove.getFirst().findElement(By.xpath("./descendant::button"));
            products.removeIf(product -> product.getName().equals(nameProduct.getText()));
            btnRemove.click();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement el = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@class='removed_cart_item']")
                )
        );

        List<WebElement> cartItems =
                driver.findElements(By.className("cart_item"));

        Assert.assertTrue(cartItems.isEmpty(), "Cart is not empty!");

    }
    @Test

    public void testClickButtonCheckOut() {
        WebElement btnCheckOut = WaitElement.clickable(driver, By.id("checkout"), 15);
        Assert.assertTrue(btnCheckOut.isDisplayed(), "button checkout haven't been displayed");

        btnCheckOut.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html", "Unable to switch pages.");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No products displayed!");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
