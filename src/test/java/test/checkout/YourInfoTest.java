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
import utils.ElementValidate;


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
        // GÁN products = tổng số sản phẩm trong cart
        List<WebElement> cartItems =
                WaitElement.visibleElements(
                        driver,
                        By.className("cart_item"),
                        10
                );
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
    public void testYourInformationWithValidCredentials() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
        ElementValidate.clearAndType(inputFirstName, "Linh");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
        ElementValidate.clearAndType(inputLastName, "Bao");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
        ElementValidate.clearAndType(inputPostalCode, "123456");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html", "Unable to switch pages.");
        WebElement titlePage = WaitElement.visible(driver, By.className("title"), 10);
        Assert.assertEquals(titlePage.getText(), "Checkout: Overview", "Unable to switch pages.");
    }

    @org.junit.Test
    @Test
    public void testYourInformationWithInvalidLastName() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
        ElementValidate.clearAndType(inputFirstName, "Linh");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
        ElementValidate.clearAndType(inputLastName, "");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
        ElementValidate.clearAndType(inputPostalCode, "123456");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        WebElement errorMsg =WaitElement.visible(driver,By.xpath("//h3"),10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Error: Last Name is required"), "Unexpected error message!");
    }

    @org.junit.Test
    @Test
    public void testYourInformationWithInvalidFirstName() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
        ElementValidate.clearAndType(inputFirstName, "");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
        ElementValidate.clearAndType(inputLastName, "Bao");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
        ElementValidate.clearAndType(inputPostalCode, "123456");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        WebElement errorMsg =WaitElement.visible(driver,By.xpath("//h3"),10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Error: First Name is required"), "Unexpected error message!");
    }
    @org.junit.Test
    @Test
    public void testYourInformationWithInvalidPostalCode() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
        ElementValidate.clearAndType(inputFirstName, "Linh");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
        ElementValidate.clearAndType(inputLastName, "Bao");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
        ElementValidate.clearAndType(inputPostalCode, "");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        WebElement errorMsg =WaitElement.visible(driver,By.xpath("//h3"),10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Error: Postal Code is required"), "Unexpected error message!");
    }

    @org.junit.Test
    @Test
    public void testClickButtonCancel(){
        WebElement btnCancel = WaitElement.clickable(driver, By.id("cancel"), 10);
        Assert.assertTrue(btnCancel.isDisplayed(),"The button cancel is not displayed.");
        btnCancel.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, By.xpath("//span[@class=\"title\"]"), 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
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

