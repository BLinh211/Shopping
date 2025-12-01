import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.Time;

public class TestDemo {
    static WebDriver driver = null;
    public static void main(String[]args){
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://accounts.saucelabs.com");

        driver.quit();
    }
}
