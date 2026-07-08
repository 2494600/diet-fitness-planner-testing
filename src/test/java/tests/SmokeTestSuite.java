package tests;

import listeners.RetryAnalyzer;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utilities.DriverManager;

@Listeners(TestListener.class)
public class SmokeTestSuite extends BaseTest{
    private final String baseUrl = "http://localhost:4200";

    private static String registeredUsername = "johndoe";


    @Test(priority = 1, description = "SM_TC_01: Verify landing page loads with hero section and features.")
    public void testLandingPageLayout() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/");
        LandingPage landingPage = new LandingPage(driver);
        Assert.assertTrue(landingPage.isHeroSectionVisible(), "Hero section container is missing.");
        Assert.assertTrue(landingPage.isFeaturesSectionVisible(), "Feature matrix grid elements are missing.");
        Assert.assertTrue(landingPage.isCtaButtonVisible(), "Vital entry points are unavailable.");
    }


    @Test(priority = 2, description = "SM_TC_02: Verify landing page rotating quotes change every 5 seconds.")
    public void testLandingPageQuoteRotation() {
        WebDriver driver=DriverManager.getDriver();
        driver.get(baseUrl + "/");
        LandingPage landingPage = new LandingPage(driver);
        String initialQuoteText = landingPage.getQuoteText();
        Assert.assertNotNull(initialQuoteText, "Quote rendering returned an empty block.");
        boolean quoteHasRotated = landingPage.checkQuoteRotates(initialQuoteText);
        Assert.assertTrue(quoteHasRotated, "Quotes widget failed to cycle text values within the time window.");
    }

    @Test(priority = 3, description = "SM_TC_03: Verify new user registration with all valid fields.")
    public void testNewUserRegistration() {
        WebDriver driver=DriverManager.getDriver();
        driver.get(baseUrl + "/register");
        RegisterPage registerPage = new RegisterPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // RECTIFIED: Generate and save the dynamic username to the shared class variable
        registeredUsername = "johndoe" + System.currentTimeMillis() / 1000;

        registerPage.fillRegistrationForm("John Doe", "9876543210", registeredUsername, "Pass@123",
                "MALE", "25", "175", "70", "VEG", "BEGINNER", "OVERALL_HEALTH");
        registerPage.clickRegister();

        dashboardPage.waitForUrlToContain("/dashboard");
        Assert.assertTrue(dashboardPage.getCurrentPageUrl().contains("/dashboard"), "App failed to reach /dashboard.");
    }

    @Test(priority = 4,
            description = "SM_TC_04: Verify registered user can log in.",
            retryAnalyzer = RetryAnalyzer.class,
            dependsOnMethods = {"testNewUserRegistration"}
    )
    public void testRegisteredUserLogin() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/login");
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // RECTIFIED: Using the dynamic shared username instead of a hardcoded string
        loginPage.login(registeredUsername, "Pass@123");

        dashboardPage.waitForUrlToContain("/dashboard");
        Assert.assertTrue(dashboardPage.getCurrentPageUrl().contains("/dashboard"), "Did not redirect to /dashboard.");
    }

    @Test(priority = 5,
            description = "SM_TC_05: Verify dashboard displays meals and workouts.",
            retryAnalyzer = RetryAnalyzer.class
    )
    public void testDashboardContents() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/login");
        new LoginPage(driver).login("user11", "Pass@123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForUrlToContain("/dashboard");

        // Assertion of basic page structure components are present
        Assert.assertTrue(dashboardPage.isDashboardCompletelyLoaded(),
                "CRITICAL FAILURE: Core dashboard widgets (Greeting, Quote, or BMI metric card) failed to render.");

        // Assertion of specific parsed text data inside the HTML template
        String greetingText = dashboardPage.getGreetingText();
        Assert.assertTrue(greetingText.contains("Hello"), "Welcome greeting text pattern context mismatch.");

        String bmi = dashboardPage.getBmiValue();
        Assert.assertEquals(bmi, "22.86", "Calculated dashboard user BMI data does not match the target value.");

        // verifying the exact element arrays loaded in your page model
        Assert.assertTrue(dashboardPage.getLoggedMealsCount() > 0,
                "Assertion Failed: Today's meal collection grid items (Breakfast, Lunch, etc.) are empty.");

        Assert.assertTrue(dashboardPage.getLoggedWorkoutsCount() > 0,
                "Assertion Failed: Workout scheduling rows (Cardio, Lower Body, etc.) are missing from display view.");
    }

    @Test(priority = 6,
            description = "SM_TC_06: Verify navbar links work for logged-in user.",
            retryAnalyzer = RetryAnalyzer.class
    )
    public void testUserNavbarNavigation() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/login");

        new LoginPage(driver).login("user11", "Pass@123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForUrlToContain("/dashboard");

        dashboardPage.clickHistoryLink();
        dashboardPage.waitForUrlToContain("/history");
        Assert.assertTrue(dashboardPage.getCurrentPageUrl().contains("/history"), "History route broken.");

        dashboardPage.clickProfileLink();
        dashboardPage.waitForUrlToContain("/profile");
        Assert.assertTrue(dashboardPage.getCurrentPageUrl().contains("/profile"), "Profile route broken.");

        dashboardPage.clickDashboardLink();
        dashboardPage.waitForUrlToContain("/dashboard");
        Assert.assertTrue(dashboardPage.getCurrentPageUrl().contains("/dashboard"), "Dashboard link broken.");
    }

    @Test(priority = 7, description = "SM_TC_07: Verify logout clears session and redirects.")
    public void testUserLogout() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/login");
        new LoginPage(driver).login("user11", "Pass@123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForUrlToContain("/dashboard");
        dashboardPage.clickLogout();

        // FIX: Wait for the unique landing page element (Hero Section) to appear first
        LandingPage landingPage = new LandingPage(driver);
        Assert.assertTrue(landingPage.isHeroSectionVisible(),
                "User Session termination failed: Hero section did not load after clicking logout.");

        // Now that the page is fully loaded, assert the final URL safely
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl + "/",
                "User Session termination failed to redirect back to the home landing page root.");
    }

    @Test(priority = 8, description = "SM_TC_08: Verify admin can login and see admin panel.")
    public void testAdminLogin() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/login");
        LoginPage loginPage = new LoginPage(driver);
        AdminPage adminPage = new AdminPage(driver);

        // Admin credentials remain static as defined in the Excel sheet (admin/admin123)
        loginPage.login("admin", "admin123");
        adminPage.waitForUrlToContain("/admin");

        Assert.assertTrue(adminPage.getCurrentPageUrl().contains("/admin"), "Admin login path redirection broken.");
        Assert.assertTrue(adminPage.isAdminPanelLoaded(), "Users table failed to display.");
    }

    @Test(priority = 9, description = "SM_TC_09: Verify navbar links work for logged-in admin.")
    public void testAdminNavbarNavigation() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/login");
        new LoginPage(driver).login("admin", "admin123");

        AdminPage adminPage = new AdminPage(driver);
        adminPage.waitForUrlToContain("/admin");

        adminPage.clickMealsTab();
        Assert.assertTrue(adminPage.isTabActive("Meals"), "Meal Plans tab failed to toggle active.");

        adminPage.clickWorkoutsTab();
        Assert.assertTrue(adminPage.isTabActive("Workouts"), "Workouts tab failed to toggle active.");

        adminPage.clickUsersTab();
        Assert.assertTrue(adminPage.isTabActive("Users"), "Users tab return failed.");
    }

    @Test(priority = 10, description = "SM_TC_10: Verify logout clears session and redirects.")
    public void testAdminLogout() {
        WebDriver driver=DriverManager.getDriver();

        driver.get(baseUrl + "/login");
        new LoginPage(driver).login("admin", "admin123");

        AdminPage adminPage = new AdminPage(driver);
        adminPage.waitForUrlToContain("/admin");
        adminPage.clickLogout();

        // FIX: Wait for the unique landing page element (Hero Section) to appear first
        LandingPage landingPage = new LandingPage(driver);
        Assert.assertTrue(landingPage.isHeroSectionVisible(),
                "Admin logout failed: Hero section did not load after clicking logout.");

        // Now that the page is fully loaded, assert the final URL safely
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl + "/",
                "Admin logout cycle did not clear cleanly back to the home landing page root.");
    }

}