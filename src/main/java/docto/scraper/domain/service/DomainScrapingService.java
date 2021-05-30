package docto.scraper.domain.service;

import docto.scraper.domain.SearchResult;
import docto.scraper.infrastructure.scraping.Scraper;

import java.util.List;

public class DomainScrapingService {
    private final Scraper scraper;

    public DomainScrapingService(Scraper scraper) {
        this.scraper = scraper;
    }

    public void goTo(String url) {
        scraper.goTo(url);
    }

    public void stop() {
        scraper.stop();
    }

    public void clickElement(String id) {
        scraper.clickElement(id);
    }

    public void clickButton(String className) {
        scraper.clickButton(className);
    }

    public void typeSearch(String className, String search) {
        scraper.typeSearch(className, search);
    }

    public void selectFirstResult(String resultClassName, String submitClassName) {
        scraper.selectFirstResult(resultClassName, submitClassName);
    }

    public void scrollToBottom() {
        scraper.scrollToBottom();
    }

    public List<SearchResult> findResults() {
        return scraper.findResults();
    }
}
