package docto.scraper;

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

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("EEE d MMMM HH:mm")
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter()
            .withLocale(Locale.FRANCE);

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/home/pablo/dev/chromedriver");

        var driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        var eligibility = Eligibility.PLUS55;

        try {
            driver.get("https://www.doctolib.fr/vaccination-covid-19");

            // Accept cookie terms
            driver.findElement(By.id("didomi-notice-agree-button")).click();

            // Select radio button for 18+ people
            driver.findElement(By.id(eligibility.getId())).click();
            // Click search button
            driver.findElements(By.className("dl-button-primary")).stream()
                    .filter(WebElement::isEnabled)
                    .filter(e -> e.getTagName().equalsIgnoreCase("button"))
                    .findFirst()
                    .ifPresent(WebElement::click);

            WebElement placeInput = driver.findElement(By.className("searchbar-place-input"));
            placeInput.clear();
            placeInput.sendKeys("Paris");
            driver.findElements(By.className("searchbar-result")).stream()
                    .filter(e -> !e.getAttribute("id").equals("-"))
                    .findFirst()
                    .ifPresent(WebElement::click);
            driver.findElement(By.className("searchbar-submit-button")).click();

            // Select radio button for 18+ people
            driver.findElement(By.id(eligibility.getId())).click();
            // Click search button
            driver.findElements(By.className("dl-button-primary")).stream()
                    .filter(WebElement::isEnabled)
                    .filter(e -> e.getTagName().equalsIgnoreCase("button"))
                    .findFirst()
                    .ifPresent(WebElement::click);

            for (int i = 0; i < 1; i++) {
                // Scroll down to make all slots appear
//                driver.executeScript("window.scrollBy(0,500)");
                driver.executeScript("window.scrollTo(0, document.body.scrollHeight)");

                try {
//                    Thread.sleep(500);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Available slots:");
            List<WebElement> availableSlots = driver.findElements(By.className("availabilities-slot"));
            availableSlots.stream()
                    .map(date -> date.getAttribute("title"))
                    .map(text -> LocalDateTime.parse(text, formatter))
                    .forEach(slot -> System.out.println(slot));

            System.out.println("Available slots until tomorrow night:");
            availableSlots.stream()
                    .map(date -> date.getAttribute("title"))
                    .map(text -> LocalDateTime.parse(text, formatter))
                    .filter(date -> date.isBefore(LocalDate.now().atStartOfDay().plusDays(2)))
                    .sorted()
                    .forEach(slot -> System.out.println(slot));
        } finally {
            driver.quit();
        }
    }
}
