package test.action;

import org.openqa.selenium.WebDriver;
import test.ui.LoginPageUI;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(LoginPageUI.USERNAME_FIELD).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(LoginPageUI.PASSWORD_FIELD).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(LoginPageUI.LOGIN_BUTTON).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
}
