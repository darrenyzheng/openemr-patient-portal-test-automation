import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.ListsPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListsTests extends BaseTest {

    private WebDriver driver;
    private ListsPage listsPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        listsPage = new ListsPage(driver);

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickListsButton();
    }

    @Test
    public void verifyImmunizationRecordIsEmpty() {
        assertEquals(
                "No records found.",
                listsPage.getImmunizationRecord().trim()
        );
    }

    @Test
    public void verifyMedicationListContainsExpectedMedications() {
        String medications = listsPage.getMedicationsListText();

        assertTrue(medications.contains("Norvasc"));
        assertTrue(medications.contains("Lisinopril"));
    }

    @Test
    public void verifyPrescriptionListIsEmpty() {
        assertEquals(
                "No Results",
                listsPage.getPrescriptionListText().trim()
        );
    }

    @Test
    public void verifyAllergyListContainsPenicillin() {
        assertTrue(
                listsPage.getAllergyListText().contains("penicillin")
        );
    }

    @Test
    public void verifyProblemListContainsExpectedConditions() {
        String problemList = listsPage.getProblemListText();

        assertTrue(problemList.contains("HTN"));
        assertTrue(problemList.contains("Chronic Renal Insuficiency"));
    }

    @Test
    public void verifyLabResultsAreEmpty() {
        assertEquals(
                "No Results",
                listsPage.getLabResultsText().trim()
        );
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}