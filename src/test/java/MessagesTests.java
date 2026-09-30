import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.LoginPage;
import pages.MessagesPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesTests extends BaseTest {

    private WebDriver driver;
    private MessagesPage messagesPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);

        dashboardPage.clickMessagesButton();
        messagesPage = new MessagesPage(driver);
    }

    @Test
    void testCancelClosesComposeMessage() {
        messagesPage.switchToMessagingFrame();
        messagesPage.clickComposeMessageButton();
        messagesPage.clickCancelButton();

        assertTrue(messagesPage.isMessagingHeaderDisplayed());
    }

    @Test
    void testCloseButtonClosesComposeMessage() {
        messagesPage.switchToMessagingFrame();
        messagesPage.clickComposeMessageButton();
        messagesPage.clickCloseButton();

        assertTrue(messagesPage.isMessagingHeaderDisplayed());
    }

    @Test
    void testSendingEmptyMessageUpdatesCounts() {
        messagesPage.switchToMessagingFrame();

        int beforeSent = messagesPage.getSentMessageCount();
        int beforeAll = messagesPage.getAllMessageCount();

        messagesPage.clickComposeMessageButton();
        messagesPage.clickSendButton();

        assertEquals(
                beforeSent + 1,
                messagesPage.getSentMessageCount()
        );

        assertEquals(
                beforeAll + 1,
                messagesPage.getAllMessageCount()
        );
    }

    @Test
    void testSendingMessageUpdatesCounts() {
        messagesPage.switchToMessagingFrame();

        int beforeSent = messagesPage.getSentMessageCount();
        int beforeAll = messagesPage.getAllMessageCount();

        messagesPage.clickComposeMessageButton();
        messagesPage.writeTestMessage();
        messagesPage.clickSendButton();

        assertEquals(
                beforeSent + 1,
                messagesPage.getSentMessageCount()
        );

        assertEquals(
                beforeAll + 1,
                messagesPage.getAllMessageCount()
        );
    }

    @Test
    void testSendingMessageToArchive() {
        messagesPage.switchToMessagingFrame();

        int beforeSent = messagesPage.getSentMessageCount();
        int beforeAll = messagesPage.getAllMessageCount();
        int beforeArchive = messagesPage.getArchiveMessageCount();

        messagesPage.clickComposeMessageButton();
        messagesPage.writeTestMessage();
        messagesPage.clickSendButton();
        messagesPage.clickSentMessages();
        messagesPage.selectTestMessage();
        messagesPage.moveMessageToArchive();

        messagesPage.waitForMessageCounts(
                beforeSent,
                beforeAll,
                beforeArchive + 1
        );

        assertEquals(
                beforeSent,
                messagesPage.getSentMessageCount()
        );

        assertEquals(
                beforeAll,
                messagesPage.getAllMessageCount()
        );

        assertEquals(
                beforeArchive + 1,
                messagesPage.getArchiveMessageCount()
        );
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}