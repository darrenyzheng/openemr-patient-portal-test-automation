package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HelpPage extends BasePage {

    private static final By HELP_ITEMS =
            By.cssSelector("h5.mb-1");

    public static final List<String> EXPECTED_HELP_ITEMS = List.of(
            "Clinical Documents",
            "Appointments",
            "Secure Messaging",
            "Health Snapshot",
            "Profile",
            "Billing Summary",
            "Medical Reports",
            "Settings"
    );

    public HelpPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getHelpItems() {
        return driver.findElements(HELP_ITEMS)
                .stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .toList();
    }

    public boolean hasExpectedHelpItemCount() {
        return getHelpItems().size() == EXPECTED_HELP_ITEMS.size();
    }
}