package stepdefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.edge.EdgeDriver;

public class Hooks {
    public static WebDriver driver;

    @Before
    public void setUp() {

        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {

        if(driver != null) {
            driver.quit();
        }
    }
}