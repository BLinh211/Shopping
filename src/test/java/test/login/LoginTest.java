package test.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import utils.ExcelUtils;
import utils.WebElementUtils;

import java.util.List;
import java.util.Map;

public class LoginTest {

    WebDriver driver;
    WebElementUtils elementUtils;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");


        elementUtils = new WebElementUtils(driver);
    }

    @Test
    public void happyCaseLogin() {

        // Đọc dữ liệu từ Excel
        List<Map<String, String>> testData =
                ExcelUtils.readExcel(
                        "src/test/resources/testdata.xlsx",
                        "HappyCase"
                );

        // Chạy từng dòng dữ liệu
        for (Map<String, String> data : testData) {

            String username = data.get("username");
            String password = data.get("password");

            // Thao tác UI qua WebElementUtils
            elementUtils.sendKeys("id", "user-name", username);
            elementUtils.sendKeys("id", "password", password);
            elementUtils.click("id", "login-button");

            // reset lại form cho case tiếp theo
            driver.navigate().refresh();
        }
    }
    @Test
    public void UpperCaseLogin() {

        // Đọc dữ liệu từ Excel
        List<Map<String, String>> testData =
                ExcelUtils.readExcel(
                        "src/test/resources/testdata.xlsx",
                        "UpperCase"
                );

        // Chạy từng dòng dữ liệu
        for (Map<String, String> data : testData) {

            String username = data.get("username");
            String password = data.get("password");

            // Thao tác UI qua WebElementUtils
            elementUtils.sendKeys("id", "user-name", username);
            elementUtils.sendKeys("id", "password", password);
            elementUtils.click("id", "login-button");

            // reset lại form cho case tiếp theo
            driver.navigate().refresh();
        }
    }
    @Test
    public void UnhappyCaseLogin() {

        // Đọc dữ liệu từ Excel
        List<Map<String, String>> testData =
                ExcelUtils.readExcel(
                        "src/test/resources/testdata.xlsx",
                        "UnhappyCase"
                );

        // Chạy từng dòng dữ liệu
        for (Map<String, String> data : testData) {

            String username = data.get("username");
            String password = data.get("password");

            // Thao tác UI qua WebElementUtils
            elementUtils.sendKeys("id", "user-name", username);
            elementUtils.sendKeys("id", "password", password);
            elementUtils.click("id", "login-button");

            // reset lại form cho case tiếp theo
            driver.navigate().refresh();
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
