# docto-scraper

Standalone Java program that scrapes the Doctolib website (https://www.doctolib.fr/vaccination-covid-19)
in order to detect the COVID-19 vaccination time slots for a given city and group of people. It shows the
time slots until the next day's evening.

## Requirements
- Java 11
- Gradle
- Google Chrome driver
  - The latest version can be downloaded from http://chromedriver.storage.googleapis.com/index.html
  - It must be stored in `src/main/resources/chromedriver`

## Launching the program
**Usage:** `gradle run --args='CityName [Eligibility]'`

...where Eligibility is one of: `[PLUS_55, PLUS_18, TEENS, HEALTH_CARE, HIGH_EXPOSURE, GOV_OFFICIAL]`

... and the default Eligibility = `PLUS_55`
