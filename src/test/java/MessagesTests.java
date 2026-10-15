import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import pages.DashboardPage;
import pages.LoginPage;
import pages.MessagesPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesTests extends BaseTest {

    private MessagesPage messagesPage;

    @BeforeEach
    void setUpMessagesPage() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        messagesPage = new MessagesPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login(USERNAME, PASSWORD, EMAIL);
        dashboardPage.clickMessagesButton();
    }

    @Test
    @Tag("regression")
    @Tag("tc15")
    void testCancelClosesComposeMessage() {
        messagesPage.switchToMessagingFrame();
        messagesPage.clickComposeMessageButton();
        messagesPage.clickCancelButton();

        assertTrue(messagesPage.isMessagingHeaderDisplayed());
    }

    @Test
    @Tag("regression")
    @Tag("tc16")
    void testCloseButtonClosesComposeMessage() {
        messagesPage.switchToMessagingFrame();
        messagesPage.clickComposeMessageButton();
        messagesPage.clickCloseButton();

        assertTrue(messagesPage.isMessagingHeaderDisplayed());
    }

    @Test
    @Tag("regression")
    @Tag("tc17")
    void testSendingEmptyMessageUpdatesCounts() {
        messagesPage.switchToMessagingFrame();

        int beforeSent = messagesPage.getSentMessageCount();
        int beforeAll = messagesPage.getAllMessageCount();

        messagesPage.clickComposeMessageButton();
        messagesPage.clickSendButton();

        messagesPage.waitForSentAndAllCounts(
                beforeSent + 1,
                beforeAll + 1
        );

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
    @Tag("smoke")
    @Tag("tc18")
    void testSendingMessageUpdatesCounts() {
        messagesPage.switchToMessagingFrame();

        int beforeSent = messagesPage.getSentMessageCount();
        int beforeAll = messagesPage.getAllMessageCount();

        messagesPage.clickComposeMessageButton();
        messagesPage.writeTestMessage();
        messagesPage.clickSendButton();


        messagesPage.waitForSentAndAllCounts(
                beforeSent + 1,
                beforeAll + 1
        );
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
    @Tag("regression")
    @Tag("tc19")
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
}
