package docto.scraper.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SearchResult {
    public static String CLASS = "dl-search-result";

    private String id;
    private Presentation presentation;
    private List<LocalDateTime> timeSlots;
}
