package test.checkout;

import models.Product;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.WaitElement;
import utils.WebElementUtils;

import java.util.ArrayList;
import java.util.List;

public class CompleteTest {
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
        // Add to Cart
        elementUtils.click("id", "add-to-cart-sauce-labs-backpack");
        // Your info
        elementUtils.click("class", "shopping_cart_link");
        // Overview
        elementUtils.click("id","checkout");
    }
    @org.junit.Test
    @Test
    public void testClickButtonBackHome(){
        WebElement btnBackHome= WaitElement.clickable(driver, By.id("back-to-products"),10);
        Assert.assertTrue(btnBackHome.isDisplayed(),"The button back home is not displayed.");
        btnBackHome.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Unable to switch pages.");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No products displayed!");
    }
    @org.junit.Test
    @Test
    public void testClickIconYourCart(){
        WebElement iconYourCart = WaitElement.clickable(driver, By.className("shopping_cart_link"),10);
        Assert.assertTrue(iconYourCart.isDisplayed(),"Icon Your Cart haven't been displayed.");
        iconYourCart.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch to Your Cart.");
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
