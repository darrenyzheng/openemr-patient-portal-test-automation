package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AppointmentPage extends BasePage {

    public static final By PAGE_HEADERS =
            By.cssSelector("h3.text-center");

    public static final By SAVE_APPOINTMENT_BUTTON =
            By.cssSelector("input[name='form_save']");

    public static final By NEW_APPOINTMENT_LINK =
            By.cssSelector("a[onclick=\"editAppointment('add',1)\"]");

    public static final By CANCEL_APPOINTMENT_BUTTON =
            By.cssSelector("#oefooter button");

    public static final By CLOSE_APPOINTMENT_BUTTON =
            By.xpath(
                    "//h5[normalize-space()='Request New Appointment']" +
                            "/following-sibling::button[@data-dismiss='modal']"
            );

    public static final By APPOINTMENT_FORM =
            By.id("theaddform");

    public AppointmentPage(WebDriver driver) {
        super(driver);
    }

    public int getPageHeaderCount() {
        List<WebElement> headers = driver.findElements(PAGE_HEADERS);
        return headers.size();
    }

    public void openNewAppointmentForm() {
        WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofSeconds(2));

        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                click(NEW_APPOINTMENT_LINK);

                shortWait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                APPOINTMENT_FORM
                        )
                );

                return;

            } catch (TimeoutException | ElementClickInterceptedException e) {
                // Retry
            }
        }
    }

    public void saveNewAppointment() {
        click(SAVE_APPOINTMENT_BUTTON);
    }

    public boolean isNoOpeningAlertPresent() {
        try {
            Alert alert = wait.until(
                    ExpectedConditions.alertIsPresent()
            );

            return alert.getText().contains(
                    "Please click on Openings to select a time."
            );

        } catch (TimeoutException e) {
            return false;
        }
    }

    public void cancelNewAppointment() {
        click(CANCEL_APPOINTMENT_BUTTON);
    }

    public void closeNewAppointment() {
        click(CLOSE_APPOINTMENT_BUTTON);
    }
}