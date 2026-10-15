import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ReportsHelpPage;
import pages.ReportsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportsHelpTests extends BaseTest {

    private ReportsPage reportsPage;
    private ReportsHelpPage reportsHelpPage;

    @BeforeEach
    void setUpReportsHelpPage() {
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = new DashboardPage(driver);
        reportsPage = new ReportsPage(driver);
        reportsHelpPage = new ReportsHelpPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickReportsButton();
        reportsPage.openHelp();
    }

    @Test
    @Tag("smoke")
    @Tag("tc27")
    public void testHelpItemTitles() {
        assertEquals(
                ReportsHelpPage.EXPECTED_HELP_ITEM_TITLES,
                reportsHelpPage.getHelpItemTitles()
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc28")
    public void testHelpItemsAfterRefresh() {
        reportsPage.refresh();

        assertEquals(
                ReportsHelpPage.EXPECTED_HELP_ITEM_TITLES,
                reportsHelpPage.getHelpItemTitles()
        );
    }
}
