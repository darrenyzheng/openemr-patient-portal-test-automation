package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DocumentsPage extends BasePage {

    private static final By DOCUMENTS_HEADER =
            By.cssSelector("div.text-center > span.h3");

    private static final By REFRESH_PAGE =
            By.id("refreshPage");

    public DocumentsPage(WebDriver driver) {
        super(driver);
    }

    public void refreshDocumentsPage() {
        click(REFRESH_PAGE);
    }

    public boolean isDocumentsHeaderVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        DOCUMENTS_HEADER
                )
        ).isDisplayed();
    }
}