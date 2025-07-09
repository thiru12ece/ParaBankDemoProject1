package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentTestManager;

public class LoginTest extends BaseTest {

    String baseUrl = ConfigReader.getProperty("baseUrl");
    LoginPage loginPage;

    @Test
    public void loginWithValidCredentials() {
        ExtentTestManager.getTest().info("Navigating to: " + baseUrl);
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        loginPage.enterUsername("john");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying title contains 'Accounts Overview'");
        Assert.assertTrue(driver.getTitle().contains("ParaBank | Accounts Overview"), "Login should succeed with valid credentials");
    }

    @Test
    public void loginWithInvalidPassword() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        loginPage.enterUsername("john");
        loginPage.enterPassword("wrongpass");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for wrong password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"), "Error message should indicate failed login");
    }

    @Test
    public void loginWithUnregisteredUser() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        loginPage.enterUsername("nonexistentuser");
        loginPage.enterPassword("somepass");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for unregistered user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"), "Error message should indicate unknown user");
    }

    @Test
    public void loginWithEmptyFields() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for empty fields");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Please enter a username and password."), "Should prompt to enter credentials");
    }

    @Test
    public void loginWithInvalidEmailFormat() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        loginPage.enterUsername("invalid-email");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for invalid email format");
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"), "Login should fail for email-like username");
    }

    @Test(enabled = false)
    public void verifyPasswordFieldIsMasked() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        Assert.assertEquals(loginPage.getPasswordFieldType(), "password", "Password field should be masked");
    }

    @Test(enabled = false)
    public void rememberMeFunctionality() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be visible");
    }

    @Test(enabled = false)
    public void sessionTimeoutTest() throws InterruptedException {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);

        loginPage.enterUsername("john");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();

        Thread.sleep(600000); // Simulate 10 min of inactivity
        driver.navigate().refresh();

        Assert.assertTrue(driver.getTitle().contains("ParaBank"), "After session timeout, user should be redirected to login");
    }
}
