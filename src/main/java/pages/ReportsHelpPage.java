package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ReportsHelpPage extends BasePage {

    private static final By HELP_ITEM_TITLES =
            By.cssSelector("h5.mb-1");

    public static final List<String> EXPECTED_HELP_ITEM_TITLES = List.of(
            "View Summary of Care",
            "Download Summary of Care",
            "Customized Medical History Report",
            "Download Medical Record Documents"
    );

    public ReportsHelpPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getHelpItemTitles() {
        return driver.findElements(HELP_ITEM_TITLES)
                .stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .toList();
    }

    public boolean hasExpectedHelpItemCount() {
        return getHelpItemTitles().size() == EXPECTED_HELP_ITEM_TITLES.size();
    }
}