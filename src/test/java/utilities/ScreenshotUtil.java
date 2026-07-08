package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver,
                                           String testName) {

        String path =  System.getProperty("user.dir")+ "/screenshots/"+ testName+ ".png";

        File source =((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

        File destination = new File(path);

        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}
