import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import pages.DashboardPage;
import pages.HelpPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpTests extends BaseTest {

    private HelpPage helpPage;

    @BeforeEach
    public void setUpHelpPage() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        helpPage = new HelpPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickHelpButton();
    }

    @Test
    @Tag("smoke")
    @Tag("tc51")
    public void verifyHelpItems() {
        assertEquals(
                HelpPage.EXPECTED_HELP_ITEMS,
                helpPage.getHelpItems()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc52")
    public void verifyHelpItemsAfterRefresh() {
        helpPage.refresh();

        assertEquals(
                HelpPage.EXPECTED_HELP_ITEMS,
                helpPage.getHelpItems()
        );
    }
}
