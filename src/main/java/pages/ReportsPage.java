package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ReportsPage extends BasePage {

    public static final By VIEW_SUMMARY_OF_CARE =
            By.id("view-summary-go");

    public static final By DOWNLOAD_SUMMARY_OF_CARE =
            By.id("download-summary-go");

    public static final By CREATE_REPORT =
            By.id("create-report-go");

    public static final By DOWNLOAD_DOCUMENTS =
            By.id("download-documents-go");

    public static final By HELP =
            By.id("help-reports-list-go");

    public ReportsPage(WebDriver driver) {
        super(driver);
    }

    public void openSummaryOfCare() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                click(VIEW_SUMMARY_OF_CARE);

                wait.until(
                        ExpectedConditions.urlContains(
                                "/openemr/ccdaservice/ccda_gateway.php?action=view&csrf_token_form="
                        )
                );

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector("h1")
                        )
                );

                return;

            } catch (TimeoutException
                     | ElementClickInterceptedException
                     | StaleElementReferenceException e) {

                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }

    public void downloadSummaryOfCare() {
        click(DOWNLOAD_SUMMARY_OF_CARE);
    }



    public void downloadDocuments() {
        click(DOWNLOAD_DOCUMENTS);
    }

    public void openHelp() {
        click(HELP);
    }
}