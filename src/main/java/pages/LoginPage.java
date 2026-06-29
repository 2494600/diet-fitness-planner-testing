package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // --- Highly Resilient Attribute-Focused Locators ---
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");

    // Selects the main form submit action element safely
    private final By signInSubmitButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Executes the main authentication flow sequence for both regular users and admin profiles.
     * Uses the inherited BasePage explicit wait strategy layers.
     */
    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(signInSubmitButton);
    }
}