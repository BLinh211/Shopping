package test.checkout;

import models.Product;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import utils.WaitElement;
import utils.WebElementUtils;

import java.util.ArrayList;
import java.util.List;

public class OverviewTest {
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
    @Test
    public void testValidProductInformationForTheDesiredOrder() {
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
    public void testVerifyCheckoutSummaryInformation() {
        WebElement paymentInformation = WaitElement.visible(driver, By.xpath("//div[@data-test=\"payment-info-value\"]"), 10);
        Assert.assertEquals(paymentInformation.getText(), "SauceCard #31337", "Incorrect information displayed");
        WebElement shippingInformation = WaitElement.visible(driver, By.xpath("//div[@data-test=\"shipping-info-value\"]"), 10);
        Assert.assertEquals(shippingInformation.getText(), "Free Pony Express Delivery!", "Incorrect information displayed");
        double sumPriceProducts = 0;
        for (Product product : products) {
            sumPriceProducts += product.getPrice();
        }
        WebElement itemTotal = WaitElement.visible(driver, By.className("summary_subtotal_label"), 10);
        Assert.assertEquals(Double.parseDouble(itemTotal.getText().replace("Item total: $", "").trim()), sumPriceProducts, "The total amount of the products is incorrect.");
        WebElement taxElement = WaitElement.visible(driver, By.className("summary_tax_label"), 10);
        Assert.assertTrue(taxElement.isDisplayed(), "tax not displayed");
        double tax = Double.parseDouble(taxElement.getText().replace("Tax: $", "").trim());
        WebElement totalElement = WaitElement.visible(driver, By.className("summary_total_label"), 10);
        String total = (totalElement.getText().replace("Total: $", "").trim());
        Assert.assertEquals(String.format("%.2f", tax + sumPriceProducts), total, "The total is incorrect.");

    }

    @Test
    public void testClickButtonCancel() {
        WebElement btnCancel = WaitElement.clickable(driver, By.id("cancel"), 10);
        Assert.assertTrue(btnCancel.isDisplayed(), "The button cancel is not displayed.");
        btnCancel.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Unable to switch pages.");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No products displayed!");
    }

    @Test
    public void testClickButtonFinish() {
        WebElement btnFinish = WaitElement.clickable(driver, By.id("finish"), 10);
        Assert.assertTrue(btnFinish.isDisplayed(), "The button finish is not displayed.");
        btnFinish.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html", "Unable to switch pages.");

        WebElement titlePage = WaitElement.visible(driver, By.className("title"), 10);
        Assert.assertEquals(titlePage.getText(), "Checkout: Complete!", "Unable to switch pages.");
        WebElement completeHeaderPage = WaitElement.visible(driver, By.xpath("//h2[@class=\"complete-header\"]"), 10);
        Assert.assertEquals(completeHeaderPage.getText(), "Thank you for your order!", "Incorrect display");
        WebElement completeTextPage = WaitElement.visible(driver, By.xpath("//div[@class=\"complete-text\"]"), 10);
        Assert.assertEquals(completeTextPage.getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!", "Incorrect display");
    }



    @AfterMethod
    public void tearDown () {
        driver.quit();
    }
}
