import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SettingsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettingsTests extends BaseTest {

    private WebDriver driver;
    private SettingsPage settingsPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        settingsPage = new SettingsPage(driver);

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickSettingsButton();
    }

    @Test
    public void testSignAndSaveWithNoInput() {
        settingsPage.openSignatureSettings();
        settingsPage.saveSignature();

        assertTrue(settingsPage.isSignatureAlertDisplayed());

        assertEquals(
                "Please provide a signature first",
                settingsPage.getSignatureAlertMessage()
        );
    }

    @Test
    public void testCancelSignatureSettings() {
        settingsPage.openSignatureSettings();
        settingsPage.cancelSignature();

        assertFalse(settingsPage.isSignatureAlertDisplayed());
        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    public void testCloseSignatureSettingsWithEscape() {
        settingsPage.openSignatureSettings();

        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);

        assertFalse(settingsPage.isSignatureAlertDisplayed());
        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    public void testManageLoginCredentials() {
        settingsPage.openLoginCredentials();

        settingsPage.enterNewUsername();
        settingsPage.enterIncorrectCurrentPassword();
        settingsPage.submitCredentialChanges();

        assertEquals(
                "The credentials you entered were invalid.",
                settingsPage.getInvalidCredentialsAlertMessage()
        );
    }

    @Test
    public void testCloseLoginCredentialsWithEscape() {
        settingsPage.openLoginCredentials();

        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        driver.switchTo().defaultContent();

        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    public void testApplySelectedTheme() {
        settingsPage.openThemeSettings();
        settingsPage.applyTheme();

        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    public void testRefreshThemeSettings() {
        settingsPage.openThemeSettings();
        driver.navigate().refresh();

        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}