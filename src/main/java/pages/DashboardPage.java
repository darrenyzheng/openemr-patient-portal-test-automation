package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {

    public static final String DASHBOARD_URL =
            "https://demo.openemr.io/openemr/portal/home.php";

    public static final By DASHBOARD_BTN =
            By.id("quickstart_dashboard");

    public static final By DOCUMENTS_BTN =
            By.id("documents-go");

    public static final By APPOINTMENTS_BTN =
            By.id("appointments-go");

    public static final By MESSAGES_BTN =
            By.id("messages-go");

    public static final By LISTS_BTN =
            By.id("lists-go");

    public static final By PROFILE_BTN =
            By.id("profile-go");

    public static final By LEDGER_BTN =
            By.id("ledger-go");

    public static final By REPORTS_BTN =
            By.id("reports-go");

    public static final By SETTINGS_BTN =
            By.id("settings-go");

    public static final By HELP_BTN =
            By.id("help-go");

    public static final By LOGOUT_BTN =
            By.id("logout-go");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoggedIn() {
        WebElement dashboardButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(DASHBOARD_BTN)
        );

        return dashboardButton.isDisplayed();
    }

    public void clickHelpButton() {
        retryClick(HELP_BTN);
    }

    public void clickLedgerButton() {
        retryClick(LEDGER_BTN);
    }

    public void clickListsButton() {
        retryClick(LISTS_BTN);
    }

    public void clickReportsButton() {
        retryClick(REPORTS_BTN);
    }

    public void clickSettingsButton() {
        retryClick(SETTINGS_BTN);
    }

    public void clickProfileButton() {
        retryClick(PROFILE_BTN);
    }

    public void clickDocumentsButton() {
        retryClick(DOCUMENTS_BTN);
    }

    public void clickAppointmentsButton() {
        retryClick(APPOINTMENTS_BTN);
    }

    public void clickMessagesButton() {
        retryClick(MESSAGES_BTN);
    }

    private void retryClick(By locator) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                click(locator);
                return;

            } catch (TimeoutException |
                     ElementClickInterceptedException |
                     StaleElementReferenceException e) {

                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }

    public void waitForDashboard() {
        wait.until(driver ->
                driver.getCurrentUrl().endsWith(DASHBOARD_URL)
        );
    }

    public void logout() {
        String currentUrl = driver.getCurrentUrl();

        click(LOGOUT_BTN);

        wait.until(
                ExpectedConditions.not(
                        ExpectedConditions.urlToBe(currentUrl)
                )
        );
    }
}
