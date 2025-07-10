package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest extends TestBaseSuite {

    private static ExtentReports extent;

    @BeforeSuite(alwaysRun = true)
    public void startExtentReport() {
        extent = ExtentManager.getInstance();
    }

    @BeforeClass(alwaysRun = true)
    public void startLog() {
        Log.startTestCase(this.getClass().getSimpleName());
    }

    @BeforeMethod(alwaysRun = true)
    public void createExtentTest(Method method) {
        ExtentTest test = extent.createTest(
            method.getDeclaringClass().getSimpleName() + " :: " + method.getName()
        );
        ExtentTestManager.setTest(test);
    }

    @AfterMethod(alwaysRun = true)
    public void captureTestResult(ITestResult result) {
        ExtentTest test = ExtentTestManager.getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("❌ Test Failed: " + result.getThrowable());

            try {
                String screenshotPath = takeScreenshot(result.getName());
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                test.warning("Screenshot not available: " + e.getMessage());
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("✅ Test Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("⚠️ Test Skipped: " + result.getThrowable());
        }
    }

    public String takeScreenshot(String testName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/test-output/screenshots/" + testName + ".png";
        File destFile = new File(path);
        FileUtils.copyFile(srcFile, destFile);
        return path;
    }

    @AfterClass(alwaysRun = true)
    public void endLog() {
        Log.endTestCase();
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        extent.flush();
    }
}
