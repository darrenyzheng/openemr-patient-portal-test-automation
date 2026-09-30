import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.AppointmentPage;
import pages.DashboardPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppointmentTests extends BaseTest {

    private WebDriver driver;
    private AppointmentPage appointmentPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        appointmentPage = new AppointmentPage(driver);

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickAppointmentsButton();
    }

    @Test
    public void refreshAppointmentsPage() {
        assertEquals(2, appointmentPage.getPageHeaderCount());

        appointmentPage.refresh();

        assertEquals(2, appointmentPage.getPageHeaderCount());
    }

    @Test
    public void cancelNewAppointment() {
        appointmentPage.openNewAppointmentForm();
        appointmentPage.cancelNewAppointment();

        assertEquals(2, appointmentPage.getPageHeaderCount());
    }

    @Test
    public void closeNewAppointment() {
        appointmentPage.openNewAppointmentForm();
        appointmentPage.closeNewAppointment();

        assertEquals(2, appointmentPage.getPageHeaderCount());
    }

    @Test
    public void saveNewAppointmentWithoutTimeShowsAlert() {
        appointmentPage.openNewAppointmentForm();
        appointmentPage.saveNewAppointment();

        assertTrue(appointmentPage.isNoOpeningAlertPresent());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}