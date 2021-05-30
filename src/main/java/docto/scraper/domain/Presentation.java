package docto.scraper.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Presentation {
    private static final String CLASS = "dl-search-result-presentation";
    private static final String TITLE_CLASS = "dl-search-result-title";
    private static final String ROOMS_CLASS = "dl-search-result-specialities";
    private static final String ADDRESS_CLASS = "dl-text dl-text-body dl-text-s dl-text-regular";
}
