package docto.scraper.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchResult {
    public static String CLASS = "dl-search-result";

    private String id;
    private Presentation presentation;
    private List<Date> timeSlots;
}
