package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import utils.ExtentTestManager;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        loginPage = new LoginPage(driver);
    }


    @Test(priority = 1)
    public void loginWithInvalidPassword() {
        //logout to perform login test cases
        loginPage.clickLogOut();
        loginPage.enterUsername("john");
        loginPage.enterPassword("wrongpass");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for wrong password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"), "Error message should indicate failed login");
    }

    @Test(priority = 2)
    public void loginWithUnregisteredUser() {
     
        loginPage.enterUsername("nonexistentuser");
        loginPage.enterPassword("somepass");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for unregistered user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"), "Error message should indicate unknown user");
    }

    @Test(priority = 3)
    public void loginWithEmptyFields() throws InterruptedException {
    	Thread.sleep(2000);
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for empty fields");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Please enter a username and password."), "Should prompt to enter credentials");
    }

    @Test(priority = 4)
    public void loginWithInvalidEmailFormat() throws InterruptedException {
    	Thread.sleep(2000);
        loginPage.enterUsername("invalid-email");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying error message for invalid email format");
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"), "Login should fail for email-like username");
    }
    
    @Test(priority = 5)
    public void loginWithValidCredentials() {
        loginPage.enterUsername("john");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();

        ExtentTestManager.getTest().info("Verifying title contains 'Accounts Overview'");
        Assert.assertTrue(driver.getTitle().contains("ParaBank | Accounts Overview"), "Login should succeed with valid credentials");
    }

    @Test(enabled = false)
    public void verifyPasswordFieldIsMasked() {

        Assert.assertEquals(loginPage.getPasswordFieldType(), "password", "Password field should be masked");
    }

    @Test(enabled = false)
    public void rememberMeFunctionality() {
  
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be visible");
    }

    @Test(enabled = false)
    public void sessionTimeoutTest() throws InterruptedException {

        loginPage.enterUsername("john");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();

        Thread.sleep(600000); // Simulate 10 minutes of inactivity
        driver.navigate().refresh();

        Assert.assertTrue(driver.getTitle().contains("ParaBank"), "After session timeout, user should be redirected to login");
    }
}
