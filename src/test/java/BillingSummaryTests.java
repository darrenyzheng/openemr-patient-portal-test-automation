import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.BillingSummaryPage;
import pages.DashboardPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BillingSummaryTests extends BaseTest {

    private BillingSummaryPage billingSummaryPage;

    @BeforeEach
    void setUpBillingSummaryPage() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        billingSummaryPage = new BillingSummaryPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickLedgerButton();
    }

    @Test
    @Tag("tc39")
    @Tag("smoke")
    public void selectTodayUsingDatePickers() {
        billingSummaryPage.openFromDatePicker();
        billingSummaryPage.selectToday(0);

        billingSummaryPage.openToDatePicker();
        billingSummaryPage.selectToday(1);

        billingSummaryPage.submitBillingSummary();

        assertEquals(
                billingSummaryPage.getToday(),
                billingSummaryPage.getFromDate()
        );

        assertEquals(
                billingSummaryPage.getToday(),
                billingSummaryPage.getToDate()
        );
    }

    @Test
    @Tag("tc40")
    @Tag("smoke")
    public void enterTodayUsingKeyboard() {
        billingSummaryPage.enterTodayAsFromDate();
        billingSummaryPage.enterTodayAsToDate();

        billingSummaryPage.submitBillingSummary();

        assertEquals(
                billingSummaryPage.getToday(),
                billingSummaryPage.getFromDate()
        );

        assertEquals(
                billingSummaryPage.getToday(),
                billingSummaryPage.getToDate()
        );
    }

    @Test
    @Tag("tc41")
    @Tag("regression")
    public void selectFromDateUsingPickerAndEnterToDateUsingKeyboard() {
        billingSummaryPage.openFromDatePicker();
        billingSummaryPage.selectToday(0);

        billingSummaryPage.enterTodayAsToDate();

        billingSummaryPage.submitBillingSummary();

        assertEquals(
                billingSummaryPage.getToday(),
                billingSummaryPage.getFromDate()
        );

        assertEquals(
                billingSummaryPage.getToday(),
                billingSummaryPage.getToDate()
        );
    }

    @Test
    @Tag("tc42")
    @Tag("regression")
    public void enterFromDateAfterToDate() {
        billingSummaryPage.enterFromDate("2026-02-01");
        billingSummaryPage.enterToDate("2026-01-01");

        billingSummaryPage.submitBillingSummary();
    }

    @Test
    @Tag("tc43")
    @Tag("regression")
    public void enterInvalidDateFormat() {
        billingSummaryPage.enterFromDate("06-02-1995");
        billingSummaryPage.enterToDate("06-30-2000");

        billingSummaryPage.submitBillingSummary();
    }

    @Test
    @Tag("tc44")
    @Tag("regression")
    public void enterFutureDates() {
        billingSummaryPage.enterFromDate("3030-02-01");
        billingSummaryPage.enterToDate("3032-02-01");

        billingSummaryPage.submitBillingSummary();
    }

    @Test
    @Tag("tc45")
    @Tag("regression")
    public void enterPastDates() {
        billingSummaryPage.enterFromDate("0950-01-01");
        billingSummaryPage.enterToDate("0975-01-01");

        billingSummaryPage.submitBillingSummary();
    }

    @Test
    @Tag("tc46")
    @Tag("regression")
    public void navigateToPreviousMonth() {
        billingSummaryPage.openFromDatePicker();
        billingSummaryPage.selectPreviousMonth(0);
    }

    @Test
    @Tag("tc47")
    @Tag("regression")
    public void navigateToNextMonth() {
        billingSummaryPage.openFromDatePicker();
        billingSummaryPage.selectNextMonth(0);
    }

    @Test
    @Tag("tc48")
    @Tag("regression")
    public void dateFieldsResetAfterRefresh() {
        billingSummaryPage.enterFromDate("2026-01-01");
        billingSummaryPage.enterToDate("2026-01-01");

        driver.navigate().refresh();

        assertNotEquals(
                "2026-01-01",
                billingSummaryPage.getFromDate()
        );

        assertNotEquals(
                "2026-01-01",
                billingSummaryPage.getToDate()
        );
    }
}