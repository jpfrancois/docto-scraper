package docto.scraper.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Search {
    private String city;
    private Eligibility eligibility;
    private List<SearchResult> searchResults;

    public Search(String city, Eligibility eligibility) {
        this.city = city;
        this.eligibility = eligibility;
        this.searchResults = new ArrayList<>();
    }

    public void addResult(SearchResult searchResult) {
        searchResults.add(searchResult);
    }
}
