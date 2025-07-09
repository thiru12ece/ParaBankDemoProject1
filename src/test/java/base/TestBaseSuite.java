package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class TestBaseSuite {
    protected static WebDriver driver;

    @Parameters("browser")
    @BeforeSuite(alwaysRun = true)
    public void setupSuite(@Optional("chrome") String browser) {
        driver = DriverFactory.initDriver(browser);

        // Perform login only if not running LoginTest
        String currentTestClass = System.getProperty("testClass");
        if (currentTestClass == null || !currentTestClass.equals("tests.LoginTest")) {
            driver.get(ConfigReader.getProperty("baseUrl"));
            new LoginPage(driver).login("john", "demo");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void teardownSuite() {
        DriverFactory.quitDriver();
    }
}
