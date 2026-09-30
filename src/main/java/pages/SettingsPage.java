package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SettingsPage extends BasePage {

    public static final By SETTINGS_HEADER =
            By.cssSelector("#settings-card header.card-header");

    public static final By DIGITAL_SIGNATURE =
            By.cssSelector("a[data-type='patient-signature']");

    public static final By MANAGE_LOGIN_CREDENTIALS =
            By.cssSelector("h4[onclick='changeCredentials(event)']");

    public static final By THEME_SETTINGS =
            By.cssSelector("h4[data-target='#settings-content']");

    public static final By SAVE_SIGNATURE =
            By.cssSelector("button[data-action='save_signature']");

    public static final By CANCEL_SIGNATURE =
            By.cssSelector("#openSignModal button[data-dismiss='modal']");

    public static final By APPLY_THEME =
            By.cssSelector("a[href='./home.php']");

    public static final By SIGNATURE_MODAL =
            By.id("openSignModal");

    public static final By LOGIN_CREDENTIALS_MODAL =
            By.cssSelector("div.modal.dialogModal.show[role='dialog']");

    public static final By SIGNER_ALERT_BOX =
            By.id("signerAlertBox");

    public static final By SIGNATURE_ALERT_MESSAGE =
            By.cssSelector("#alertMessage p");

    public static final By USERNAME_FIELD =
            By.id("login_uname");

    public static final By CONFIRM_USERNAME_FIELD =
            By.id("confirm_uname");

    public static final By CURRENT_PASSWORD_FIELD =
            By.id("pass_current");

    public static final By SUBMIT_CREDENTIALS_BUTTON =
            By.name("submit");

    public static final By INVALID_CREDENTIALS_MESSAGE =
            By.cssSelector(".alert.alert-danger");

    private static final int MAX_ATTEMPTS = 3;

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void openSignatureSettings() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                click(DIGITAL_SIGNATURE);

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                SIGNATURE_MODAL
                        )
                );

                return;

            } catch (StaleElementReferenceException e) {
                if (attempt == MAX_ATTEMPTS) {
                    throw e;
                }
            }
        }
    }

    public void openLoginCredentials() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                click(MANAGE_LOGIN_CREDENTIALS);

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                LOGIN_CREDENTIALS_MODAL
                        )
                );

                driver.switchTo().frame("modalframe");

                return;

            } catch (StaleElementReferenceException e) {
                if (attempt == MAX_ATTEMPTS) {
                    throw e;
                }
            }
        }
    }

    public void openThemeSettings() {
        click(THEME_SETTINGS);
    }

    public void saveSignature() {
        click(SAVE_SIGNATURE);
    }

    public void cancelSignature() {
        click(CANCEL_SIGNATURE);
    }

    public void applyTheme() {
        click(APPLY_THEME);
    }

    public void submitCredentialChanges() {
        click(SUBMIT_CREDENTIALS_BUTTON);
    }

    public boolean isSignatureAlertDisplayed() {
        return !driver.findElements(SIGNER_ALERT_BOX).isEmpty();
    }

    public boolean isSettingsHeaderVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        SETTINGS_HEADER
                )
        ).isDisplayed();
    }

    public String getSignatureAlertMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        SIGNATURE_ALERT_MESSAGE
                )
        ).getText();
    }

    public String getInvalidCredentialsAlertMessage() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                INVALID_CREDENTIALS_MESSAGE
                        )
                );

                return driver.findElement(INVALID_CREDENTIALS_MESSAGE)
                        .getText()
                        .trim();

            } catch (StaleElementReferenceException e) {
                if (attempt == MAX_ATTEMPTS) {
                    throw e;
                }
            }
        }

        return "";
    }

    public void enterNewUsername() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                wait.until(
                        ExpectedConditions.refreshed(
                                ExpectedConditions.visibilityOfElementLocated(
                                        USERNAME_FIELD
                                )
                        )
                ).clear();

                wait.until(
                        ExpectedConditions.refreshed(
                                ExpectedConditions.visibilityOfElementLocated(
                                        USERNAME_FIELD
                                )
                        )
                ).sendKeys("PhilPhil1");

                wait.until(
                        ExpectedConditions.refreshed(
                                ExpectedConditions.visibilityOfElementLocated(
                                        CONFIRM_USERNAME_FIELD
                                )
                        )
                ).sendKeys("PhilPhil1");

                return;

            } catch (StaleElementReferenceException e) {
                if (attempt == MAX_ATTEMPTS) {
                    throw e;
                }
            }
        }
    }

    public void enterIncorrectCurrentPassword() {
        click(CURRENT_PASSWORD_FIELD);

        driver.findElement(CURRENT_PASSWORD_FIELD)
                .sendKeys("INCORRECT_PASSWORD");
    }
}