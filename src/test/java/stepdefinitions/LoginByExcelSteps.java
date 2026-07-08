package stepdefinitions;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LoginPage;
import utilities.ExcelUtil;

import java.time.Duration;

public class LoginByExcelSteps {

    private final String filePath =
            System.getProperty("user.dir")
                    + "/src/test/resources/testdata/LoginData.xlsx";

    @Given("User launches the application for excel login")
    public void launchApplicationForExcelLogin() {

        Hooks.driver.get("http://localhost:4200/login");
    }

    @When("User logs in using excel data")
    public void userLogsInUsingExcelData() {

        Object[][] data =
                ExcelUtil.getLoginData(filePath, "Login");

        for (Object[] row : data) {

            String username = row[0].toString();
            String password = row[1].toString();

            Hooks.driver.get("http://localhost:4200/login");

            LoginPage loginPage = new LoginPage(Hooks.driver);

            loginPage.setUsername(username);
            loginPage.setPassword(password);
            loginPage.submit();

            WebDriverWait wait =
                    new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/dashboard"),
                    ExpectedConditions.urlContains("/admin")
            ));

            String currentUrl = Hooks.driver.getCurrentUrl();

            System.out.println(
                    "Username: " + username +
                    " | URL: " + currentUrl
            );

            Assert.assertTrue(
                    currentUrl.contains("/dashboard")
                            || currentUrl.contains("/admin"),
                    "Login Failed For User : " + username
            );
        }
    }

    @Then("All users should be redirected successfully")
    public void allUsersShouldBeRedirectedSuccessfully() {

        System.out.println("Excel Login Execution Completed");
    }
}