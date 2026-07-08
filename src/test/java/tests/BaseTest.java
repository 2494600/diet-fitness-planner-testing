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

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver webDriver= new ChromeDriver(options);

        if(browser.equalsIgnoreCase("chrome")){
            webDriver = new ChromeDriver(options);
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
