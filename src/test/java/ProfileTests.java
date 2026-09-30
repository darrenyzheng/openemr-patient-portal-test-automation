import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTests extends BaseTest {

    private WebDriver driver;
    private ProfilePage profilePage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        profilePage = new ProfilePage(driver);

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickProfileButton();
    }

    @Test
    void testCancelClosesProfileModal() {
        profilePage.clickEditProfileButton();

        assertTrue(profilePage.isProfileModalDisplayed());

        profilePage.clickCancelButton();

        assertTrue(profilePage.isProfileHeaderDisplayed());
    }

    @Test
    void testCloseButtonClosesProfileModal() {
        profilePage.clickEditProfileButton();

        assertTrue(profilePage.isProfileModalDisplayed());

        profilePage.clickProfileModalCloseButton();

        assertTrue(profilePage.isProfileHeaderDisplayed());
    }

    @Test
    void testRefreshClosesProfileModal() {
        profilePage.clickEditProfileButton();

        profilePage.refresh();

        assertTrue(profilePage.isProfileHeaderDisplayed());
        assertFalse(profilePage.isProfileModalDisplayed());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}