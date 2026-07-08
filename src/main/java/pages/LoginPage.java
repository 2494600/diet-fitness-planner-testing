package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By signInSubmitButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        setUsername(username);
        setPassword(password);
        submit();
    }

    public void setUsername(String username) {
        type(usernameInput, username);
    }

    public void setPassword(String password) {
        type(passwordInput, password);
    }

    public String getUsernameValue() {
        return driver.findElement(usernameInput).getAttribute("value");
    }

    public String getPasswordValue() {
        return driver.findElement(passwordInput).getAttribute("value");
    }

    public void submit() {
        click(signInSubmitButton);
    }

    public void waitForLoginForm() {
        waitForVisibility(usernameInput);
        waitForVisibility(passwordInput);
    }
}