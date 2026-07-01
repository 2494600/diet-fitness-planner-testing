package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage extends BasePage {
    private final By heroContainer = By.className("hero");
    private final By featuresContainer = By.className("features");
    private final By quoteTextContainer = By.className("quote-text");
    private final By getStartedButton = By.cssSelector(".hero-actions a[href='/register']");
    private final By loginNavButton = By.cssSelector(".nav-links a[href='/login']");

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeroSectionVisible() { return isDisplayed(heroContainer); }
    public boolean isFeaturesSectionVisible() { return isDisplayed(featuresContainer); }
    public boolean isCtaButtonVisible() { return isDisplayed(getStartedButton) && isDisplayed(loginNavButton); }
    public String getQuoteText() { return getText(quoteTextContainer); }
    public boolean checkQuoteRotates(String initialQuote) { return waitForTextToChange(quoteTextContainer, initialQuote); }
}