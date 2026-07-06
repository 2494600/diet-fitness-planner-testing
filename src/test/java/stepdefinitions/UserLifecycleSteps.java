package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.*;
import utilities.ExcelReader;

public class UserLifecycleSteps {
    private final WebDriver driver = Hooks.getDriver();
    private final RegisterPage registerPage = new RegisterPage(driver);
    private final LoginPage loginPage = new LoginPage(driver);
    private final DashboardPage dashboardPage = new DashboardPage(driver);
    private final LandingPage landingPage = new LandingPage(driver);
    private final AdminPage adminPage = new AdminPage(driver);

    // static variable keeps our created dynamic user alive in memory between different feature runs
    private static String runtimeUsername = "johndoe";
    private String initialQuote;


    @Given("The application landing page is open")
    public void openLandingPage() {
        driver.get("https://diet-and-fitness-planner-5qhw.vercel.app/");
    }

    @Then("The hero section banner container should be displayed")
    public void verifyHero() { Assert.assertTrue(landingPage.isHeroSectionVisible()); }

    @Then("The features informational grid matrix should be visible")
    public void verifyFeatures() { Assert.assertTrue(landingPage.isFeaturesSectionVisible()); }

    @Then("The main Call-To-Action entry buttons should be present")
    public void verifyCTAs() { Assert.assertTrue(landingPage.isCtaButtonVisible()); }

    @When("I read the initial text value of the quotes display panel")
    public void readQuote() { initialQuote = landingPage.getQuoteText(); }

    @Then("The quote text should automatically rotate to a new value within 12 seconds")
    public void verifyRotation() { Assert.assertTrue(landingPage.checkQuoteRotates(initialQuote)); }


    @Given("I navigate to the user registration route portal")
    public void navigateToRegistration() {
        driver.get("https://diet-and-fitness-planner-5qhw.vercel.app/register");
    }

    @When("I submit valid personal details with a unique username, password {string}, age {string}, height {string}, weight {string}, level {string}, and goal {string}")
    public void fillRegistration(String password, String age, String height, String weight, String level, String goal) {
        runtimeUsername = "johndoe" + System.currentTimeMillis() / 1000;
        registerPage.fillRegistrationForm("John Doe", "9876543210", runtimeUsername, password,
                "MALE", age, height, weight, "VEG", level, goal);
        registerPage.clickRegister();
    }


    @Given("I am on the application authentication login interface")
    public void openLoginPage() {
        driver.get("https://diet-and-fitness-planner-5qhw.vercel.app/login");
    }

    @When("I log in using the newly registered account credentials with password {string}")
    public void loginActiveUser(String password) {
        loginPage.login(runtimeUsername, password);
    }

    @Then("I should be securely redirected and synchronized to the user {string} panel")
    public void verifyRedirection(String pathFraction) {
        dashboardPage.waitForUrlToContain(pathFraction);
        Assert.assertTrue(dashboardPage.getCurrentPageUrl().contains(pathFraction));
    }

    @Then("The greeting banner text widget should contain {string}")
    public void checkGreeting(String pattern) {
        Assert.assertTrue(dashboardPage.getGreetingText().contains(pattern));
    }

    @Then("The user dashboard BMI value text display must read {string}")
    public void verifyBmi(String bmiVal) {
        Assert.assertEquals(dashboardPage.getBmiValue(), bmiVal);
    }

    @Then("The loaded meal planning task items collection must not be empty")
    public void verifyMeals() {
        Assert.assertTrue(dashboardPage.getLoggedMealsCount() > 0);
    }

    @Then("The loaded workouts routine scheduling logs must not be empty")
    public void verifyWorkouts() {
        Assert.assertTrue(dashboardPage.getLoggedWorkoutsCount() > 0);
    }

    @When("I navigate across {string}, {string}, and back to {string} navigation links")
    public void cycleNavbarLinks(String r1, String r2, String r3) {
        dashboardPage.clickHistoryLink();
        dashboardPage.waitForUrlToContain(r1);
        dashboardPage.clickProfileLink();
        dashboardPage.waitForUrlToContain(r2);
        dashboardPage.clickDashboardLink();
        dashboardPage.waitForUrlToContain(r3);
    }

    @Then("Each target route application path parameter must load safely")
    public void confirmCycleSuccess() {
        Assert.assertTrue(dashboardPage.getCurrentPageUrl().contains("/dashboard"));
    }


    @When("I click the navigation header session logout option")
    public void clickUserLogout() {
        dashboardPage.clickLogout();
    }

    @Then("My active session tokens must clear and redirect me back to the root landing path")
    public void verifyReturnToLanding() {
        Assert.assertTrue(landingPage.isHeroSectionVisible());
        Assert.assertEquals(driver.getCurrentUrl(), "https://diet-and-fitness-planner-5qhw.vercel.app/");
    }

    @When("I log in using excel user {string}")
    public void loginUsingExcel(String rowNumber) {

        String excelPath = "src/test/resources/testdata/LoginData.xlsx";

        int row = Integer.parseInt(rowNumber);

        String username = ExcelReader.getCellData(excelPath, row, 0);
        String password = ExcelReader.getCellData(excelPath, row, 1);

        loginPage.login(username, password);
    }
}