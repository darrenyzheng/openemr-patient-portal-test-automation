package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ListsPage extends BasePage {

    private static final By IMMUNIZATION_RECORD =
            By.cssSelector(".card-body .border");

    private static final By MEDICATIONS_LIST =
            By.id("medicationlist");

    private static final By PRESCRIPTION_LIST =
            By.id("prescriptionlist");

    private static final By ALLERGY_LIST =
            By.id("allergylist");

    private static final By PROBLEM_LIST =
            By.id("problemslist");

    private static final By LAB_RESULTS =
            By.id("labresults");

    public ListsPage(WebDriver driver) {
        super(driver);
    }

    public String getImmunizationRecord() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(IMMUNIZATION_RECORD)
        ).getText();
    }

    public String getMedicationsListText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(MEDICATIONS_LIST)
        ).getText();
    }

    public String getPrescriptionListText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(PRESCRIPTION_LIST)
        ).getText();
    }

    public String getAllergyListText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(ALLERGY_LIST)
        ).getText();
    }

    public String getProblemListText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(PROBLEM_LIST)
        ).getText();
    }

    public String getLabResultsText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(LAB_RESULTS)
        ).getText();
    }
}