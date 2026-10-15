import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pages.DashboardPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeEach
    void setUpLoginPage() {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);

        loginPage.open(BASE_URL);
    }

    @Test
    @Tag("smoke")
    @Tag("tc01")
    public void verifyValidLogin() {
        loginPage.login(USERNAME, PASSWORD, EMAIL);

        dashboardPage.waitForDashboard();

        String currentUrl = driver.getCurrentUrl();

        assertTrue(
                currentUrl != null &&
                        currentUrl.endsWith(DashboardPage.DASHBOARD_URL)
        );
        assertTrue(dashboardPage.isLoggedIn());
    }

    @Test
    @Tag("regression")
    @Tag("tc02")
    public void verifyEmptyLoginFields() {
        loginPage.login("", "", "");

        WebElement activeElement =
                (WebElement) ((JavascriptExecutor) driver)
                        .executeScript("return document.activeElement");

        assertEquals(
                driver.findElement(LoginPage.USERNAME),
                activeElement
        );
        assertFalse(
                driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc03")
    public void verifyInvalidEmailFormat() {
        String invalidEmail = "invalid-email";

        loginPage.login(USERNAME, PASSWORD, invalidEmail);

        WebElement activeElement =
                (WebElement) ((JavascriptExecutor) driver)
                        .executeScript("return document.activeElement");

        assertEquals(
                driver.findElement(LoginPage.EMAIL),
                activeElement
        );
        assertFalse(
                driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc04")
    public void verifyEmptyUsername() {
        loginPage.login("", PASSWORD, EMAIL);

        WebElement activeElement =
                (WebElement) ((JavascriptExecutor) driver)
                        .executeScript("return document.activeElement");

        assertEquals(
                driver.findElement(LoginPage.USERNAME),
                activeElement
        );
        assertFalse(
                driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc05")
    public void verifyInvalidUsername() {
        String invalidUsername = "invalid-username";

        loginPage.login(invalidUsername, PASSWORD, EMAIL);
        loginPage.waitForErrorMessage();

        assertTrue(
                driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc06")
    public void verifyEmptyPassword() {
        loginPage.login(USERNAME, "", EMAIL);

        WebElement activeElement =
                (WebElement) ((JavascriptExecutor) driver)
                        .executeScript("return document.activeElement");

        assertEquals(
                driver.findElement(LoginPage.PASSWORD),
                activeElement
        );
        assertFalse(
                driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc07")
    public void verifyInvalidPassword() {
        String invalidPassword = "invalid-password";

        loginPage.login(USERNAME, invalidPassword, EMAIL);
        loginPage.waitForErrorMessage();

        assertTrue(
                driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc08")
    public void verifyPasswordVisibilityToggle() {
        loginPage.fillLoginForm(USERNAME, "test123", EMAIL);

        WebElement password = driver.findElement(LoginPage.PASSWORD);

        assertEquals("password", password.getDomProperty("type"));
        assertEquals("test123", password.getDomProperty("value"));

        driver.findElement(LoginPage.PASSWORD_VISIBILITY_BUTTON).click();

        assertEquals("text", password.getDomProperty("type"));
        assertEquals("test123", password.getDomProperty("value"));

        driver.findElement(LoginPage.PASSWORD_VISIBILITY_BUTTON).click();

        assertEquals("password", password.getDomProperty("type"));
        assertEquals("test123", password.getDomProperty("value"));
    }

    @Test
    @Tag("regression")
    @Tag("tc09")
    public void verifyEmptyEmail() {
        loginPage.login(USERNAME, PASSWORD, "");

        loginPage.waitForErrorMessage();

        assertTrue(
                loginPage.getText(LoginPage.ERROR_MESSAGE)
                        .contains("Something went wrong. Please try again.")
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc10")
    public void verifyEmailWithoutDomain() {
        String emailWithoutDomain = "test@";

        loginPage.login(USERNAME, PASSWORD, emailWithoutDomain);

        String validationMessage =
                driver.findElement(LoginPage.EMAIL)
                        .getDomProperty("validationMessage");

        assertEquals(
                "Please enter a part following '@'. '" +
                        emailWithoutDomain +
                        "' is incomplete.",
                validationMessage
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc11")
    public void verifyEmailWithoutLocalPart() {
        String emailWithoutLocalPart = "@test.com";

        loginPage.login(USERNAME, PASSWORD, emailWithoutLocalPart);

        String validationMessage =
                driver.findElement(LoginPage.EMAIL)
                        .getDomProperty("validationMessage");

        assertEquals(
                "Please enter a part followed by '@'. '" +
                        emailWithoutLocalPart +
                        "' is incomplete.",
                validationMessage
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc12")
    public void verifyEmailWithoutDomainSuffix() {
        String emailWithoutDomainSuffix = "test@test";

        loginPage.login(USERNAME, PASSWORD, emailWithoutDomainSuffix);

        loginPage.waitForErrorMessage();

        assertTrue(
                loginPage.getText(LoginPage.ERROR_MESSAGE)
                        .contains("Something went wrong. Please try again.")
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc13")
    public void verifyFieldsAreClearedAfterRefresh() {
        loginPage.fillLoginForm(USERNAME, PASSWORD, EMAIL);

        driver.navigate().refresh();

        String username =
                driver.findElement(LoginPage.USERNAME)
                        .getDomProperty("value");

        assertTrue(username == null || username.isEmpty());

        String password =
                driver.findElement(LoginPage.PASSWORD)
                        .getDomProperty("value");

        assertTrue(password == null || password.isEmpty());

        String email =
                driver.findElement(LoginPage.EMAIL)
                        .getDomProperty("value");

        assertTrue(email == null || email.isEmpty());

        assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    @Tag("smoke")
    @Tag("tc14")
    public void verifyLogoutReturnsToLoginPage() {
        loginPage.login(USERNAME, PASSWORD, EMAIL);

        loginPage.waitForDashboard();

        String currentUrl = driver.getCurrentUrl();

        assertTrue(
                currentUrl != null &&
                        currentUrl.endsWith(DashboardPage.DASHBOARD_URL)
        );

        dashboardPage.logout();

        assertEquals(
                BASE_URL + "?site=default&logout",
                driver.getCurrentUrl()
        );

        assertTrue(
                driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed()
        );
    }
}
