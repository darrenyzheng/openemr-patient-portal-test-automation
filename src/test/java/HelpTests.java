import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.HelpPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpTests extends BaseTest {

    private WebDriver driver;
    private HelpPage helpPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        helpPage = new HelpPage(driver);

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickHelpButton();
    }

    @Test
    public void verifyHelpItems() {
        assertEquals(
                HelpPage.EXPECTED_HELP_ITEMS,
                helpPage.getHelpItems()
        );
    }

    @Test
    public void verifyHelpItemsAfterRefresh() {
        helpPage.refresh();

        assertEquals(
                HelpPage.EXPECTED_HELP_ITEMS,
                helpPage.getHelpItems()
        );
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}