import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.Keys;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SettingsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettingsTests extends BaseTest {

    private SettingsPage settingsPage;

    @BeforeEach
    void setUpSettingsPage() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        settingsPage = new SettingsPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickSettingsButton();
    }

    @Test
    @Tag("regression")
    @Tag("tc32")
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
    @Tag("regression")
    @Tag("tc33")
    public void testCancelSignatureSettings() {
        settingsPage.openSignatureSettings();
        settingsPage.cancelSignature();

        assertFalse(settingsPage.isSignatureAlertDisplayed());
        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    @Tag("regression")
    @Tag("tc34")
    public void testCloseSignatureSettingsWithEscape() {
        settingsPage.openSignatureSettings();

        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);

        assertFalse(settingsPage.isSignatureAlertDisplayed());
        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    @Tag("regression")
    @Tag("tc35")
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
    @Tag("regression")
    @Tag("tc36")
    public void testCloseLoginCredentialsWithEscape() {
        settingsPage.openLoginCredentials();

        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        driver.switchTo().defaultContent();

        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    @Tag("smoke")
    @Tag("tc37")
    public void testApplySelectedTheme() {
        settingsPage.openThemeSettings();
        settingsPage.applyTheme();

        assertTrue(settingsPage.isSettingsHeaderVisible());
    }

    @Test
    @Tag("regression")
    @Tag("tc38")
    public void testRefreshThemeSettings() {
        settingsPage.openThemeSettings();
        driver.navigate().refresh();

        assertTrue(settingsPage.isSettingsHeaderVisible());
    }
}
