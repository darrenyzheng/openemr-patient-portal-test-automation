import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTests extends BaseTest {

    private ProfilePage profilePage;

    @BeforeEach
    void setUpProfilePage() {

        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = new DashboardPage(driver);
        profilePage = new ProfilePage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickProfileButton();
    }

    @Test
    @Tag("tc24")
    @Tag("regression")
    void testCancelClosesProfileModal() {
        profilePage.clickEditProfileButton();

        assertTrue(profilePage.isProfileModalDisplayed());

        profilePage.clickCancelButton();

        assertTrue(profilePage.isProfileHeaderDisplayed());
    }

    @Test
    @Tag("tc25")
    @Tag("regression")
    void testCloseButtonClosesProfileModal() {
        profilePage.clickEditProfileButton();

        assertTrue(profilePage.isProfileModalDisplayed());

        profilePage.clickProfileModalCloseButton();

        assertTrue(profilePage.isProfileHeaderDisplayed());
    }

    @Test
    @Tag("tc26")
    @Tag("regression")
    void testRefreshClosesProfileModal() {
        profilePage.clickEditProfileButton();

        profilePage.refresh();

        assertTrue(profilePage.isProfileHeaderDisplayed());
        assertFalse(profilePage.isProfileModalDisplayed());
    }
}
