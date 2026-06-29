package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    // --- Core Widget Locators (SM_TC_05) ---
    // Selects the personalized welcome header text displaying the username
    private final By greetingMessage = By.cssSelector(".greeting h1");
    // Selects the central container housing the text quote value
    private final By quoteLine = By.className("quote-line");
    // Explicit targeting for the numeric BMI string wrapper
    private final By bmiValueDisplay = By.className("bmi-value");
    // Structural wrapper for the Obese/Overweight/Normal indicator box
    private final By bmiStatusPill = By.cssSelector(".bmi-status");
    // Selecting specific layout grid structures for automated collection validation
    private final By mealPlanCardsList = By.cssSelector(".meal-badge");
    private final By workoutCardsList = By.cssSelector(".workout-badge");

    // --- App Navigation Items (SM_TC_06) ---
    private final By dashboardNavLink = By.cssSelector("a[href='/dashboard']");
    private final By historyNavLink = By.cssSelector("a[href='/history']");
    private final By profileNavLink = By.cssSelector("a[href='/profile']");

    // --- Session Termination Controls (SM_TC_07) ---
    private final By logoutButton = By.className("btn-logout");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Comprehensive validation step for SM_TC_05.
     * Verifies if all structural content blocks from your custom layout render safely.
     */
    public boolean isDashboardCompletelyLoaded() {
        return isDisplayed(greetingMessage)
                && isDisplayed(quoteLine)
                && isDisplayed(bmiValueDisplay)
                && isDisplayed(bmiStatusPill);
    }

    public String getGreetingText() {
        return getText(greetingMessage);
    }

    public String getBmiValue() {
        return getText(bmiValueDisplay);
    }

    public int getLoggedMealsCount() {
        return driver.findElements(mealPlanCardsList).size();
    }

    public int getLoggedWorkoutsCount() {
        return driver.findElements(workoutCardsList).size();
    }

    // --- Interaction Methods ---
    public void clickDashboardLink() {
        click(dashboardNavLink);
    }

    public void clickHistoryLink() {
        click(historyNavLink);
    }

    public void clickProfileLink() {
        click(profileNavLink);
    }

    public void clickLogout() {
        click(logoutButton);
    }
}