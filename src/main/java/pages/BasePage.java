package pages;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected final WebDriverWait wait;
    protected static final String BASE_URL = System.getenv("BASE_URL");
    protected static final String USERNAME = System.getenv("USERNAME");
    protected static final String PASSWORD = System.getenv("PASSWORD");
    protected static final String EMAIL = System.getenv("EMAIL");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void click(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            wait.until(driver -> {
                try {
                    driver.findElement(locator).click();
                    return true;
                } catch (ElementClickInterceptedException ex) {
                    return false;
                }
            });
        }
    }

    public void type(By locator, String text) {
        WebElement element = this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(text);
    }

    public String getText(By locator) {
        WebElement element = this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    public void refresh() {
        this.driver.navigate().refresh();
    }

    public void open(String url) {
        driver.get(url);
    }
}