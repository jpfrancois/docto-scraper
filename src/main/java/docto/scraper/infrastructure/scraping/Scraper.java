package docto.scraper.infrastructure.scraping;

import docto.scraper.domain.SearchResult;

import java.util.List;

public interface Scraper {
    void goTo(String url);

    void stop();

    void clickElement(String id);

    void clickButton(String className);

    void typeSearch(String className, String search);

    void selectFirstResult(String resultClassName, String submitClassName);

    void scrollToBottom();

    List<SearchResult> findResults();
}
