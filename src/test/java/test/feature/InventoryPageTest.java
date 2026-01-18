package test.feature;

import models.Product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import test.action.InventoryPage;
import test.action.LoginPage;
import test.ui.InventoryPageUI;
import utils.WaitElement;
import utils.WebElementUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPageTest extends InventoryPageUI {
    WebDriver driver;
    WebElementUtils elementUtils;
    List<Product> products;
    LoginPage loginPage;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void setup() {

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        elementUtils = new WebElementUtils(driver);
        products = new ArrayList<>();

        inventoryPage = new InventoryPage(driver);

        loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

    }
    @Test
    public void testAddToCartProduct()
    {

        WebElement product =
                inventoryPage.getProducts().getFirst();

        inventoryPage.getAddToCartButton(product).click();

        Product p = new Product();
        p.setName(inventoryPage.getProductName(product));
        p.setPrice(inventoryPage.getProductPrice(product));
        p.setDesc(inventoryPage.getProductDesc(product));
        p.setQuantity(1);
        products.add(p);

        Assert.assertEquals(
                inventoryPage.getCartBadgeCount(),
                products.size()
        );

        inventoryPage.moveToCart();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/cart.html"
        );
    }
    @Test
    public void testAddToCartNoProducts() {
        List<WebElement> listProduct = driver.findElements(InventoryPage.PRODUCT_NAMES);
        Assert.assertTrue(listProduct.getFirst().isDisplayed(), "No products displayed!");

        WebElement totalShoppingCart = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCart.getText(), "", "❌ The total number off products in the cart is incorrect!");

        totalShoppingCart.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, By.xpath("//span[@class=\"title\"]"), 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
