package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {
    private final By adminHeaderTitle = By.cssSelector(".page-header h1");
    private final By adminMainDataTable = By.className("admin-table");

    private final By usersTabButton = By.xpath("//button[contains(@class,'tab-btn')][contains(.,'Users')]");
    private final By mealPlansTabButton = By.xpath("//button[contains(@class,'tab-btn')][contains(.,'Meal Plans')]");
    private final By workoutsTabButton = By.xpath("//button[contains(@class,'tab-btn')][contains(.,'Workouts')]");
    private final By adminLogoutButton = By.className("btn-logout");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAdminPanelLoaded() {
        return isDisplayed(adminHeaderTitle) && isDisplayed(adminMainDataTable);
    }

    public void clickUsersTab() { click(usersTabButton); }
    public void clickMealsTab() { click(mealPlansTabButton); }
    public void clickWorkoutsTab() { click(workoutsTabButton); }
    public void clickLogout() { click(adminLogoutButton); }

    public boolean isTabActive(String tabName) {
        By targetedTab;
        switch (tabName.toUpperCase()) {
            case "MEALS", "MEAL PLANS" -> targetedTab = mealPlansTabButton;
            case "WORKOUTS" -> targetedTab = workoutsTabButton;
            default -> targetedTab = usersTabButton;
        }
        return waitForVisibility(targetedTab).getAttribute("class").contains("active");
    }
}