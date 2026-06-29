package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {

    // --- Page Header & Structural Views (SM_TC_08) ---
    private final By adminHeaderTitle = By.cssSelector(".page-header h1");
    private final By adminMainDataTable = By.className("admin-table");

    // --- Custom Tab Button Selectors (SM_TC_09) ---
    // Targets the tab buttons inside your .admin-tabs wrapper using exact structural text checks
    private final By usersTabButton = By.xpath("//button[contains(@class,'tab-btn')][contains(.,'Users')]");
    private final By mealPlansTabButton = By.xpath("//button[contains(@class,'tab-btn')][contains(.,'Meal Plans')]");
    private final By workoutsTabButton = By.xpath("//button[contains(@class,'tab-btn')][contains(.,'Workouts')]");

    // --- Global Shared Navbar Logout Button (SM_TC_10) ---
    private final By adminLogoutButton = By.className("btn-logout");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Validates SM_TC_08: Confirms the presence of the admin framework workspace
     */
    public boolean isAdminPanelLoaded() {
        return isDisplayed(adminHeaderTitle) && isDisplayed(adminMainDataTable);
    }

    // --- Custom Tab Navigation Actions (SM_TC_09) ---
    public void clickUsersTab() {
        click(usersTabButton);
    }

    public void clickMealsTab() {
        click(mealPlansTabButton);
    }

    public void clickWorkoutsTab() {
        click(workoutsTabButton);
    }

    /**
     * Helper validation method to confirm that a specific sub-view category tab state is currently marked active.
     */
    public boolean isTabActive(String tabName) {
        By targetedTab;
        switch (tabName.toUpperCase()) {
            case "MEALS", "MEAL PLANS" -> targetedTab = mealPlansTabButton;
            case "WORKOUTS" -> targetedTab = workoutsTabButton;
            default -> targetedTab = usersTabButton;
        }
        // Checks if Angular has applied the "active" style class to the chosen button container element
        return waitForVisibility(targetedTab).getAttribute("class").contains("active");
    }

    // --- Session Control Actions (SM_TC_10) ---
    public void clickLogout() {
        click(adminLogoutButton);
    }
}