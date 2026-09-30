import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.DocumentsPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentsTests extends BaseTest {

    private WebDriver driver;
    private DocumentsPage documentsPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        documentsPage = new DocumentsPage(driver);

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickDocumentsButton();
    }

    @Test
    public void verifyDocumentsPageRefreshButton() {
        assertTrue(documentsPage.isDocumentsHeaderVisible());

        documentsPage.refreshDocumentsPage();

        assertTrue(documentsPage.isDocumentsHeaderVisible());
    }

    @Test
    public void verifyDocumentsPageBrowserRefresh() {
        assertTrue(documentsPage.isDocumentsHeaderVisible());

        documentsPage.refresh();

        assertTrue(documentsPage.isDocumentsHeaderVisible());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}