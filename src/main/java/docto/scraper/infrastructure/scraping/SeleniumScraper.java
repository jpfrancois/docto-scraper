package docto.scraper.infrastructure.scraping;

import docto.scraper.domain.Presentation;
import docto.scraper.domain.SearchResult;
import docto.scraper.infrastructure.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SeleniumScraper implements Scraper {
    private static final String BUTTON = "button";
    private static final String ID = "id";
    private static final String NON_RESULT = "-";
    private static final long WAIT_INTERVAL_MILLIS = 1000;
    private static final int SCROLLS = 8;

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("EEE d MMMM HH:mm")
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter().withLocale(Locale.FRANCE);

    private final RemoteWebDriver driver;

    public SeleniumScraper(Config config) {
        driver = config.webDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Override
    public void goTo(String url) {
        driver.get(url);
    }

    @Override
    public void stop() {
        driver.quit();
    }

    @Override
    public void clickElement(String id) {
        driver.findElement(By.id(id)).click();
    }

    @Override
    public void clickButton(String className) {
        driver.findElements(By.className(className)).stream()
                .filter(WebElement::isEnabled)
                .filter(e -> e.getTagName().equalsIgnoreCase(BUTTON))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    @Override
    public void typeSearch(String className, String search) {
        WebElement placeInput = driver.findElement(By.className(className));
        placeInput.clear();
        placeInput.sendKeys(search);
    }

    @Override
    public void selectFirstResult(String resultClassName, String submitClassName) {
        driver.findElements(By.className(resultClassName)).stream()
                .filter(e -> !e.getAttribute(ID).equals(NON_RESULT))
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElement(By.className(submitClassName)).click();
    }

    @Override
    public void scrollToBottom() {
        // Result loading triggering works better when slowly scrolling down
        for (int i = 0; i < SCROLLS; i++) {
            driver.executeScript("window.scrollBy(0,500)");
            waitForLoading();
        }
    }

    private static void waitForLoading() {
        try {
            Thread.sleep(WAIT_INTERVAL_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SearchResult> findResults() {
        SearchResult searchResult = SearchResult.builder()
                .id("someId")
                .presentation(Presentation.builder().build())
                .timeSlots(new ArrayList<>())
                .build();
        driver.findElements(By.className("availabilities-slot")).stream()
                .map(date -> date.getAttribute("title"))
                .map(text -> LocalDateTime.parse(text, formatter))
                .forEach(date -> searchResult.getTimeSlots().add(date));
        return Collections.singletonList(searchResult);
    }
}
