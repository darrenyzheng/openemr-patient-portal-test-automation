import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ReportsHelpPage;
import pages.ReportsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportsHelpTests extends BaseTest {

    private WebDriver driver;
    private ReportsPage reportsPage;
    private ReportsHelpPage reportsHelpPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

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
    public void testHelpItemTitles() {
        assertEquals(
                ReportsHelpPage.EXPECTED_HELP_ITEM_TITLES,
                reportsHelpPage.getHelpItemTitles()
        );
    }

    @Test
    public void testHelpItemsAfterRefresh() {
        reportsPage.refresh();

        assertEquals(
                ReportsHelpPage.EXPECTED_HELP_ITEM_TITLES,
                reportsHelpPage.getHelpItemTitles()
        );
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}