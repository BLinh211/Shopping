package Exercise4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Exercise {
    static WebDriver driver=null;
    public static void main(String[] args) throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String pageTitle1 = driver.getTitle();

        //Login
        WebElement userName = wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        userName.sendKeys("standard_user");

        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        password.sendKeys("secret_sauce");

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginBtn.click();

        //Select Dropdownlist
        Select select = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
        select.selectByValue("lohi");

        //add to cart
        WebElement addToCart1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@id,'backpack')]")));
        addToCart1.click();
        WebElement addToCart2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@id,'t-shirt')]")));
        addToCart2.click();
        //print number on Cartbadge
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartNumber = cartBadge.getText();

        //step 4s
        WebElement cartLink = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        cartLink.click();

        String title = driver.findElement(By.className("title")).getText();

        if(title.equals("Your Cart")){
            System.out.println("In Your Cart");
        }else{
            System.out.println("Not in Your Cart");
        }
        String titleItem1 = driver.findElement(By.id("item_5_title_link")).getText();
        System.out.println("Item Title 1: " + titleItem1);
        String titleItem2 = driver.findElement(By.id("item_2_title_link")).getText();
        System.out.println("Item Title 1: " + titleItem2);

        List<WebElement> removeButtons =
                driver.findElements(By.className("btn_secondary"));

        int numberRemove = removeButtons.size();

        if(numberRemove == 2){
            System.out.println("Màn hình hiển thị đúng 2 button Remove");
        }else{
            System.out.println("Sai số lượng button Remove, thực tế: " + numberRemove);
        }

        //step 5
        WebElement btnCheckOut = driver.findElement(By.id("checkout"));
        btnCheckOut.click();
        String title1 = driver.findElement(By.className("title")).getText();
        System.out.println("On Screen:" + title1);

        WebElement firstName2 = driver.findElement(By.id("first-name"));
        firstName2.sendKeys("Chris");
        WebElement lastName2 = driver.findElement(By.id("last-name"));
        lastName2.sendKeys("Heimworth");
        WebElement postalCode = driver.findElement(By.id("postal-code"));;
        postalCode.sendKeys("12345");
        //step 6 click button
        WebElement submitBtn = driver.findElement(By.id("continue"));
        submitBtn.click();
        // In ra tên
        List<WebElement> itemNames = driver.findElements(
                By.xpath("//div[@class='inventory_item_name']")
        );
        for (WebElement name : itemNames) {
            System.out.println("Name of items: " + name.getText());
        }

        // In ra giá
        List<WebElement> prices = driver.findElements(
                By.xpath("//div[@class='inventory_item_price']")
        );
        for (WebElement price : prices) {
            System.out.println("Price of each follows: " + price.getText());
        }
        //get shipping info
        WebElement shippingInfo = driver.findElement(
                By.xpath("//div[@data-test='shipping-info-value']")
        );
        String shippingText = shippingInfo.getText();
        System.out.println("Shipping Information: " + shippingText);
        //kiểm tra dung hay sai
        if (shippingText.equals("Free Pony Express Delivery!")) {
            System.out.println("Shipping info is correct");
        } else {
            System.out.println("Shipping info is incorrect");
        }
        //get tổng price
        double sumPrice = 0;
        double tax = Double.parseDouble(driver.findElement(By.xpath("//div[@class='summary_tax_label'])")).getText().replace("$",""));
        for (WebElement price : prices) {
            String priceText = price.getText();     // "$7.99"
            double priceValue = Double.parseDouble(
                    priceText.replace("$", "")
            );
            sumPrice += priceValue;
        }
        double expectedPrice = (sumPrice+tax);
        //check total price
        double actualTotal = Double.parseDouble(driver.findElement(By.xpath("//div[@class='summary_total_label']")).getText().replace("Total: $", ""));
        if(expectedPrice == actualTotal){
            System.out.println("Total price is correct");
        }else {
            System.out.println("Total price is incorrect");
        }
        //click btn finish
        WebElement btnFinish = driver.findElement(By.id("finish"));
        btnFinish.click();
        String completeTitle = driver.findElement(By.id("title")).getText();
        System.out.println(completeTitle);
        String thankYou = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText();
        System.out.println(thankYou);
        String completeText = driver.findElement(By.xpath("//div[@class='complete-text']")).getText();
        System.out.println(completeText);
        String btnBackHome  = driver.findElement(By.id("back-to-products")).getText();
        System.out.println(btnBackHome);
    }
}
