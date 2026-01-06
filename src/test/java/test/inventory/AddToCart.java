package test.inventory;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import utils.WebElementUtils;

public class AddToCart {

    WebDriver driver;
    WebElementUtils elementUtils;

    @Before
    public void setup() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        //  KHỞI TẠO DRIVER
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        elementUtils = new WebElementUtils(driver);

        //  LOGIN
        elementUtils.sendKeys("id", "user-name", "standard_user");
        elementUtils.sendKeys("id", "password", "secret_sauce");
        elementUtils.click("id", "login-button");
    }
    @Test
    public void AddToCart()
    {

    }
}
