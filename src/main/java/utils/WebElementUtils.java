package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebElementUtils {

    WebDriver driver;
    WebDriverWait wait;

    public WebElementUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public By getBy(String type, String value) {
        switch (type.toLowerCase()) {
            case "id":
                return By.id(value);
            case "name":
                return By.name(value);
            case "xpath":
                return By.xpath(value);
            case "css":
                return By.cssSelector(value);
            default:
                throw new RuntimeException("Locator type not supported: " + type);
        }
    }

    public WebElement getElement(String type, String value) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        getBy(type, value)
                )
        );
    }

    public void sendKeys(String type, String value, String text) {
        WebElement element = getElement(type, value);
        element.clear();
        element.sendKeys(text);
    }


    public void click(String type, String value) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(
                        getBy(type, value)
                )
        );
        element.click();
    }

}
