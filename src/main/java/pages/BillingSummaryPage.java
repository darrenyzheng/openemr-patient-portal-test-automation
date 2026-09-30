package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

public class BillingSummaryPage extends BasePage {

    private static final By BILLING_SUMMARY_HEADER =
            By.cssSelector(".card-header");

    private static final By FROM_DATE_FIELD =
            By.id("form_from_date");

    private static final By TO_DATE_FIELD =
            By.id("form_to_date");

    private static final By SUBMIT_BUTTON =
            By.cssSelector(".btn.btn-primary");

    private static final By LEDGER_IFRAME =
            By.cssSelector("iframe[src*='pat_ledger.php']");

    private static final LocalDate TODAY = LocalDate.now();

    public BillingSummaryPage(WebDriver driver) {
        super(driver);
    }

    public String getToday() {
        return TODAY.toString();
    }

    private void switchToLedger() {
        driver.switchTo().defaultContent();

        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(LEDGER_IFRAME)
        );

        driver.switchTo().frame(iframe);
    }

    public void openFromDatePicker() {
        switchToLedger();

        By datePicker = getDatePickerLocator(0);

        for (int attempt = 1; attempt <= 3; attempt++) {
            click(FROM_DATE_FIELD);

            try {
                new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        datePicker
                                )
                        );

                return;
            } catch (TimeoutException e) {
                // Retry opening the date picker.
            }
        }

        throw new TimeoutException(
                "From date picker did not become visible after 3 attempts"
        );
    }

    public void openToDatePicker() {
        switchToLedger();

        By datePicker = getDatePickerLocator(1);

        for (int attempt = 1; attempt <= 3; attempt++) {
            click(TO_DATE_FIELD);

            try {
                new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        datePicker
                                )
                        );

                return;
            } catch (TimeoutException e) {
                // Retry opening the date picker.
            }
        }

        throw new TimeoutException(
                "To date picker did not become visible after 3 attempts"
        );
    }

    public void selectToday(int datePickerIndex) {
        By todayButton = By.xpath(
                "(//div[contains(@class,'xdsoft_datetimepicker')]" +
                        "//button[contains(@class,'xdsoft_today_button')])[" +
                        (datePickerIndex + 1) + "]"
        );

        retryFind(todayButton);
        retryClick(todayButton);

        int day = TODAY.getDayOfMonth();
        int month = TODAY.getMonthValue() - 1;
        int year = TODAY.getYear();

        By todayDate = By.xpath(
                "(//div[contains(@class,'xdsoft_datetimepicker')])[" +
                        (datePickerIndex + 1) +
                        "]//td[contains(@class,'xdsoft_date')" +
                        " and @data-date='" + day + "'" +
                        " and @data-month='" + month + "'" +
                        " and @data-year='" + year + "']"
        );

        retryFind(todayDate);
        retryClick(todayDate);
    }

    public void selectPreviousMonth(int datePickerIndex) {
        By previousMonth = By.xpath(
                "(//div[contains(@class,'xdsoft_datetimepicker')]" +
                        "//button[contains(@class,'xdsoft_prev')])[" +
                        (datePickerIndex + 1) + "]"
        );

        retryFind(previousMonth);
        retryClick(previousMonth);
    }

    public void selectNextMonth(int datePickerIndex) {
        By nextMonth = By.xpath(
                "(//div[contains(@class,'xdsoft_datetimepicker')]" +
                        "//button[contains(@class,'xdsoft_next')])[" +
                        (datePickerIndex + 1) + "]"
        );

        retryFind(nextMonth);
        retryClick(nextMonth);
    }

    public void enterFromDate(String date) {
        switchToLedger();
        type(FROM_DATE_FIELD, date);
    }

    public void enterToDate(String date) {
        switchToLedger();
        type(TO_DATE_FIELD, date);
    }

    public void enterTodayAsFromDate() {
        enterFromDate(TODAY.toString());
    }

    public void enterTodayAsToDate() {
        enterToDate(TODAY.toString());
    }

    public String getFromDate() {
        switchToLedger();
        return driver.findElement(FROM_DATE_FIELD)
                .getDomProperty("value");
    }

    public String getToDate() {
        switchToLedger();
        return driver.findElement(TO_DATE_FIELD)
                .getDomProperty("value");
    }

    public void submitBillingSummary() {
        click(SUBMIT_BUTTON);
    }

    private By getDatePickerLocator(int datePickerIndex) {
        return By.xpath(
                "(//div[contains(@class,'xdsoft_datetimepicker')])[" +
                        (datePickerIndex + 1) + "]"
        );
    }

    private void retryFind(By locator) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(locator)
                );

                return;
            } catch (TimeoutException e) {
                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }

    private void retryClick(By locator) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                switchToLedger();
                click(locator);
                return;
            } catch (Exception e) {
                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }
}