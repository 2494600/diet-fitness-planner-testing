package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage extends BasePage {

    // --- Core Layout Elements (SM_TC_01) ---
    private final By heroContainer = By.className("hero");
    private final By featuresContainer = By.className("features");

    // Exact structural class matching the rotating quotes card layout
    private final By quoteTextContainer = By.className("quote-text");

    // Strong, un-brittle CSS selectors for your Call-to-Action entry links
    private final By getStartedButton = By.cssSelector(".hero-actions a[href='/register']");
    private final By loginNavButton = By.cssSelector(".nav-links a[href='/login']");

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Validates SM_TC_01: High-level layout presence verification
     */
    public boolean isHeroSectionVisible() {
        return isDisplayed(heroContainer);
    }

    public boolean isFeaturesSectionVisible() {
        return isDisplayed(featuresContainer);
    }

    public boolean isCtaButtonVisible() {
        return isDisplayed(getStartedButton) && isDisplayed(loginNavButton);
    }

    /**
     * Extracts text value of the active quote string block
     */
    public String getQuoteText() {
        return getText(quoteTextContainer);
    }

    /**
     * Validates SM_TC_02: Fluent wait loop ensuring the text shifts away
     * from the initial value without freezing the current thread execution cycle.
     */
    public boolean checkQuoteRotates(String initialQuote) {
        return waitForTextToChange(quoteTextContainer, initialQuote);
    }
}