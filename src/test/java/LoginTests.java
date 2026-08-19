import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;


public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private static final String BASE_URL = System.getenv("BASE_URL");
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");
    private static final String EMAIL = System.getenv("EMAIL");

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        driver.get(BASE_URL);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testValidLogin() {
        loginPage.login(USERNAME, PASSWORD, EMAIL);
    }

    @Test
    public void testEmptyLogin() {
        loginPage.login("", "", "");

        WebElement activeElement = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return document.activeElement");

        assertEquals(driver.findElement(LoginPage.USERNAME), activeElement);
        assertFalse(driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed());
    }

    @Test
    public void testWrongFormatEmail() {
        String invalidEmail = "invalid-email";
        loginPage.login(USERNAME, PASSWORD, invalidEmail);

        WebElement activeElement = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return document.activeElement");

        assertEquals(driver.findElement(LoginPage.EMAIL), activeElement);
        assertFalse(driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed());
    }

    @Test
    public void testEmptyUsername() {
        loginPage.login("", PASSWORD, EMAIL);

        WebElement activeElement = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return document.activeElement");

        assertEquals(driver.findElement(LoginPage.USERNAME), activeElement);
        assertFalse(driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed());
    }

    @Test
    public void testInvalidUsername() {
        String invalidUsername = "invalid-username";
        loginPage.login(invalidUsername, PASSWORD, EMAIL);

        assertTrue(driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed());
    }

    @Test
    public void testEmptyPassword() {
        loginPage.login(USERNAME, "", EMAIL);

        WebElement activeElement = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return document.activeElement");

        assertEquals(driver.findElement(LoginPage.PASSWORD), activeElement);
        assertFalse(driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed());
    }

    @Test
    public void testInvalidPassword() {
        String invalidPassword = "invalid-password";
        loginPage.login(USERNAME, invalidPassword, EMAIL);

        assertTrue(driver.findElement(LoginPage.ERROR_MESSAGE).isDisplayed());
    }

    @Test
    public void testPasswordVisibilityToggle() {
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
    public void testEmptyEmail() {
        loginPage.login(USERNAME, PASSWORD, "");

        assertTrue(
                loginPage.getText(LoginPage.ERROR_MESSAGE)
                        .contains("Something went wrong. Please try again.")
        );
    }

    @Test
    public void testNoDomainEmail() {
        String emailWithoutDomain = "test@";

        loginPage.login(USERNAME, PASSWORD, emailWithoutDomain);

        String validationMessage =
                driver.findElement(LoginPage.EMAIL)
                        .getDomProperty("validationMessage");

        assertEquals(
                "Please enter a part following '@'. '" + emailWithoutDomain + "' is incomplete.",
                validationMessage
        );
    }

    @Test
    public void testNoLocalPartEmail() {
        String emailWithoutLocalPart = "@test.com";

        loginPage.login(USERNAME, PASSWORD, emailWithoutLocalPart);

        String validationMessage =
                driver.findElement(LoginPage.EMAIL)
                        .getDomProperty("validationMessage");

        assertEquals(
                "Please enter a part followed by '@'. '" + emailWithoutLocalPart + "' is incomplete.",
                validationMessage
        );
    }

    @Test
    public void testNoDomainSuffixEmail() {
        String emailWithoutDomainSuffix = "test@test";

        loginPage.login(USERNAME, PASSWORD, emailWithoutDomainSuffix);

        assertTrue(
                loginPage.getText(LoginPage.ERROR_MESSAGE)
                        .contains("Something went wrong. Please try again.")
        );
    }

    @Test
    public void testFieldsAfterRefresh() {
        loginPage.fillLoginForm(USERNAME, PASSWORD, EMAIL);

        driver.navigate().refresh();

        String username =
                driver.findElement(LoginPage.USERNAME).getDomProperty("value");
        assertTrue(username == null || username.isEmpty());

        String password =
                driver.findElement(LoginPage.PASSWORD).getDomProperty("value");
        assertTrue(password == null || password.isEmpty());

        String email =
                driver.findElement(LoginPage.EMAIL).getDomProperty("value");
        assertTrue(email == null || email.isEmpty());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}