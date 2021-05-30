package docto.scraper.domain.service;

import docto.scraper.domain.Search;
import docto.scraper.domain.SearchResult;
import docto.scraper.infrastructure.scraping.Scraper;

import java.time.LocalDate;
import java.util.Collection;

public class DomainScrapingService implements ScrapingService {
    private static final String SITE = "https://www.doctolib.fr/vaccination-covid-19";

    private final Scraper scraper;

    public DomainScrapingService(Scraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void search(Search search) {
        try {
            scraper.goTo(SITE);

            // Accept cookie terms
            scraper.clickElement("didomi-notice-agree-button");

            // Select eligibility and click search a first time (by default the eligibility modal
            // appears and blocks the city search)
            scraper.clickElement(search.getEligibility().getId());
            scraper.clickButton("dl-button-primary");

            // Type requested city and select result
            scraper.typeSearch("searchbar-place-input", search.getCity());
            scraper.selectFirstResult("searchbar-result", "searchbar-submit-button");

            // Select eligibility and click search
            scraper.clickElement(search.getEligibility().getId());
            scraper.clickButton("dl-button-primary");

            // Scroll down to make all slots appear
            scraper.scrollToBottom();

            var results = scraper.findResults();
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
            scraper.stop();
        }
    }
}
