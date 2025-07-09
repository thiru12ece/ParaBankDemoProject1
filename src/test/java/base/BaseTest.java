package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.*;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.Log;

import java.lang.reflect.Method;

public class BaseTest extends TestBaseSuite {  // ✅ gets driver from parent

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
        ExtentTest test = extent.createTest(method.getDeclaringClass().getSimpleName() + " :: " + method.getName());
        ExtentTestManager.setTest(test);
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
