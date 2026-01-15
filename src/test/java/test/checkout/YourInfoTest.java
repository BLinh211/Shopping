package test.checkout;

import models.Product;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.WaitElement;
import utils.WebElementUtils;

import java.util.ArrayList;
import java.util.List;

public class YourInfoTest {
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
    }

    @org.junit.Test
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

    @org.junit.Test
    @Test
    public void testRemoveAllProduct() {
        int size = products.size();
        for (int i = 0; i < size; i++) {
            List<WebElement> listElementProductBeforeRemove = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
            Assert.assertEquals(listElementProductBeforeRemove.size(), products.size(), "incorrect quantity");
            WebElement nameProduct = listElementProductBeforeRemove.get(0).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
            WebElement btnRemove = listElementProductBeforeRemove.get(0).findElement(By.xpath("./descendant::button"));
            products.removeIf(product -> product.getName().equals(nameProduct.getText()));
            btnRemove.click();
        }
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assert.assertTrue(cartItems.isEmpty(), "incorrect quantity");
        WebElement totalShoppingCar = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCar.getText(), "", "❌ The total number off products in the cart is incorrect!");
    }

    @org.junit.Test
    @Test
    public void testRemoveAProduct() {
        List<WebElement> listElementProductBeforeRemove = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
        Assert.assertEquals(listElementProductBeforeRemove.size(), products.size(), "incorrect quantity");

        WebElement nameProduct = listElementProductBeforeRemove.getFirst().findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
        WebElement btnRemove = listElementProductBeforeRemove.getFirst().findElement(By.xpath("./descendant::button"));
        products.removeIf(product -> product.getName().equals(nameProduct.getText()));
        btnRemove.click();

        List<WebElement> listElementProductAfterRemove = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
        Assert.assertTrue(listElementProductAfterRemove.getFirst().isDisplayed(), "incorrect quantity");
        WebElement totalShoppingCar = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(products.size(), Integer.parseInt(totalShoppingCar.getText()), "❌ The total number off products in the cart is incorrect!");
        for (int i = 0; i < products.size(); i++) {
            WebElement nameProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
            Assert.assertEquals(products.get(i).getName(), nameProductAfterRemove.getText(), "Product name number " + (i + 1) + " is incorrect.");
            WebElement descProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            Assert.assertEquals(products.get(i).getDesc(), descProductAfterRemove.getText(), "Product desc number " + (i + 1) + " is incorrect.");

            WebElement priceProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            Assert.assertEquals("$" + products.get(i).getPrice(), priceProductAfterRemove.getText(), "Product price number " + (i + 1) + " is incorrect.");

            WebElement quantityProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"cart_quantity\"]"));
            Assert.assertEquals("" + products.get(i).getQuantity(), quantityProductAfterRemove.getText(), "Product quantity number " + (i + 1) + " is incorrect.");
        }
    }

    @org.junit.Test
    public void testClickButtonCheckout() {
        WebElement btnCheckout = (WebElement) ExpectedConditions.presenceOfElementLocated(By.id("checkout"));
        Assert.assertTrue(btnCheckout.isDisplayed(), "button checkout not displayed");
        btnCheckout.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html", "Unable to switch pages.");
        WebElement titlePage = WaitElement.visible(driver, By.className("title"), 10);
        Assert.assertEquals(titlePage.getText(), "Checkout: Your Information", "Unable to switch pages.");

    }
    @AfterMethod
        public void tearDown () {
            driver.quit();
        }
    }

