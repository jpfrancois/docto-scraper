package docto.scraper;

import docto.scraper.application.cli.CliController;
import docto.scraper.domain.Eligibility;
import docto.scraper.domain.service.DomainScrapingService;
import docto.scraper.infrastructure.config.ChromeConfig;
import docto.scraper.infrastructure.scraping.SeleniumScraper;

import java.util.Arrays;

public class App {
    private static final Eligibility DEFAULT_ELIGIBILITY = Eligibility.PLUS_55;

    public static void main(String[] args) {
        validateArgs(args);

        var scraper = new SeleniumScraper(new ChromeConfig());
        var scrapingService = new DomainScrapingService(scraper);
        var controller = new CliController(scrapingService);

        var eligibility = (args.length < 2 ? DEFAULT_ELIGIBILITY : Eligibility.valueOf(args[1]));
        controller.search(args[0], eligibility);
    }

    private static void validateArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: gradle run --args='CityName [Eligibility]'");
            System.out.printf("...where Eligibility is one of: %s%n",
                    Arrays.toString(Eligibility.values()));
            System.out.printf("...and the default Eligibility = %s%n", DEFAULT_ELIGIBILITY);
            System.exit(1);
        }
    }
}
