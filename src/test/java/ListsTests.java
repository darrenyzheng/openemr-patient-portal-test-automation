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
    @Tag("tc39")
    public void verifyImmunizationRecordIsEmpty() {
        assertEquals(
                "No records found.",
                listsPage.getImmunizationRecord().trim()
        );
    }

    @Test
    @Tag("smoke")
    @Tag("tc40")
    public void verifyMedicationListContainsExpectedMedications() {
        String medications = listsPage.getMedicationsListText();

        assertTrue(medications.contains("Norvasc"));
        assertTrue(medications.contains("Lisinopril"));
    }

    @Test
    @Tag("regression")
    @Tag("tc41")
    public void verifyPrescriptionListIsEmpty() {
        assertEquals(
                "No Results",
                listsPage.getPrescriptionListText().trim()
        );
    }

    @Test
    @Tag("smoke")
    @Tag("tc42")
    public void verifyAllergyListContainsPenicillin() {
        assertTrue(
                listsPage.getAllergyListText().contains("penicillin")
        );
    }

    @Test
    @Tag("regression")
    @Tag("tc43")
    public void verifyProblemListContainsExpectedConditions() {
        String problemList = listsPage.getProblemListText();

        assertTrue(problemList.contains("HTN"));
        assertTrue(problemList.contains("Chronic Renal Insuficiency"));
    }

    @Test
    @Tag("regression")
    @Tag("tc44")
    public void verifyLabResultsAreEmpty() {
        assertEquals(
                "No Results",
                listsPage.getLabResultsText().trim()
        );
    }
}
