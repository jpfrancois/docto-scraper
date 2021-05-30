package docto.scraper;

import docto.scraper.domain.Eligibility;
import docto.scraper.domain.Search;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class App {
    private static final String SITE = "https://www.doctolib.fr/vaccination-covid-19";
    private static final String DRIVER = "webdriver.chrome.driver";
    private static final String DRIVER_PATH = "src/main/resources/chromedriver";

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("EEE d MMMM HH:mm")
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter().withLocale(Locale.FRANCE);

    public static void main(String[] args) {
        System.setProperty(DRIVER, DRIVER_PATH);

        var driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        var search = new Search("Paris", Eligibility.PLUS_55);

        try {
            driver.get(SITE);

            // Accept cookie terms
            driver.findElement(By.id("didomi-notice-agree-button")).click();

            // Select radio button for 18+ people
            driver.findElement(By.id(search.getEligibility().getId())).click();
            // Click search button
            driver.findElements(By.className("dl-button-primary")).stream()
                    .filter(WebElement::isEnabled)
                    .filter(e -> e.getTagName().equalsIgnoreCase("button"))
                    .findFirst()
                    .ifPresent(WebElement::click);

            WebElement placeInput = driver.findElement(By.className("searchbar-place-input"));
            placeInput.clear();
            placeInput.sendKeys(search.getCity());
            driver.findElements(By.className("searchbar-result")).stream()
                    .filter(e -> !e.getAttribute("id").equals("-"))
                    .findFirst()
                    .ifPresent(WebElement::click);
            driver.findElement(By.className("searchbar-submit-button")).click();

            // Select radio button for 18+ people
            driver.findElement(By.id(search.getEligibility().getId())).click();
            // Click search button
            driver.findElements(By.className("dl-button-primary")).stream()
                    .filter(WebElement::isEnabled)
                    .filter(e -> e.getTagName().equalsIgnoreCase("button"))
                    .findFirst()
                    .ifPresent(WebElement::click);

            // Scroll down to make all slots appear
            driver.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            waitForLoading();

            System.out.println("Available slots:");
            List<WebElement> availableSlots = driver.findElements(By.className("availabilities-slot"));
            availableSlots.stream()
                    .map(date -> date.getAttribute("title"))
                    .map(text -> LocalDateTime.parse(text, formatter))
                    .forEach(System.out::println);

            System.out.println("Available slots until tomorrow night:");
            availableSlots.stream()
                    .map(date -> date.getAttribute("title"))
                    .map(text -> LocalDateTime.parse(text, formatter))
                    .filter(date -> date.isBefore(LocalDate.now().atStartOfDay().plusDays(2)))
                    .sorted()
                    .forEach(System.out::println);
        } finally {
            driver.quit();
        }
    }

    private static void waitForLoading() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
