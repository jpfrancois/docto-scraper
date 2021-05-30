package docto.scraper.domain;

public enum Eligibility {
    PLUS_55("eligibility-0"),
    PLUS_18("eligibility-1"),
    TEENS("eligibility-2"),
    HEALTH_CARE("eligibility-3"),
    HIGH_EXPOSURE("eligibility-4"),
    GOV_OFFICIAL("eligibility-5");

    private final String id;

    Eligibility(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
