package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utilities.DriverManager;

import java.util.Locale;

public class BaseTest {

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {

        WebDriver webDriver= null;

        if(browser.equalsIgnoreCase("chrome")){
            webDriver = new ChromeDriver();
        }else if(browser.equalsIgnoreCase("edge")){
            webDriver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            webDriver = new FirefoxDriver();
        }

        DriverManager.setDriver(webDriver);
    }


    @AfterMethod
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}
