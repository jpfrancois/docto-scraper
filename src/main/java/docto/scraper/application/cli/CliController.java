package docto.scraper.application.cli;

import docto.scraper.domain.Search;
import docto.scraper.domain.SearchResult;
import docto.scraper.domain.service.DomainScrapingService;
import docto.scraper.infrastructure.config.ChromeConfig;
import docto.scraper.infrastructure.scraping.Scraper;
import docto.scraper.infrastructure.scraping.SeleniumScraper;

import java.time.LocalDate;
import java.util.Collection;

public class CliController {
    private static final String SITE = "https://www.doctolib.fr/vaccination-covid-19";

    private final Scraper scraper = new SeleniumScraper(new ChromeConfig());
    private final DomainScrapingService scrapingService = new DomainScrapingService(scraper);

    public void search(Search search) {
        try {
            scrapingService.goTo(SITE);

            // Accept cookie terms
            scrapingService.clickElement("didomi-notice-agree-button");

            // Select eligibility and click search a first time (by default the eligibility modal
            // appears and blocks the city search)
            scrapingService.clickElement(search.getEligibility().getId());
            scrapingService.clickButton("dl-button-primary");

            // Type requested city and select result
            scrapingService.typeSearch("searchbar-place-input", search.getCity());
            scrapingService.selectFirstResult("searchbar-result", "searchbar-submit-button");

            // Select eligibility and click search
            scrapingService.clickElement(search.getEligibility().getId());
            scrapingService.clickButton("dl-button-primary");

            // Scroll down to make all slots appear
            scrapingService.scrollToBottom();

            var results = scrapingService.findResults();
            System.out.println();
            System.out.println("Available slots:");
            results.stream()
                    .map(SearchResult::getTimeSlots)
                    .flatMap(Collection::stream)
                    .forEach(System.out::println);

            System.out.println();
            System.out.println("Available slots until tomorrow night:");
            results.stream()
                    .map(SearchResult::getTimeSlots)
                    .flatMap(Collection::stream)
                    .filter(date -> date.isBefore(LocalDate.now().atStartOfDay().plusDays(2)))
                    .sorted()
                    .forEach(System.out::println);
        } finally {
            scrapingService.stop();
        }
    }
}
