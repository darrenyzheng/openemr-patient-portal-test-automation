import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ReportsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportsTests extends BaseTest {

    private ReportsPage reportsPage;

    @BeforeEach
    void setUpReportsPage() {
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = new DashboardPage(driver);
        reportsPage = new ReportsPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickReportsButton();
    }

    @Test
    @Tag("smoke")
    @Tag("tc29")
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
    @Tag("regression")
    @Tag("tc30")
    public void testDownloadSummaryOfCare() {
        reportsPage.downloadSummaryOfCare();
    }

    @Test
    @Tag("regression")
    @Tag("tc31")
    public void testDownloadDocuments() {
        reportsPage.downloadDocuments();
    }
}
