package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.DriverManager;
import utilities.ScreenshotUtil;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {

        System.out.println("Execution Started");
    }

    @Override
    public void onFinish(ITestContext context) {

        System.out.println("Execution Finished");
    }

    @Override
    public void onTestStart(ITestResult result) {

        System.out.println("Started : "+ result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println("PASSED : "+ result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        System.out.println("Failed : "+ result.getName());
        ScreenshotUtil.captureScreenshot(DriverManager.getDriver(),result.getName());

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        System.out.println("SKIPPED : "+ result.getName());
    }
}