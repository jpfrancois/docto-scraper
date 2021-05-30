package docto.scraper.application.cli;

import docto.scraper.application.Controller;
import docto.scraper.domain.Eligibility;
import docto.scraper.domain.Search;
import docto.scraper.domain.service.ScrapingService;

public class CliController implements Controller {
    private final ScrapingService scrapingService;

    public CliController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    public void search(String city, Eligibility eligibility) {
        var search = new Search(city, eligibility);
        scrapingService.search(search);
    }
}
