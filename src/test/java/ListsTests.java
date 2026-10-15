import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import pages.DashboardPage;
import pages.ListsPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListsTests extends BaseTest {

    private ListsPage listsPage;

    @BeforeEach
    public void setUpListsPage() {
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = new DashboardPage(driver);
        listsPage = new ListsPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickListsButton();
    }

    @Test
    @Tag("regression")
    @Tag("tc53")
    public void verifyImmunizationRecordIsEmpty() {
        assertEquals(
                "No records found.",
                listsPage.getImmunizationRecord().trim()
        );
    }

    @Test
    @Tag("smoke")
    @Tag("tc54")
    public void verifyMedicationListContainsExpectedMedications() {
        String medications = listsPage.getMedicationsListText();

        assertTrue(medications.contains("Norvasc"));
        assertTrue(medications.contains("Lisinopril"));
    }

    @Test
    @Tag("regression")
    @Tag("tc55")
    public void verifyPrescriptionListIsEmpty() {
        assertEquals(
                "No Results",
                listsPage.getPrescriptionListText().trim()
        );
    }

    @Test
    @Tag("smoke")
    @Tag("tc56")
    public void verifyAllergyListContainsPenicillin() {
        assertTrue(
                listsPage.getAllergyListText().contains("penicillin")
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc57")
    public void verifyProblemListContainsExpectedConditions() {
        String problemList = listsPage.getProblemListText();

        assertTrue(problemList.contains("HTN"));
        assertTrue(problemList.contains("Chronic Renal Insuficiency"));
    }

    @Test
    @Tag("regression")
    @Tag("tc58")
    public void verifyLabResultsAreEmpty() {
        assertEquals(
                "No Results",
                listsPage.getLabResultsText().trim()
        );
    }
}
