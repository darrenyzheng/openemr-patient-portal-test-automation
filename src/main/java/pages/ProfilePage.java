package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {

    private static final int MAX_CLICK_ATTEMPTS = 10;

    private static final By PROFILE_HEADER =
            By.cssSelector("#DEM header");

    private static final By EDIT_PROFILE_BUTTON =
            By.id("editDems");

    private static final By PROFILE_MODAL =
            By.cssSelector("div.modal.show:has(h5.modal-title)");

    private static final By CANCEL_BUTTON =
            By.cssSelector("#oefooter button[data-dismiss='modal']");

    private static final By PROFILE_MODAL_CLOSE_BUTTON =
            By.cssSelector("div.modal.show .modal-header button.close");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public void clickEditProfileButton() {
        for (int attempt = 1; attempt <= MAX_CLICK_ATTEMPTS; attempt++) {
            try {
                click(EDIT_PROFILE_BUTTON);

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                PROFILE_MODAL
                        )
                );

                return;

            } catch (TimeoutException
                     | ElementClickInterceptedException
                     | StaleElementReferenceException e) {

                if (attempt == MAX_CLICK_ATTEMPTS) {
                    throw e;
                }
            }
        }
    }

    public boolean isProfileModalDisplayed() {
        return !driver.findElements(PROFILE_MODAL).isEmpty()
                && driver.findElement(PROFILE_MODAL).isDisplayed();
    }

    public boolean isProfileHeaderDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        PROFILE_HEADER
                )
        ).isDisplayed();
    }

    public void clickCancelButton() {
        click(CANCEL_BUTTON);
    }

    public void clickProfileModalCloseButton() {
        click(PROFILE_MODAL_CLOSE_BUTTON);
    }
}