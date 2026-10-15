import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {

    protected static final String BASE_URL = System.getenv("BASE_URL");
    protected static final String USERNAME = System.getenv("USERNAME");
    protected static final String PASSWORD = System.getenv("PASSWORD");
    protected static final String EMAIL = System.getenv("EMAIL");


    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        options.setAcceptInsecureCerts(true);

        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);

        if (!Boolean.getBoolean("headless")) {
            driver.manage().window().maximize();
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}