package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static pages.DashboardPage.DASHBOARD_URL;

public class LoginPage extends BasePage {

    public static final By USERNAME =
            By.id("uname");

    public static final By PASSWORD =
            By.id("pass");

    public static final By EMAIL =
            By.id("passaddon");

    public static final By LOGIN_BUTTON =
            By.cssSelector(".btn.btn-success");

    public static final By LANGUAGE_FIELD =
            By.id("selLanguage");

    public static final By PASSWORD_VISIBILITY_BUTTON =
            By.id("password-icon");

    public static final By ERROR_MESSAGE =
            By.cssSelector(".h6.alert");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password, String email) {
        fillLoginForm(username, password, email);

        List<WebElement> languageFields = driver.findElements(LANGUAGE_FIELD);

        if (!languageFields.isEmpty() && languageFields.get(0).isDisplayed()) {
            new Select(languageFields.get(0)).selectByValue("1");
        }

        click(LOGIN_BUTTON);
    }

    public String waitForErrorMessage() {
        return wait.until(driver -> {
            try {
                WebElement errorMessage =
                        driver.findElement(ERROR_MESSAGE);

                if (errorMessage.isDisplayed()) {
                    String text = errorMessage.getText();

                    if (!text.isEmpty()) {
                        return text;
                    }
                }

                return null;

            } catch (WebDriverException e) {
                return null;
            }
        });
    }

    public void fillLoginForm(
            String username,
            String password,
            String email
    ) {
        type(USERNAME, username);
        type(PASSWORD, password);
        type(EMAIL, email);
    }

    public void waitForDashboard() {
        wait.until(
                ExpectedConditions.urlContains(DASHBOARD_URL)
        );
    }
}