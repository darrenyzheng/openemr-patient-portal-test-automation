package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MessagesPage extends BasePage {

    private static final int MAX_CLICK_ATTEMPTS = 10;

    private static final By SECURE_MESSAGING_HEADER =
            By.cssSelector("#main .my-3 h2");

    private static final By ACTIONS_BUTTON =
            By.cssSelector("button[data-toggle='dropdown']");

    private static final By SENT_MESSAGES =
            By.id("navSent");

    private static final By SENT_MESSAGE_COUNT =
            By.id("badgeSent");

    private static final By ALL_MESSAGE_COUNT =
            By.id("badgeAll");

    private static final By ARCHIVE_MESSAGE_COUNT =
            By.id("badgeTrash");

    private static final By MESSAGE_TEXTBOX =
            By.cssSelector("div[role='textbox']");

    private static final By COMPOSE_MESSAGE_BUTTON =
            By.cssSelector("#inboxToolbar button");

    private static final By SEND_BUTTON =
            By.id("submit");

    private static final By CANCEL_BUTTON =
            By.cssSelector(".modal-footer button[data-dismiss='modal']");

    private static final By CLOSE_BUTTON =
            By.cssSelector(".modal-header button[data-dismiss='modal']");

    private static final By SEND_TO_ARCHIVE_BUTTON =
            By.id("btnBatchDelete");

    private static final By MESSAGING_IFRAME =
            By.cssSelector("iframe[src*='messaging/messages.php']");

    private static final By MESSAGE_ROW =
            By.cssSelector("tr[role='button']");

    private static final By MESSAGE_CHECKBOX =
            By.cssSelector("input.item-checkbox");

    public MessagesPage(WebDriver driver) {
        super(driver);
    }

    public void switchToMessagingFrame() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(
                        ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                                MESSAGING_IFRAME
                        )
                );
    }

    public void clickComposeMessageButton() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                retryClick(COMPOSE_MESSAGE_BUTTON);

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                MESSAGE_TEXTBOX
                        )
                );

                return;

            } catch (TimeoutException e) {
                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }

    public void clickCancelButton() {
        retryClick(CANCEL_BUTTON);
    }

    public void clickCloseButton() {
        retryClick(CLOSE_BUTTON);
    }

    public void clickSendButton() {
        retryClick(SEND_BUTTON);
    }

    public void clickSentMessages() {
        click(SENT_MESSAGES);
    }

    public Integer getSentMessageCount() {
        return getMessageCount(SENT_MESSAGE_COUNT);
    }

    public Integer getAllMessageCount() {
        return getMessageCount(ALL_MESSAGE_COUNT);
    }

    public Integer getArchiveMessageCount() {
        return getMessageCount(ARCHIVE_MESSAGE_COUNT);
    }

    public void waitForMessageCounts(
            int expectedSent,
            int expectedAll,
            int expectedArchive
    ) {
        wait.until(driver ->
                expectedSent == getSentMessageCount()
                        && expectedAll == getAllMessageCount()
                        && expectedArchive == getArchiveMessageCount()
        );
    }

    public void waitForSentAndAllCounts(int expectedSent, int expectedAll) {
        wait.until(driver ->
                getSentMessageCount() == expectedSent
                        && getAllMessageCount() == expectedAll
        );
    }

    public void writeTestMessage() {
        WebElement textBox = wait.until(
                ExpectedConditions.elementToBeClickable(MESSAGE_TEXTBOX)
        );

        textBox.click();
        textBox.sendKeys("This is a test message");
    }

    public boolean isMessagingHeaderDisplayed() {
        return !driver.findElements(SECURE_MESSAGING_HEADER).isEmpty()
                && driver.findElement(SECURE_MESSAGING_HEADER).isDisplayed();
    }

    public void selectTestMessage() {
        List<WebElement> rows = driver.findElements(MESSAGE_ROW);

        for (WebElement row : rows) {
            if (row.getText().contains("This is a test message")) {
                row.findElement(MESSAGE_CHECKBOX).click();
                return;
            }
        }
    }

    public void moveMessageToArchive() {
        click(ACTIONS_BUTTON);
        click(SEND_TO_ARCHIVE_BUTTON);
        acceptAlert();
    }

    private Integer getMessageCount(By locator) {
        return wait.until(driver -> {
            try {
                String text = driver.findElement(locator)
                        .getText()
                        .trim();

                if (text.matches("\\d+")) {
                    return Integer.parseInt(text);
                }

                return null;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    private void retryClick(By locator) {
        for (int attempt = 1; attempt <= MAX_CLICK_ATTEMPTS; attempt++) {
            try {
                click(locator);
                return;
            } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                if (attempt == MAX_CLICK_ATTEMPTS) {
                    throw e;
                }
            }
        }
    }

    private void acceptAlert() {
        Alert alert = wait.until(
                ExpectedConditions.alertIsPresent()
        );

        alert.accept();
    }
}