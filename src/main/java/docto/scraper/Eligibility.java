package docto.scraper;

public enum Eligibility {
    PLUS55("eligibility-0"),
    PLUS18("eligibility-1");

    private final String id;

    Eligibility(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
