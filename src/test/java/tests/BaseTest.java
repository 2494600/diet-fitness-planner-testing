package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utilities.DriverManager;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {

        if(browser.equalsIgnoreCase("chrome")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            WebDriver driver = new ChromeDriver(options);
        }else if(browser.equalsIgnoreCase("edge")){
            WebDriver driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriver driver = new FirefoxDriver();
        }

        DriverManager.setDriver(driver);
        driver=DriverManager.getDriver();
    }


    @AfterMethod
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}
