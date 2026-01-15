package test.inventory;

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
import utils.WaitElement;
import utils.WebElementUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AddToCartTest {
    WebDriver driver;
    WebElementUtils elementUtils;
    List<Product> products;


    @BeforeMethod
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
    public void testAddToCartProduct()
    {

        List<WebElement> listProduct =
                WaitElement.visibleElements(
                        driver,
                        By.xpath("//div[@class='inventory_item']"),
                        10
                );

        // click Add to cart của sản phẩm đầu tiên
        WebElement addBtn =
                listProduct.getFirst().findElement(By.xpath(".//button"));
        Product product = new Product();
        addBtn.click();
        Assert.assertEquals(addBtn.getText().trim(),"Remove","Unable to add the product.");
        WebElement nameProduct = listProduct.getFirst().findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
        product.setName(nameProduct.getText());
        WebElement priceProduct = listProduct.getFirst().findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
        String priceNumber = priceProduct.getText().replace("$", "");
        product.setPrice(Double.parseDouble(priceNumber));
        WebElement descProduct = listProduct.getFirst().findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
        product.setDesc(descProduct.getText());

        int quantityProduct = 1;
        product.setQuantity(quantityProduct);
        products.add(product);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement totalShoppingCart = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("shopping_cart_link")
                )
        );

        Assert.assertEquals(
                totalShoppingCart.getText(),
                products.size() + "",
                "The total number off products in the cart is incorrect!"
        );

        totalShoppingCart.click();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/cart.html",
                "Unable to switch pages."
        );

        WebElement title =
                WaitElement.visible(
                        driver,
                        By.xpath("//span[@class='title']"),
                        10
                );


        Assert.assertEquals(
                title.getText(),
                "Your Cart",
                "Unable to switch pages."
        );





    }
    @Test
    public void testAddToCartNoProducts() {
        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
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
