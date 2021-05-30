package docto.scraper.application;

import docto.scraper.domain.Eligibility;

public interface Controller {
    void search(String city, Eligibility eligibility);
}
