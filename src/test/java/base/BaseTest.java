package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import utils.DriverFactory;
import utils.ExtentManager;
import utils.Log;
import utils.ConfigReader;
import pages.LoginPage;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent = ExtentManager.getInstance();
    protected ExtentTest test;

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.initDriver(browser);
        Log.startTestCase(this.getClass().getSimpleName());
        String baseUrl = ConfigReader.getProperty("baseUrl");
        driver.get(baseUrl);
        // 🛑 Skip login for LoginTest class
        if (!this.getClass().getSimpleName().equals("LoginTest")) {
            performLogin();
        }
    }

    public void performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");
    }

    @AfterClass
    public void tearDown() {
        Log.endTestCase();
        DriverFactory.quitDriver();
    }
}
