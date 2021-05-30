package docto.scraper.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Eligibility {
    PLUS_55("eligibility-0"),
    PLUS_18("eligibility-1"),
    TEENS("eligibility-2"),
    HEALTH_CARE("eligibility-3"),
    HIGH_EXPOSURE("eligibility-4"),
    GOV_OFFICIAL("eligibility-5");

    private final String id;
}
