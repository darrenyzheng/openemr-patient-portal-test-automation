import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ReportsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportsTests extends BaseTest {

    private WebDriver driver;
    private ReportsPage reportsPage;

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

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickReportsButton();
    }

    @Test
    public void testOpenSummaryOfCare() {
        reportsPage.openSummaryOfCare();

        assertTrue(
                driver.getCurrentUrl().contains(
                        "/openemr/ccdaservice/ccda_gateway.php?action=view&csrf_token_form="
                )
        );

        assertEquals(
                "SUMMARIZATION OF EPISODE NOTE",
                driver.findElement(By.cssSelector("h1")).getText()
        );
    }

    @Test
    public void testDownloadSummaryOfCare() {
        reportsPage.downloadSummaryOfCare();
    }

    @Test
    public void testDownloadDocuments() {
        reportsPage.downloadDocuments();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}