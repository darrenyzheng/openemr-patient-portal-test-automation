import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import pages.DashboardPage;
import pages.DocumentsPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentsTests extends BaseTest {

    private DocumentsPage documentsPage;

    @BeforeEach
    public void setUpDocumentsPage() {
        LoginPage loginPage = new LoginPage(driver);
        documentsPage = new DocumentsPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickDocumentsButton();
    }

    @Test
    @Tag("smoke")
    @Tag("tc49")
    public void verifyDocumentsPageRefreshButton() {
        assertTrue(documentsPage.isDocumentsHeaderVisible());

        documentsPage.refreshDocumentsPage();

        assertTrue(documentsPage.isDocumentsHeaderVisible());
    }

    @Test
    @Tag("regression")
    @Tag("tc50")
    public void verifyDocumentsPageBrowserRefresh() {
        assertTrue(documentsPage.isDocumentsHeaderVisible());

        documentsPage.refresh();

        assertTrue(documentsPage.isDocumentsHeaderVisible());
    }
}
