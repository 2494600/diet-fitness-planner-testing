package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    private final By greetingMessage = By.cssSelector(".greeting h1");
    private final By quoteLine = By.className("quote-line");
    private final By bmiValueDisplay = By.className("bmi-value");
    private final By bmiStatusPill = By.cssSelector(".bmi-status");
    private final By mealPlanCardsList = By.cssSelector(".meal-badge");
    private final By workoutCardsList = By.cssSelector(".workout-badge");

    private final By dashboardNavLink = By.cssSelector("a[href='/dashboard']");
    private final By historyNavLink = By.cssSelector("a[href='/history']");
    private final By profileNavLink = By.cssSelector("a[href='/profile']");
    private final By logoutButton = By.className("btn-logout");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardCompletelyLoaded() {
        return isDisplayed(greetingMessage) && isDisplayed(quoteLine) && isDisplayed(bmiValueDisplay) && isDisplayed(bmiStatusPill);
    }

    public String getGreetingText() { return getText(greetingMessage); }
    public String getBmiValue() { return getText(bmiValueDisplay); }
    public int getLoggedMealsCount() { return driver.findElements(mealPlanCardsList).size(); }
    public int getLoggedWorkoutsCount() { return driver.findElements(workoutCardsList).size(); }
    public void clickDashboardLink() { click(dashboardNavLink); }
    public void clickHistoryLink() { click(historyNavLink); }
    public void clickProfileLink() { click(profileNavLink); }
    public void clickLogout() { click(logoutButton); }
}