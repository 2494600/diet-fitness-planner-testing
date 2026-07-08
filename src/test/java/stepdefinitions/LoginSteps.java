package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


import org.testng.Assert;

import pages.LoginPage;
import utilities.DriverManager;

public class LoginSteps {

    LoginPage loginPage;

    @Given("User launches the application")
    public void launchApplication() {

        DriverManager.getDriver().get("http://localhost:4200/login");
        loginPage = new LoginPage(Hooks.driver);
    }

    @When("User enters username {string}")
    public void enterUsername(String username) {

        loginPage.setUsername(username);
    }

    @And("User enters password {string}")
    public void enterPassword(String password) {

        loginPage.setPassword(password);
    }

@And("User clicks login button")
public void clickLogin() {

    loginPage.submit();

    WebDriverWait wait =
            new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));

    wait.until(driver ->
            driver.getCurrentUrl().contains("/dashboard")
                    || driver.getCurrentUrl().contains("/admin"));
}

    @Then("User should be redirected successfully")
    public void verifyLogin() {

        String currentUrl = Hooks.driver.getCurrentUrl();

        System.out.println("Current URL = " + currentUrl);

        Assert.assertTrue(
                currentUrl.contains("/dashboard")
                        || currentUrl.contains("/admin"),
                "Login Failed");
    }
}