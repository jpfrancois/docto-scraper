package docto.scraper;

import docto.scraper.application.cli.CliController;
import docto.scraper.domain.Eligibility;
import docto.scraper.domain.Search;

import java.util.Arrays;

public class App {
    private static final Eligibility DEFAULT_ELIGIBILITY = Eligibility.PLUS_55;

    public static void main(String[] args) {
        validateArgs(args);

        var eligibility = (args.length < 2 ? DEFAULT_ELIGIBILITY : Eligibility.valueOf(args[1]));
        var search = new Search(args[0], eligibility);

        var controller = new CliController();
        controller.search(search);
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
