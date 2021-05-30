package docto.scraper.infrastructure.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromeConfig implements Config {
    private static final String DRIVER = "webdriver.chrome.driver";
    private static final String DRIVER_PATH = "src/main/resources/chromedriver";

    private final RemoteWebDriver webDriver;

    public ChromeConfig() {
        System.setProperty(DRIVER, DRIVER_PATH);
        webDriver = new ChromeDriver();
    }

    public RemoteWebDriver webDriver() {
        return webDriver;
    }
}
