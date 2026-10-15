import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import pages.AppointmentPage;
import pages.DashboardPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppointmentTests extends BaseTest {

    private AppointmentPage appointmentPage;

    @BeforeEach
    public void setUpAppointment() {
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = new DashboardPage(driver);
        appointmentPage = new AppointmentPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickAppointmentsButton();
    }

    @Test
    @Tag("smoke")
    @Tag("tc20")
    public void refreshAppointmentsPage() {
        assertEquals(2, appointmentPage.getPageHeaderCount());

        appointmentPage.refresh();

        assertEquals(2, appointmentPage.getPageHeaderCount());
    }

    @Test
    @Tag("regression")
    @Tag("tc21")
    public void cancelNewAppointment() {
        appointmentPage.openNewAppointmentForm();
        appointmentPage.cancelNewAppointment();

        assertEquals(2, appointmentPage.getPageHeaderCount());
    }

    @Test
    @Tag("regression")
    @Tag("tc22")
    public void closeNewAppointment() {
        appointmentPage.openNewAppointmentForm();
        appointmentPage.closeNewAppointment();

        assertEquals(2, appointmentPage.getPageHeaderCount());
    }

    @Test
    @Tag("regression")
    @Tag("tc23")
    public void saveNewAppointmentWithoutTimeShowsAlert() {
        appointmentPage.openNewAppointmentForm();
        appointmentPage.saveNewAppointment();

        assertTrue(appointmentPage.isNoOpeningAlertPresent());
    }
}