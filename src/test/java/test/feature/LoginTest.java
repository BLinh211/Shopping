package test.feature;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import test.action.LoginPage;
import utils.ExcelUtils;
import utils.WebElementUtils;

import java.util.List;
import java.util.Map;

public class LoginTest {

    WebDriver driver;
    WebElementUtils elementUtils;
    LoginPage loginPage;


    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);

        elementUtils = new WebElementUtils(driver);
    }


    @Test
    public void happyCaseLogin() {

        List<Map<String, String>> testData =
                ExcelUtils.readExcel(
                        "src/test/resources/testdata.xlsx",
                        "HappyCase"
                );

        for (Map<String, String> data : testData) {

            String username = data.get("username");
            String password = data.get("password");

            loginPage.login(username, password);

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
            loginPage.enterUsername(data.get("username"));
            loginPage.enterPassword(data.get("password"));
            loginPage.clickLoginButton();

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
            loginPage.enterUsername(data.get("username"));
            loginPage.enterPassword(data.get("password"));
            loginPage.clickLoginButton();

            // reset lại form cho case tiếp theo
            driver.navigate().refresh();
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
